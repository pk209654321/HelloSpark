package test

import org.apache.spark.{SparkContext, SparkConf}
import util.IpUtil

/**
  * Created by lenovo on 2018/8/20.
  */
object WordCount {
  def main(args: Array[String]) {
    var sparkConf: SparkConf = null
    val local: Boolean = IpUtil.judgeLocal()
    if (local) {
      sparkConf = new SparkConf().setAppName("WinMasterSparkWindow").setMaster("local[*]")
    } else {
      sparkConf = new SparkConf().setAppName("WinMasterSparkWindow")
    }
    val sc: SparkContext = new SparkContext(sparkConf)
    sc.setLogLevel("WARN")
    val textFile = sc.textFile("hdfs://192.168.136.101:9000/word/test.txt")
    textFile.flatMap(_.split("/t")).map((_,1)).reduceByKey(_+_).collect()
  }
}
