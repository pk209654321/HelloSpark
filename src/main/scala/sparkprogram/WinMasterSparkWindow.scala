package sparkprogram

import java.util
import java.util.Date

import _root_.util.{IpUtil, TimeUtil}
import bean.{SingleUser, TCollect}
import constant.Constants
import dao.{ITSingleUserDao, ITCollectDao}
import dao.factory.DAOFactory
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.{JavaConversions, mutable}


/**
  * Created by lenovo on 2018/6/27.
  */
object WinMasterSparkWindow {
  def main(args: Array[String]) {
    val range = args(0).toInt
    for (dayFlag <- (1 to range).reverse) {
      println("查询的类容范围偏移量offset：" + dayFlag)
      println("查询的类容范围偏移量offset：" + dayFlag)
      println("查询的类容范围偏移量offset：" + dayFlag)
      val local: Boolean = IpUtil.judgeLocal()
      var sparkConf: SparkConf = null
      if (local) {
        sparkConf = new SparkConf().setAppName("WinMasterSparkWindow").setMaster("local[*]")
      } else {
        sparkConf = new SparkConf().setAppName("WinMasterSparkWindow")
      }
      val sc: SparkContext = new SparkContext(sparkConf)
      sc.setLogLevel("WARN")
      val sql = "select * from t_collect where TO_DAYS(NOW()) - TO_DAYS(create_time) >=" + dayFlag
      val array = Array[String]()
      val tCollectDao: ITCollectDao = DAOFactory.getTCollectDao
      val collectList: util.List[TCollect] = tCollectDao.selectTCollectList(sql, array)
      //java=>scala
      val scalaBuffer: mutable.Buffer[TCollect] = JavaConversions.asScalaBuffer(collectList)
      val parallelize: RDD[TCollect] = sc.parallelize(scalaBuffer, 10).cache()
      //获取相应的数据
      //app,sourse=0
      val filterApp: RDD[TCollect] = parallelize.filter(_.getSource == 0)
      //微信,sourse=1
      val filterWei: RDD[TCollect] = parallelize.filter(_.getSource == 1)
      //安卓,device=1
      val filterAnd: RDD[TCollect] = parallelize.filter(_.getDevice == 1)
      //ios,device=2
      val filterIos: RDD[TCollect] = parallelize.filter(_.getDevice == 2)

      //执行app 0
      runJob(filterApp, Constants.APP_FLAG, dayFlag)
      //执行微信 1
      runJob(filterWei, Constants.WEIXIN_FLAG, dayFlag)
      //执行安卓 2
      runJob(filterAnd, Constants.AND_FLAG, dayFlag)
      //执行ios 3
      runJob(filterIos, Constants.IOS_FLAG, dayFlag)
      //执行汇总 4
      runJob(parallelize, Constants.TOTAL_FLAG, dayFlag)

      sc.stop()
      val stopped: Boolean = sc.isStopped
      println("任务是否执行结束" + stopped)
    }
  }
   // }
  def runJob(parallelize: RDD[TCollect],flag:Int,dayFlag:Int): Unit ={
    //collect
    CollectAppOrWeiXin.doSparkAppOrWeiXin(parallelize,flag,dayFlag)
    //singleUser
    SingleUserClient.doSingleUser(parallelize,flag,dayFlag)
    //SpecialList
    SpecialListClient.doSpecialList(parallelize,flag,dayFlag)
     //筛选用户: 过滤掉app和汇总类型
     /*if (flag!=Constants.APP_FLAG&&flag!=Constants.TOTAL_FLAG){
       ScreenUserInfoClient.doScreenUserInfo(parallelize,flag,dayFlag)
     }*/
  }
  //(x-min) / (max-min)数据归一化处理
  def normalizedFunction(num:Int,min:Int,max:Int): Int ={
    num-min/max-min
  }

  def rankByThreeField(): Unit ={

  }
}






















