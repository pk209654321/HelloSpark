package LoginDataSpark

import java.util
import java.util.Date

import _root_.util.{TimeUtil, IpUtil}
import bean.login.{LoginResult, LoginData}
import dao.factory.SqlSessionFactory
import mapper.{LoginResultMapper, LoginDataMapper}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.{mutable, JavaConversions}

/**
  * Created by lenovo on 2018/9/4.
  */
object LoginDataClientBak {
  def main(args: Array[String]) {
    val range = args(0).toInt

      val local: Boolean = IpUtil.judgeLocal()
      var sparkConf: SparkConf = null
      if (local) {
        sparkConf = new SparkConf().setAppName("LoginDataClient").setMaster("local[*]")
      } else {
        sparkConf = new SparkConf().setAppName("LoginDataClient")
      }
      val sc: SparkContext = new SparkContext(sparkConf)
      sc.setLogLevel("WARN")
    for (dayFlag <- (1 to range).reverse) {
      val loginDataMapper: LoginDataMapper = SqlSessionFactory.getLoginDataMapper
      val loginResultMapper: LoginResultMapper = SqlSessionFactory.getLoginResultMapper
      val map= new util.HashMap[String,Object]()
      map.put("dayFlag",dayFlag.toString)//查询当天的时间
      val dataList: util.List[LoginData] = loginDataMapper.selectLoginDataList(map)
      val scalaBuffer: mutable.Buffer[LoginData] = JavaConversions.asScalaBuffer(dataList)
      val parallelize: RDD[LoginData] = sc.parallelize(scalaBuffer,10)
      analyseLoginData(dayFlag,parallelize,loginResultMapper)
    }
  }

  def analyseLoginData(dayFlag:Int,parallelize: RDD[LoginData],loginResultMapper: LoginResultMapper): Unit ={
    parallelize.map(line => {
      val user_id: Integer = line.getUser_id
      val start_time: String = line.getStart_time
      val string2Date: Date = TimeUtil.formatString2Date("yyyy-MM-dd HH", start_time)
      ((user_id, string2Date), 1)
    }).reduceByKey(_+_).foreachPartition(lines=> {
      lines.foreach(line => {
        val results = new util.ArrayList[LoginResult]()
        val result: LoginResult = new LoginResult
        val userId: Integer = line._1._1
        val date: Date = line._1._2
        val count: Int = line._2
        result.setInsert_time(date)
        result.setLogin_count(count)
        result.setUser_id(userId)
        results.add(result)
        val loginResultMapper: LoginResultMapper = SqlSessionFactory.getLoginResultMapper
        val result1: Int = loginResultMapper.insertLoginResult(results)
        println("result1:"+result1)

      })
    })

  }


}
