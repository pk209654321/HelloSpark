package cn.itcast.action

import java.util

import _root_.util.IpUtil
import bean.{Info, AccountDetail}
import dao.{IInfoDao, IAccountDetailDao}
import dao.factory.DAOFactory
import _root_.jdbc.JDBCHelper
import org.apache.commons.lang3.StringUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._

import scala.collection.{mutable, JavaConversions}

/**
  * Created by lenovo on 2018/7/12.
  */
object PhoneDetail {
  def main(args: Array[String])= {
  /*  val local: Boolean = IpUtil.judgeLocal()
    var sparkConf:SparkConf=null
    if(local){
      sparkConf= new SparkConf().setAppName("PhoneDetail").setMaster("local[*]")
    }else{
      sparkConf=new SparkConf().setAppName("PhoneDetail")
    }
    val sc: SparkContext = new SparkContext(sparkConf)*/
    val spark= SparkSession
      .builder()
      .appName("PhoneDetail")
      .config("spark.some.config.option", "some-value")
      .master("local[*]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    import spark.implicits._
    val detailDF: DataFrame = getDetailDF(spark)
    detailDF.show()
    val infoDF: DataFrame = getInfoDF(spark)
    infoDF.show()
    detailDF.createOrReplaceTempView("detail")
    infoDF.createOrReplaceTempView("info")
    val sql: DataFrame = spark.sql("select " +
      "i.id iId," +
      "i.infoSource," +
      "d.source," +
      "d.id dId," +
      "d.userName," +
      "d.timeStr," +
      "d.phone " +
      "from detail d left join info i " +
      "on d.id=i.id ")
    val filter: Dataset[Row] = sql.filter(line => {
      val string: String = line.getString(2)
      StringUtils.isNotEmpty(string)
    })
    filter.show()
    filter.foreachPartition(line => {
      val line1: Iterator[Row] = line

    })
   filter.foreachPartition(it=> {
      it.foreach(line => {
        val iId: Int = line.getAs[Int]("iId")
        val infoSource: String = line.getAs[String]("infoSource")
        val source: String = line.getAs[String]("source")
        val dId: Int = line.getAs[Int]("dId")
        val userName: String = line.getAs[String]("userName")
        val phone: String = line.getAs[String]("phone")
        val timeStr: String = line.getAs[String]("timeStr")
        val dao: IInfoDao = DAOFactory.getInfoDao
        //insert
        val sqlInsert="insert into info (accountId,userName,phone,source,timeStr) values (?,?,?,?,?)"
        val arrayInsert=Array[AnyRef](dId.toString,userName,phone,source,timeStr)
        //update
        val sqlUpdate="update info set source=? where accountId=?"
        val arrayUpdate=Array[String](source,dId.toString)
        if(iId==0){
          dao.insertInfoList(sqlInsert,arrayInsert)
        }else{
          if(infoSource==null){
            dao.updateInfoList(sqlUpdate,arrayUpdate)
          }
        }
      })
    })
  }

  def getDetailDF(spark:SparkSession):DataFrame ={
    val accountDetailDao: IAccountDetailDao = DAOFactory.getAccountDetailDao
    val sql="select *,FROM_UNIXTIME(t.iGenTimeStamp) timeStr from t_account_detail t"
    val arrayDetail= Array[String]()
    val detaiList: util.List[AccountDetail] = accountDetailDao.selectAccountDetaiList(sql,arrayDetail)
    val scalaBuffer: mutable.Buffer[AccountDetail] = JavaConversions.asScalaBuffer(detaiList)
    val parallelize: RDD[AccountDetail] = spark.sparkContext.parallelize(scalaBuffer,2)
    val deailRdd: RDD[Detail] = parallelize.map(line => {
      val id = line.getiAccountId()
      val userName = line.getsUserName()
      val phone = line.getsPhone()
      val source: String = line.getsSource()
      val stamp: String = line.getTimeStr
      Detail(id,userName, phone, source,stamp,"")
    })
    import spark.implicits._
    val detailDF: DataFrame = deailRdd.toDF()
    detailDF
  }
  def getInfoDF(spark:SparkSession): DataFrame ={
    val dao: IInfoDao = DAOFactory.getInfoDao
    val infoList: util.List[Info] = dao.selectInfoList()
    val scalaBuffer: mutable.Buffer[Info] = JavaConversions.asScalaBuffer(infoList)
    val parallelize: RDD[Info] = spark.sparkContext.parallelize(scalaBuffer,2)
    val map: RDD[AccountInfo] = parallelize.map(line => {
      val id = line.getAccountId
      val userName = line.getUserName
      val phone = line.getPhone
      val source: String = line.getSource
      AccountInfo(id.toInt,source)
    })
    import spark.implicits._
    map.toDF()
  }
}
case class Detail (id:Int,userName:String,phone:String,source:String,timeStr:String,infoSource:String)
case class AccountInfo(id:Int,infoSource:String)