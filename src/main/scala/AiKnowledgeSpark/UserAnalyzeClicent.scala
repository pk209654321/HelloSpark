package AiKnowledgeSpark

import java.util.Date
import bean.{AiAccountResigterInfo, AiUserActionInfo}
import org.apache.spark.rdd.RDD
import util.TimeUtil
import util.sparkutil.CombineKeyFunction

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by lenovo on 2018/7/9.
  *
  * @deprecated 根据(用户,时间)粒度分析统计数据
  */
object UserAnalyzeClicent {

  def analyzeUserFlow(parallelize: RDD[AiUserActionInfo],resRdd: RDD[AiAccountResigterInfo],sysFlag:Int): Unit ={

    val mapApple: RDD[AiAccountResigterInfo] = resRdd.filter(line => {
      val sDUA = line.getsDUA()
      val split: Array[String] = sDUA.split("&")
      if (split.size == 11) {
        //SN=IOSCJPH10_GA&VN=102041911&BN=0&VC=APPLE&MO=iPad&RL=768_1024&CHID=1000&LCID=0&RV=&OS=9.3.5&DV=V1
        // SN=ADRCJPH10_GA&VN=100041911&BN=0&VC=HUAWEI&MO=EDI-AL10&RL=1440_2416&CHID=huawei_huawei&LCID=&RV=&OS=Android7.0&DV=V1
        val split1: String = split(3)
        val app= sysFlag match {
          case 0 => true
          case 1 =>false
        }
        split1 match {
          case "APPLE" => app
          case _ => !app
        }
      } else {
        false
      }
    })

    //计算活跃用户
    val dateLine: RDD[(String,AiUserActionInfo)] = parallelize.map(line => {
      val userId = line.getUserId
      val time = line.getCreateTime
      val parse: Date = TimeUtil.getFastTimeFormat.parse(time)
      (time,line)
    })
    val timeLineList: RDD[(String, List[AiUserActionInfo])] = dateLine.combineByKey(
      CombineKeyFunction.createBine[AiUserActionInfo](_),
      CombineKeyFunction.mergeValue[AiUserActionInfo],
      CombineKeyFunction.mergeCombine[AiUserActionInfo]
    )
    val dateUserCount: RDD[(String, Int)] = timeLineList.map(line => {
      val time = line._1
      val list = line._2
      val userSet: mutable.HashSet[String] = mutable.HashSet()
      for (i <- list) {
        val userId: String = i.getUserId
        userSet.add(userId)
      }
      val userCount = userSet.size //当天的活跃用户
      (time, userCount)
    })
    dateUserCount.foreach(line => {
      //insert
    })
    //=================================================================================
    //求得当天的活跃用户
    val distinct: RDD[(String, Int)] = parallelize.map(line => {
      val userId = line.getUserId
      (userId, 1)
    }).distinct()

    val userMapRdd: RDD[(String, Int)] = resRdd.map(line => {
      val accountId = line.getiAccountId()
      (accountId.toString, 1)
    })
    val join: RDD[(String, (Int, Option[Int]))] = distinct.leftOuterJoin(userMapRdd)
    val filterUser: RDD[(String, (Int, Option[Int]))] = join.filter(_._2._2.getOrElse(0)==0)
    filterUser.count()
    //=================================================================================
    //求截止到当天的用户量
    val userRdd: RDD[String] = parallelize.map(line => {
      val userId = line.getUserId
      userId
    })
    val userTotalCount: Long = userRdd.distinct().count()
  }
}
