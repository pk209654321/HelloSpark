package sparkprogram

import java.util.Date

import bean.TCollect
import constant.Constants
import dao.ITVideoplayDao
import dao.factory.DAOFactory
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.time.DateUtils
import org.apache.spark.rdd.RDD
import util.{DateTimeUtils, TimeUtil}

import scala.collection.mutable
import scala.collection.mutable.{Set, SortedSet}

/**
  * Created by lenovo on 2018/6/29.
  *
  * @deprecated 按照(课程,时间)的粒度分析统计数据
  */
object CollectAppOrWeiXin {
  val videoPlayDao: ITVideoplayDao = DAOFactory.getVideoPlayDao
  def doSparkAppOrWeiXin(parallelize: RDD[TCollect],appFlag:Int,dayFlag:Int): Unit = {

    //过滤出课程的基础数据
    val courseData: RDD[TCollect] = parallelize.filter(line => {
      val courseId = line.getCourseId
      val liveId=line.getLiveId
      if (courseId != null) {
        true
      }else{
        false
      }
    })
    //println(courseData.collect().toList)
    //过滤出直播的基础数据
    /*val liveData: RDD[TCollect] = parallelize.filter(line => {
      val liveId = line.getLiveId
      if (liveId != null) {
        true
      }else{
        false
      }
    })
    println("liveDataCollect-----"+liveData.collect().toList)*/
    //执行课程数据
    doLiveOrCourse(courseData,Constants.COURSE_FLAG,appFlag,dayFlag)

    //执行直播数据
    //doLiveOrCourse(liveData,Constants.LIVE_FLAG,appFlag)

  }

