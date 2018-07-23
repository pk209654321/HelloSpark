package cn.itcast.action

import java.sql.Date
import java.util

import bean.{AccountDetail, ActivitiesOperation}
import dao.IActivitiesOperationDao
import dao.factory.DAOFactory
import org.apache.commons.lang3.StringUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.collection.{JavaConversions, mutable}

/**
  * Created by lenovo on 2018/7/20.
  */
object ActivityAction {
  def main(args: Array[String]) {
    val spark= SparkSession
      .builder()
      .appName("PhoneDetail")
      //.config("spark.some.config.option", "some-value")
      .master("local[*]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    val dao: IActivitiesOperationDao = DAOFactory.getActivitiesOperationDao
    val sql="select * from t_activities_operation"
    val array: Array[AnyRef] = Array[AnyRef]()
    val operationList: util.List[ActivitiesOperation] = dao.selectActivitiesOperationList(sql,array)
    val scalaBuffer: mutable.Buffer[ActivitiesOperation] = JavaConversions.asScalaBuffer(operationList)
    val parallelize: RDD[ActivitiesOperation] = spark.sparkContext.parallelize(scalaBuffer,2).cache()
    import spark.implicits._
    val activityRdd: RDD[Activity] = parallelize.map(line => {
      val id = line.getId
      val userId = line.getUserId
      val markUser = line.getMarkUser
      val device = line.getDevice
      val pageCode = line.getPageCode
      val produceCode = line.getProduceCode
      val eventId = line.getEventId
      val phone = line.getPhone
      val timestamp = line.getTimestamp
      Activity(id, userId, markUser, device, pageCode, produceCode, eventId, phone, timestamp)
    })
    val activityDF: DataFrame = activityRdd.toDF()
    activityDF.show()
    //activityDF.createOrReplaceTempView("activity")
    ////截至到当前时间各用户来源用户的数量
   /* val userCount: DataFrame = spark.sql("SELECT COUNT(*) count,a.markUser  FROM activity a GROUP BY a.markUser")
    userCount.foreach(line => {
      val count: Long = line.getAs[Long]("count")
      val markUser: Int = line.getAs[Int]("markUser")
      val name=if(markUser==0) "站内" else "站外"
      println("用户数量:"+count+"         用户来源:"+name)
    })
    userCount.show()*/

    //计算每个产品,每个用户来源用户点击数
    //SELECT COUNT(*) 用户数量,IF(a.mark_user=0,'站内','站外') 用户来源,a.product_code FROM t_activities_operation a GROUP BY a.mark_user,a.product_code
  /*  val dataFrame: DataFrame = spark.sql("select count(*) count,if(a.markUser=0,'站内','站外') markUser,a.productCode from activity a group by a.markUser,a.productCode")
    dataFrame.foreach(line => {
      val count = line.getAs[Long]("count")
      val markUser= line.getAs[String]("markUser")
      val produceCode = line.getAs[String]("productCode")
      println("用户数量:"+count+"     "+"用户来源:"+markUser+"      "+"产品:"+produceCode)
    })
    dataFrame.show()*/
    //计算每日活跃用户数量
    //#SELECT COUNT(*) 用户数量,IF(a.mark_user=0,'站内','站外') 用户来源,date(a.`timestamp`) 时间 FROM t_activities_operation a GROUP BY a.mark_user,a.`timestamp`
   /* val dataFrame: DataFrame = spark.sql("SELECT COUNT(*) count,IF(a.markUser=0,'站内','站外') markUser,date(a.timestamp) time FROM activity a GROUP BY a.markUser,a.timestamp order by count")
    dataFrame.collect().foreach(line => {
      val count: Long = line.getAs[Long]("count")
      val markUser: String = line.getAs[String]("markUser")
      val time = line.getAs[Date]("time")
      println("用户数量:"+count+"     "+"用户来源:"+markUser+"      "+"时间:"+time)
    })
    dataFrame.show()*/
    /*#计算每天新增的用户
    SELECT COUNT(*) 用户数量,IF(a.mark_user=0,'站内','站外') 用户来源 FROM t_activities_operation a LEFT JOIN (SELECT phone FROM t_activities_operation WHERE TO_DAYS(NOW())-TO_DAYS(`timestamp`)>=1) b
      ON a.phone=b.phone WHERE b.phone is NULL GROUP BY a.mark_user*/
   /* val dataFrame: DataFrame = spark.sql("SELECT COUNT(DISTINCT a.userId) count,IF(a.markUser=0,'站内','站外') markUser FROM activity a LEFT JOIN (SELECT phone FROM activity WHERE datediff(CURRENT_DATE,timestamp)>=1) b ON a.phone=b.phone WHERE b.phone is NULL GROUP BY a.markUser")
    dataFrame.show()*/
  }
}
//--driver-class-path /usr/local/spark-1.5.2-bin-hadoop2.6/mysql-connector-java-5.1.35-bin.jar \
case class Activity(id:Int,userId:Int,markUser:Int,device:String,pageCode:String,productCode:String,eventId:String,phone:String,timestamp:String)