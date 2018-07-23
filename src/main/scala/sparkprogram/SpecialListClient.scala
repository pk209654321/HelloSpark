package sparkprogram

import java.util.Date

import bean.TCollect
import dao.factory.DAOFactory
import org.apache.commons.lang3.time.DateUtils
import org.apache.spark.rdd.RDD
import util.TimeUtil

import scala.collection.mutable

/**
  * Created by lenovo on 2018/7/2.
  *
  * @deprecated 按照(投顾,时间)的粒度分析统计数据
  */
object SpecialListClient {

  val dao=DAOFactory.getTSpecialistDao
  def doSpecialList(parallelize: RDD[TCollect],flag:Int,dayFlag:Int): Unit ={
    //过滤出投顾的数据
    val filter: RDD[TCollect] = parallelize.filter(line => {
      val adId: Integer = line.getAdviserId
      if (adId!=null) {
        true
      } else {
        false
      }
    })
    //过滤出来当天的数据
    val currentData: RDD[TCollect] = filter.filter(line => {
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
    //(时间,投顾) line 粒度分析
    val map: RDD[((Date, Integer), TCollect)] = currentData.map(line => {
      val adviserId = line.getAdviserId
      val time = TimeUtil.formatString2Date("yyyy-MM-dd", line.getCreateTime)
      ((time, adviserId), line)
    })
    val groupByKey: RDD[((Date, Integer), Iterable[TCollect])] = map.groupByKey()
    val insertData: RDD[(Integer, Int, Int, Int, Date)] = groupByKey.map(line => {
      val it = line._2
      val adviserId = line._1._2
      val date = line._1._1
      val daySize = it.size //点击数
      //val set=Set[Int]()
      val set = new mutable.HashSet[Int]()
      for (i <- it) {
        val userId = i.getUserId
        set.add(userId)
      }
      val userCount: Int = set.size //用户数
      val avgValue = daySize / userCount //人均点击数 ---当天点击数/用户数
      (adviserId, daySize, avgValue, userCount, date)
     //(投顾id,当天的点击数,人均点击数,当天用户数,时间)
    })
    insertData.foreach(line => {
      val adId=line._1
      val date: Date = line._5
      val date2String: String = TimeUtil.getDate2String("yyyy-MM-dd",date)
      //val fac= DAOFactory.getTSpecialistDao
      //ExpertClick,ExpertDailyClick,ExpertUser,AdId,insertTime
     /* val nextDate: Date = TimeUtil.getNextDate(new Date(), -dayFlag)
      val string: String = TimeUtil.getDate2String("yyyy-MM-dd",nextDate)*/
      val sql="insert t_Specialist (expert_click,expert_daily_click,expert_user,ad_id,insert_time,page_type)" +
        "values(?,?,?,?,?,?)"
      val array= Array[String](line._2.toString,line._3.toString,line._4.toString,line._1.toString,date2String,flag.toString)
      val specialist: Int = dao.insertTSpecialist(sql,array)
        println("insert---t_Specialist--"+specialist)
    })
    //================================================================================
    //求截至到当天的总用户数量
    val adUserId: RDD[(Integer, Integer)] = parallelize.map(line => {
      val adId = line.getAdviserId
      val userId = line.getUserId
      (adId, userId)
    })
    val adUserIdUserList: RDD[(Integer, Iterable[Integer])] = adUserId.groupByKey()
    val updateUserCountData: RDD[(Integer, Int)] = adUserIdUserList.map(line => {
      val adId = line._1
      val it = line._2
      val set = mutable.HashSet[Int]()
      for (elem <- it) {
        set.add(elem)
      }
      val size = set.size //累计用户数量
      (adId, size)
    })
    updateUserCountData.foreach(line => {
      val fac= DAOFactory.getTSpecialistDao
      //update t_Specialist set ExpertAggregateUser=? " +
     // "where AdId=? and TO_DAYS(InsertTime)=TO_DAYS(date_sub(now(),interval 1 day))
      val sql="update t_Specialist set expert_aggregate_user=? " +
        "where ad_id=? and TO_DAYS(now())-TO_DAYS(insert_time)="+dayFlag
      val array= Array[String](line._2.toString,line._1.toString)
      val specialist: Int = fac.updateTSpecialist(sql,array)
      println("update--"+specialist)
    })
    //================================================================================
    //新增用户数量
    val adUserDate: RDD[((Integer, Integer), Date)] = parallelize.map(line => {
      val adId = line.getAdviserId //投资顾问Id
      val userId = line.getUserId
      val time: String = line.getCreateTime
      val formatString2Date: Date = TimeUtil.formatString2Date("yyyy-MM-dd", time)
      ((adId, userId), formatString2Date)
    })
    val asUserDateList: RDD[((Integer, Integer), Iterable[Date])] = adUserDate.groupByKey()
    val adDateOne: RDD[((Integer, Date), Int)] = asUserDateList.map(line => {
      val adId = line._1._1
      val userId = line._1._2
      val it = line._2
      val sortSet = mutable.SortedSet[Date]()
      for (date <- it) {
        sortSet.add(date)
      }
      ((adId, sortSet.head), 1)
      //(投顾Id,用户第一次访问的时间),1
    })
    val reduceByKey: RDD[((Integer, Date), Int)] = adDateOne.reduceByKey(_+_)
    //过滤数据留下当天的新增用户
    val currentDayIncrease: RDD[((Integer, Date), Int)] = reduceByKey.filter(line => {
      val date: Date = line._1._2
      //求当天的时间
      val nextDate: Date = TimeUtil.getNextDate(new Date(), -dayFlag)
      val sameDay: Boolean = DateUtils.isSameDay(date, nextDate)
      if (sameDay) {
        true
      } else {
        false
      }
    })
    currentDayIncrease.foreach(line => {
      //println(line._1._1+"date---"+line._1._2)
      val date=line._1._2
      val date2String: String = TimeUtil.getDate2String("yyyy-MM-dd",date)
      //val fac= DAOFactory.getTSpecialistDao
      // val sql="update t_Specialist set ExpertNewUser=? " +
      //"where AdId=? and TO_DAYS(InsertTime)=TO_DAYS(date_sub(now(),interval 1 day))"
      val sql="update t_Specialist set expert_new_user=? " +
        "where ad_id=? and insert_time=? and page_type=?"
      val array= Array[String](line._2.toString,line._1._1.toString,date2String,flag.toString)
      dao.updateTSpecialist(sql,array)
    })
  }

  def selectDaySpecial(date2String:String,adId:Int): Int ={
    val sqlSelect="select * from t_Specialist where insert_time=? and ad_id=?"
    val arraySelect: Array[String] = Array[String](date2String,adId.toString)
    val row: Int = dao.selectTSpecialist(sqlSelect,arraySelect)
    row
  }

}