  def doLiveOrCourse(courseData: RDD[TCollect],liveFlag:Int,appFlag:Int,dayFlag:Int): Unit ={
    //过滤掉不是当天的数据
    val currentDayData: RDD[TCollect] = courseData.filter(line => {
      val time: String = line.getCreateTime
      val date: Date = TimeUtil.getFastTimeFormat.parse(time)
      //求当天的数据时间
      val nextDate: Date = TimeUtil.getNextDate(new Date(), -dayFlag)
      val sameDay: Boolean = DateUtils.isSameDay(date, nextDate)
      if (sameDay) {
        true
      } else {
        false
      }
    })
    //每天课程
    val timeCourseIdRdd: RDD[((Date, Integer), TCollect)] = currentDayData.map(line => {
      val time = line.getCreateTime //时间
      val date = TimeUtil.formatString2Date("yyyy-MM-dd", time)
      //println(date)
      val courseId = line.getCourseId //课程
      val liveId=line.getLiveId
      if(liveFlag==Constants.LIVE_FLAG){//直播
        ((date,liveId),line)
      }else{
        //课程
      ((date, courseId), line)
      }
    })

    //课程group
    val timeCourseIdIt: RDD[((Date, Integer), Iterable[TCollect])] = timeCourseIdRdd.groupByKey()

    //求点击量
    val insertDateFunction = (line: ((Date, Integer), Iterable[TCollect])) => {
      val time=line._1._1
      val it = line._2
      val itSize = it.size //点击数
      var timeToal:Double=0.0 //总时长
      val set = mutable.HashSet[Integer]()
     // val set= new mutable.HashSet[Int]()
      val typeId=line._1._2//直播/课程
      for (elem <- it) {
        var time:Double=0.0
        if(StringUtils.isNotEmpty(elem.getEnterTime)&&StringUtils.isNotEmpty(elem.getLeaveTime)){
          time =DateTimeUtils.printDifference(elem.getEnterTime, elem.getLeaveTime,"yyyy-MM-dd HH:mm:ss")
          //println("time:" + time)
        }
        val userId: Integer = elem.getUserId
        set.add(userId)
        timeToal = timeToal + time
        //println(timeToal)
      }
      val size: Int = set.size //用户数量
      println("userCount:"+size)
      val avgClick = itSize / size //平均点击量
      val avgTime:Double = timeToal / size
      val round: Long = Math.round(avgTime)//平均时长
      (typeId,itSize,round,avgClick,size,time)
    }
    //求各类型每天的点击量
    //val avgValue: RDD[(Int,Int, Long, Int, Int)] = timeCourseIdIt.map(insertDateFunction)
    val avgValue: RDD[(Integer, Int, Long, Int, Int,Date)] = timeCourseIdIt.map(insertDateFunction)
    avgValue.foreach(line => {
      val date: Date = line._6
      val date2String: String = TimeUtil.getDate2String("yyyy-MM-dd",date)
      //val videoPlayDao: ITVideoplayDao = DAOFactory.getVideoPlayDao
      val typeId= line._1 //课程id
      val itSize= line._2//点击量
      val avgTime=line._3//平均点击时间
      val avgClick= line._4//平均点击
      val size= line._5//用户数量
      val time =line._6//用户行为时间
      val sql1="insert t_videoplay (live_id,course_click,capita_min,course_daily_click,course_user,page_type,insert_time)" +
        "values(?,?,?,?,?,?,?)"
      val sql2="insert t_videoplay (course_id,course_click,capita_min,course_daily_click,course_user,page_type,insert_time)" +
        "values(?,?,?,?,?,?,?)"
      //videoPlayDao.insertTVideoplayDao()
      val array=Array[String](typeId.toString,itSize.toString,avgTime.toString,avgClick.toString,size.toString,appFlag.toString,date2String)
      //selectTodayCourse(date2String,typeId,appFlag)
      if(liveFlag==Constants.LIVE_FLAG){//直播
      val dao: Int = videoPlayDao.insertTVideoplayDao(sql1,array)
      }else if(liveFlag==Constants.COURSE_FLAG){//课程
        videoPlayDao.insertTVideoplayDao(sql2,array)
      }

    })
    //======================================================================================================
    //求截至当天的总UserCount
    //求课程总的量--每个对应的userCount
    val distinct: RDD[((Integer, Integer), Int)] = courseData.map(line => {
      val courseId = line.getCourseId //课程
      val liveId= line.getLiveId
      val user: Integer = line.getUserId
      if(liveFlag==Constants.LIVE_FLAG){
        ((liveId, user), 1)
      }else{
        ((courseId, user), 1)
      }
    }).distinct()
    val courseCount: RDD[(Integer, Int)] = distinct.map(line => {
      val typeId = line._1._1
      (typeId, 1)
    }).reduceByKey(_ + _)
    courseCount.foreach(line=> {
      val typeId=line._1
      val count=line._2
      // update t_videoplay set (CourseAggregateUser=?) where InsertTime=Now()
      val sql1="update t_videoplay set course_aggregate_user=? where " +
        " live_id=? and page_type=? and TO_DAYS(now())-TO_DAYS(insert_time)="+dayFlag
      val sql2="update t_videoplay set course_aggregate_user=? where " +
        " course_id=? and page_type=? and TO_DAYS(now())-TO_DAYS(insert_time)="+dayFlag

      val array=Array[String](count.toString,typeId.toString,appFlag.toString)
      if(liveFlag==Constants.LIVE_FLAG){
        val updateUserTotalCount: Int = videoPlayDao.updateUserTotalCount(sql1,array)
        //println(updateUserTotalCount)
      }else{
        val updateUserTotalCount: Int = videoPlayDao.updateUserTotalCount(sql2,array)
        //println(updateUserTotalCount)
      }
    })
    //======================================================================================================

    //按(userID,courseId,date)得到每个用户对应的每个 对应的所在天数
    val userCourseIdFunction = (line: TCollect) => {
      val time = line.getCreateTime //时间
      val date = TimeUtil.formatString2Date("yyyy-MM-dd", time)
      val courseId = line.getCourseId
      val userId = line.getUserId
      val liveId=line.getLiveId
      if(liveFlag==Constants.LIVE_FLAG){//直播
        ((userId,liveId), date)
      }else{//课程
        ((userId,courseId),date)
      }
    }

    val userCourseId: RDD[((Integer, Integer), Date)] = courseData.map(userCourseIdFunction)
    //(userId,couresId,set) //将对应用户--时间排序,第一个天数则是该用户是新增的
    val userDates: RDD[((Integer, Integer), Iterable[Date])] = userCourseId.groupByKey()

    val userDatesFunction = (line:((Integer, Integer), Iterable[Date])) => {
      val typeId=line._1._2
      val dates = line._2
      val set = SortedSet[Date]()
      for (date <- dates) {
        set.add(date)
      }
      val head: Date = set.head
      //head 时间才算作是新用户
      ((head,typeId),1)
    }
    //计算新增用户 (某个时间,某个课程新增)
    val reduceByKey: RDD[((Date, Integer), Int)] = userDates.map(userDatesFunction).reduceByKey(_+_)
    val currentData: RDD[((Date, Integer), Int)] = reduceByKey.filter(line => {
      val date: Date = line._1._1
      //求当天的数据时间
      val nextDate: Date = TimeUtil.getNextDate(new Date(), -dayFlag)
      val sameDay: Boolean = DateUtils.isSameDay(date, nextDate)
      if (sameDay) {
        true
      } else {
        false
      }
    })
    currentData.foreach(line=>{
      //println("line-------------"+line)
      val typeId=line._1._2
      val date =line._1._1
      val date2String: String = TimeUtil.getDate2String("yyyy-MM-dd",date)
      val countNewUser=line._2
      //update
      val sql1="update t_videoplay set course_new_user=? where " +
        "insert_time=? " +
        "and live_id=? and page_type=?"
      val sql2="update t_videoplay set course_new_user=? where " +
        "insert_time=? " +
        "and course_id=? and page_type=?"
      val array=Array[String](countNewUser.toString,date2String,typeId.toString,appFlag.toString)
      //val videoPlayDao: ITVideoplayDao = DAOFactory.getVideoPlayDao
      if(liveFlag==Constants.LIVE_FLAG){//直播
        val updateUserTotalCount: Int = videoPlayDao.updateUserTotalCount(sql1,array)
        //println(updateUserTotalCount)
      }else{//课程
        val updateUserTotalCount: Int = videoPlayDao.updateUserTotalCount(sql2,array)
       //println(updateUserTotalCount)
      }
    })
  }

  def selectTodayCourse(date2String:String,typeId:Int,appFlag:Int): Int ={
    val arraySelect=Array[String](date2String,typeId.toString,appFlag+"")
    val sql="select * from t_videoplay where insert_time=? and course_id=? and page_type=?"
    val row: Int = videoPlayDao.selectTVideoplayDao(sql,arraySelect)
    row
  }
}
