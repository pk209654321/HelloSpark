package sparkprogram

import java.util.Date

import bean.TCollect
import dao.ITSingleUserDao
import dao.factory.DAOFactory
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.time.DateUtils
import org.apache.spark.rdd.RDD
import util.{DateTimeUtils, TimeUtil}

import scala.collection.mutable

/**
  * Created by lenovo on 2018/7/3.
  *
  * @deprecated 按照(用户,时间,课程,投顾id)的粒度分析统计数据
  */
object SingleUserClient {

  val dao= DAOFactory.getTSingleUserDao
  def doSingleUser(parallelize: RDD[TCollect],flag:Int,dayFlag:Int): Unit ={
    //计算某天,某用户,对应的某课程,的流量
    val filter: RDD[TCollect] = parallelize.filter(_.getCourseId!=null)
    //过滤掉不是当天的数据
    val currentDayData: RDD[TCollect] = filter.filter(line => {
      //val adId: Integer = line.getAdviserId
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
    val userDateCourseAd: RDD[((Integer, Date, Integer, Integer), TCollect)] = currentDayData.map(line => {
      val adId: Integer = line.getAdviserId()
      val userId = line.getUserId
      val courseId: Integer = line.getCourseId
      val string2Date = TimeUtil.formatString2Date("yyyy-MM-dd", line.getCreateTime)
      ((userId, string2Date, courseId, adId), line)
    })
    val userTimeLines: RDD[((Integer, Date, Integer, Integer), Iterable[TCollect])] = userDateCourseAd.groupByKey()
    val userCountTime: RDD[(Integer, Int, Long, Date, Integer, Integer)] = userTimeLines.map(line => {
      val adId: Integer = line._1._4 //投顾adId
      val date = line._1._2
      val userId = line._1._1
      val courseId = line._1._3
      val it = line._2
      val dayUserCount = it.size //每天用户点击量

      var totalTime: Double = 0.0 //总的时长
      for (line <- it) {
        val startTime = line.getEnterTime
        val endTime = line.getLeaveTime
        var difference: Double = 0.0
        if (StringUtils.isNotEmpty(line.getEnterTime) && StringUtils.isNotEmpty(line.getLeaveTime)) {
          //println("userId------------" + line.getId)
          difference = DateTimeUtils.printDifference(startTime, endTime, "yyyy-MM-dd HH:mm:ss")
        }
        totalTime += difference
      }
      val round: Long = Math.round(totalTime)
      (userId, dayUserCount, round, date, courseId, adId)
    })
    userCountTime.foreach(line => {//insert userid,当天用户点击量,时长,时间,课程id
      val userId= line._1
      val date: Date = line._4
      val courseId: Integer = line._5
      val adId: Integer = line._6
      val date2String: String = TimeUtil.getDate2String("yyyy-MM-dd",date)
      val sql="insert t_SingleUser (single_employ_time,single_click,single_user_id,insert_time,course_id,page_type,ad_id) " +
        "values " +
        "(?,?,?,?,?,?,?)"
      val array=Array[String](line._3.toString,line._2.toString,line._1.toString,date2String,courseId.toString,flag.toString,adId.toString)
      //判断是否有今天的数据
      //selectToday(date2String,userId,courseId,flag)
      val insertSingleUser: Int = dao.insertSingleUser(sql,array)
      println("insertSingleUser:"+insertSingleUser)
    })

    //==============================================================================================
    //点击数，点击天数日均使用时长，点击数，日均点击数，点击天数
    val userLine: RDD[((Integer, Integer), TCollect)] = filter.map(line => {
      val userId = line.getUserId
      val courseId: Integer = line.getCourseId
      ((userId, courseId), line)
    })
    val userLines: RDD[((Integer, Integer), Iterable[TCollect])] = userLine.groupByKey()
    val map: RDD[(Integer, Int, Long, Int, Integer)] = userLines.map(line => {

      val userId = line._1._1
      val courseId: Integer = line._1._2
      val it = line._2
      var totalTime: Double = 0.0
      val dateSet = mutable.HashSet[Date]()
      val totalClickCount: Int = it.size //总点击数
      for (line <- it) {
        val time = line.getCreateTime
        val string2Date: Date = TimeUtil.formatString2Date("yyyy-MM-dd", time)
        val startTime = line.getEnterTime
        val endTime = line.getLeaveTime
        var difference: Double = 0.0
        if (StringUtils.isNotEmpty(line.getEnterTime) && StringUtils.isNotEmpty(line.getLeaveTime)) {
          difference = DateTimeUtils.printDifference(startTime, endTime, "yyyy-MM-dd HH:mm:ss")
        }
        totalTime += difference
        dateSet.add(string2Date)
      }
      val dayCount = dateSet.size //天数
      val avgTime = totalTime / dayCount //日均使用时长
      val round: Long = Math.round(avgTime)
      val avgClickCount = totalClickCount / dayCount //日均点击数
      (userId, dayCount, round, avgClickCount, courseId)
    })
    map.foreach(line => {
      //val dao: ITSingleUserDao = DAOFactory.getTSingleUserDao
     /* val sql="update t_SingleUser set QuantumRefluxDay=?," +
        "QuantumBuy=?," +
        "AverageDay=? " +
        "where SingleUserID=? and date(InsertTime)=date_sub(date(now()),interval 1 day)"*/
      val sql="update t_SingleUser set quantum_reflux_day=?," +
        "quantum_buy=?," +
        "average_day=? " +
        "where single_user_id=? and course_id=? and page_type=? and TO_DAYS(now())-TO_DAYS(insert_time)="+dayFlag
      val array=Array[String](line._3.toString,line._4.toString,line._2.toString,line._1.toString,line._5.toString,flag.toString)
      val user: Int = dao.updateSingleUser(sql,array)
      //update
    })
    //=============================================================================================
    //依据多个字段的值对课程/投顾老师所对应的用户排行



  }


}
