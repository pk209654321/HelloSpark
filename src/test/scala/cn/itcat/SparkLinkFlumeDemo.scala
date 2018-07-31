package cn.itcat

import java.net.InetSocketAddress

import org.apache.log4j.Level
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}

import scala.Predef

/**
  * Created by lenovo on 2018/7/25.
  */
object SparkLinkFlumeDemo {
  def main(args: Array[String]) {
    MyLog.setLogLeavel(Level.WARN)
    val conf = new SparkConf().setAppName("SparkLinkFlumeDemo").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(10))
    val addrs = Seq(new InetSocketAddress("192.168.136.101", 44444))
    val ds = FlumeUtils.createPollingStream(ssc, addrs, StorageLevel.MEMORY_ONLY)
    sc.setCheckpointDir("e:/flumeTmp")
   /* val res = ds.flatMap(x => {
      new String(x.event.getBody.array()).split(" ")
    }).map((_, 1)).updateStateByKey(myFunc, new HashPartitioner(sc.defaultParallelism), true)
    res.print()
    ssc.start()*/

    val reduceByKey: DStream[(String, Int)] = ds.flatMap(line => {
      new Predef.String(line.event.getBody.array()).split(" ")
    }).map((_, 1)).reduceByKey(_ + _)
    reduceByKey.print()
    ssc.start()
    ssc.awaitTermination()
  }

  def myFunc = (it: Iterator[(String, Seq[Int], Option[Int])]) => {
    it.map(x => {
      (x._1, x._2.sum + x._3.getOrElse(0))
    })
  }
}
