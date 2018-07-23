package cn.itcast

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.mutable

/**
  * Created by lenovo on 2018/6/25.
  */
object WordCount {
  def main(args: Array[String]) {
    //非常重要，是通向Spark集群的入口
    val conf = new SparkConf().setAppName("WC")
      .setMaster("local[*]")
    val sc = new SparkContext(conf)
    // ALL,DEBUG,ERROR,FATAL,INFO,OFF,TRACE,WARN
    sc.setLogLevel("WARN")
    val toList: List[(String, Int)] = sc.textFile("e://testdata/t.txt").flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
      .collect().toList
    println(toList)
    sc.stop()


  }
}
