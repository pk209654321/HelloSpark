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
object ActivityAction1 {
  def main(args: Array[String]) {
    val spark= SparkSession
      .builder()
      .appName("PhoneDetail")
      //.config("spark.some.config.option", "some-value")
      .master("local[*]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    val parallelize: RDD[String] = spark.sparkContext.textFile("e:/people.txt")
    import spark.implicits._
    val activityRdd: RDD[Activity1] = parallelize.map(line => {
      val split: Array[String] = line.split("\t")
      val id = split(0).toInt
      val userId = split(1).toInt
      val markUser = split(2).toInt
      val device = split(3)
      val pageCode = split(4)
      val produceCode = split(5)
      val eventId = split(6)
      val phone = split(7)
      val timestamp = split(8)
      Activity1(id, userId, markUser, device, pageCode, produceCode, eventId, phone, timestamp)
    })
    val activityDF: DataFrame = activityRdd.toDF()
    //activityDF.show(23)
    activityDF.createOrReplaceTempView("activity")
    ////截至到当前时间各用户来源用户的数量
    val userCount: DataFrame = spark.sql("SELECT COUNT(*) count,a.markUser  FROM activity a GROUP BY a.markUser")
    userCount.foreach(line => {
      val count: Long = line.getAs[Long]("count")
      val markUser: Int = line.getAs[Int]("markUser")
      val name=if(markUser==0) "站内" else "站外"
      println("用户数量:"+count+"         用户来源:"+name)
    })
    userCount.show()

  }
}
//--driver-class-path /usr/local/spark-1.5.2-bin-hadoop2.6/mysql-connector-java-5.1.35-bin.jar \
case class Activity1(id:Int,userId:Int,markUser:Int,device:String,pageCode:String,productCode:String,eventId:String,phone:String,timestamp:String)