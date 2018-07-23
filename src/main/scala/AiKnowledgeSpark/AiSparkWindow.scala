package AiKnowledgeSpark

import java.util

import _root_.util.{TimeUtil, IpUtil}
import bean.{AiAccountResigterInfo, AiUserActionInfo}
import dao.{IAiAccountResigterInfoDao, IUserActionInfoDao}
import dao.factory.DAOFactory
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.{mutable, JavaConversions}

/**
  * Created by lenovo on 2018/7/9.
  */
object AiSparkWindow {
  //判断是不是本地测试
  def main(args: Array[String]) {
    val local: Boolean = IpUtil.judgeLocal()
    var sparkConf:SparkConf=null
    if (local){
      sparkConf = new SparkConf().setAppName("AiSparkWindow").setMaster("local[*]")
    }else{
      sparkConf=new SparkConf().setAppName("AiSparkWindow")
    }
    val sc: SparkContext = new SparkContext(sparkConf)
    sc.setLogLevel("WARN")
    val dao: IUserActionInfoDao = DAOFactory.getUserActionInfoDao
    val resigterInfoDao: IAiAccountResigterInfoDao = DAOFactory.getAccountResigterInfoDao
    val sql="select * from user_acton_info"
    val sqlResigter="select * from t_account_resigter_info " +
      "where TO_DAYS(Now())-TO_DAYS(from_unixtime(time))=2"
    val arraySelect= Array[String]()
    val arrayRes=Array[String]()
    val resigterInfoList: util.List[AiAccountResigterInfo] = resigterInfoDao.selectAiAccountResigterInfoList(sql,arrayRes)
    //用户字典
    val resList: mutable.Buffer[AiAccountResigterInfo] = JavaConversions.asScalaBuffer(resigterInfoList)
    val resRdd: RDD[AiAccountResigterInfo] = sc.parallelize(resList).cache()
    val infoList: util.List[AiUserActionInfo] = dao.selectUserActionInfoList(sql,arraySelect)
    val scalaBuffer: mutable.Buffer[AiUserActionInfo] = JavaConversions.asScalaBuffer(infoList)
    //用户行为
    val parallelize: RDD[AiUserActionInfo] = sc.parallelize(scalaBuffer).cache()
    val filterApple: RDD[AiUserActionInfo] = parallelize.filter(_.getOperatType=="0")
    val filterAndroid: RDD[AiUserActionInfo] = parallelize.filter(_.getOperatType=="1")
    // user苹果数据  0
    UserAnalyzeClicent.analyzeUserFlow(filterApple,resRdd,0)
    // user安卓数据  1
    UserAnalyzeClicent.analyzeUserFlow(filterAndroid,resRdd,1)
    //板块苹果数据  0
    PlateAnalyzeClicent.analyzePlateFlow(filterApple,0)
    //板块安卓数据  1
    PlateAnalyzeClicent.analyzePlateFlow(filterAndroid,1)
    sc.stop()
  }
}
