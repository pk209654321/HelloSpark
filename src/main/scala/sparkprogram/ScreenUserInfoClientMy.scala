package sparkprogram

import java.util.Date

import bean.{ScreeningUserInfo, TCollect}
import dao.factory.{SqlSessionFactory, DAOFactory}
import dao.{IScreeningAdInfoDao, IScreeningCourseInfoDao, IScreeningUserInfoDao}
import mapper.{ScreeningUserInfoMapper, RoleMapper}
import org.apache.commons.lang3.time.DateUtils
import org.apache.spark.rdd.RDD
import util.TimeUtil

/**
  * Created by lenovo on 2018/8/2.
  */
object ScreenUserInfoClientMy {
//  private val userDao: IScreeningUserInfoDao = DAOFactory.getScreeningUserDao
//  private val adDao: IScreeningAdInfoDao = DAOFactory.getScreeningAdInfoDao
//  private val courseDao: IScreeningCourseInfoDao = DAOFactory.getScreeningCourseInfoDao
  private val userMapper: ScreeningUserInfoMapper = SqlSessionFactory.getScreeningUserInfoMapper
  def doScreenUserInfo(parallelize: RDD[TCollect],flag:Int,dayFlag:Int): Unit ={

    val filter: RDD[TCollect] = parallelize.filter(line => {
      //过滤掉其他时间数据,保留当天数据
      val time: String = line.getCreateTime
      //求当天的数据时间
      val date: Date = TimeUtil.getFastTimeFormat.parse(time)
      val nextDate: Date = TimeUtil.getNextDate(new Date(), -dayFlag)
      val sameDay: Boolean = DateUtils.isSameDay(date, nextDate)
      if (sameDay) {
        true
      } else {
        false
      }
    })
    val fileterCache: RDD[TCollect] = filter.cache()
    //对用户分析
    doScreenUser(fileterCache,flag,dayFlag)
    //对投顾ad分析
    //doScreenAdInfo(fileterCache,flag,dayFlag)
    //对课程course分析
    //doScreenCourseInfo(fileterCache,flag,dayFlag)
  }

  def doScreenUser(parallelize: RDD[TCollect],flag:Int,dayFlag:Int): Unit ={
    val userIdOneTime: RDD[(Integer, (Int, Double))] = parallelize.map(line => {
      val userId = line.getUserId
      val startTime: String = line.getEnterTime
      val endTime: String = line.getLeaveTime
      val difference: Double = TimeUtil.printDifference(startTime, endTime, "yyyy-MM-dd HH:mm:ss")
      (userId, (1, difference))
    })
    val userIdCountTime: RDD[(Integer, (Int, Double))] = userIdOneTime.reduceByKey((x, y) => {
      var userOneAfter: Int = x._1
      val userOnePre: Int = y._1
      var userTimeAfter: Double = x._2
      val userTimePre: Double = y._2
      (userOneAfter + userOnePre, userTimeAfter + userTimePre)
    })
    val sortByUser: RDD[(Integer, (Int, Double))] = userIdCountTime.sortBy(_._2._1,false)
    sortByUser.collect().foreach(line => {
      val userId: Integer = line._1
      val userCount: Int = line._2._1
      val userTotalTime: Double = line._2._2
      val round: Long = Math.round(userTotalTime*60)//四舍五入后的时间
      val user=new ScreeningUserInfo()
      user.setUserId(userId)
      user.setOperationCount(userCount)
      user.setUserOnlineTime(round)
      user.setPageType(flag)
      user.setDayFlag(dayFlag)
      val info = userMapper.insertScreeningUserInfo(user)
      println("user insert result:"+info)
    })
  }

  //按照投顾力度分析
  def doScreenAdInfo(parallelize: RDD[TCollect],flag:Int,dayFlag:Int): Unit ={
    val filter: RDD[TCollect] = parallelize.filter(_.getAdviserId!=null)//将投顾为空的过滤掉
    val userIdAdIdOneTime: RDD[((Integer, Integer), (Int, Double))] = filter.map(line => {
      val userId = line.getUserId
      val adId = line.getAdviserId
      val startTime = line.getEnterTime
      val endTime = line.getLeaveTime
      val difference = TimeUtil.printDifference(startTime, endTime, "yyyy-MM-dd HH:mm:ss") * 60 //精确到秒
      ((userId, adId), (1, difference))
    })
    val userIdAdIdCountTime: RDD[((Integer, Integer), (Int, Double))] = userIdAdIdOneTime.reduceByKey((x, y) => {
      val afterOne: Int = x._1
      val preOne: Int = y._1
      val timeAfter: Double = x._2
      val timePre: Double = y._2
      (afterOne + preOne, timeAfter + timePre)
    })
    val sortByAd: RDD[((Integer, Integer), (Int, Double))] = userIdAdIdCountTime.sortBy(_._2._1,false)
    sortByAd.collect().foreach(line => {
      val userId: Integer = line._1._1
      val adId: Integer = line._1._2
      val adCount: Int = line._2._1
      val totalTime = line._2._2
    })
  }


  //根据课程粒度
  def doScreenCourseInfo(parallelize: RDD[TCollect],flag:Int,dayFlag:Int): Unit ={
    val filter: RDD[TCollect] = parallelize.filter(_.getCourseId!=null)
    val userIdCourseIdOneTime: RDD[((Integer, Integer), (Int, Double))] = filter.map(line => {
      val userId = line.getUserId
      val courseId = line.getCourseId
      val startTime = line.getEnterTime
      val endTime = line.getLeaveTime
      val difference = TimeUtil.printDifference(startTime, endTime, "yyyy-MM-dd HH:mm:ss") * 60 //精确到秒
      ((userId, courseId), (1, difference))
    })
    val userCourseCountTime: RDD[((Integer, Integer), (Int, Double))] = userIdCourseIdOneTime.reduceByKey((x, y) => {
      val oneAfter: Int = x._1
      val onePre: Int = y._1
      val timeAfter: Double = x._2
      val timePre: Double = y._2
      (oneAfter + onePre, timeAfter + timePre)
    })
    val sortByCourse: RDD[((Integer, Integer), (Int, Double))] = userCourseCountTime.sortBy(_._2._1,false)
    sortByCourse.collect().foreach(line => {
      val userId: Integer = line._1._1
      val courseId: Integer = line._1._2
      val adCount: Int = line._2._1
      val totalTime: Double = line._2._2
    })
  }
}
