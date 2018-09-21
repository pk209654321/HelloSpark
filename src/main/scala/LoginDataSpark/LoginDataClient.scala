package LoginDataSpark

import java.util
import java.util.Date

import _root_.util.{IpUtil, TimeUtil}
import bean.AccountDetail
import bean.collect.TotalUserActionInfo
import bean.login.{LoginData, LoginResult}
import conf.MapperFactory
import constant.UserActionConstants
import dao.factory.SqlSessionFactory
import mapper.{AccountDetailMapper, TotalUserActionInfoMapper, LoginDataMapper, LoginResultMapper}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.{JavaConversions, mutable}

/**
  * Created by lenovo on 2018/9/4.
  */
object LoginDataClient {
  def main(args: Array[String]) {
    val range = args(0).toInt
    val parNum: Int = args(1).toInt
      val local: Boolean = IpUtil.judgeLocal()
      var sparkConf: SparkConf = null
      if (local) {
        sparkConf = new SparkConf().setAppName("LoginDataClient").setMaster("local[*]")
      } else {
        sparkConf = new SparkConf().setAppName("LoginDataClient")
      }
      val sc: SparkContext = new SparkContext(sparkConf)
      sc.setLogLevel("WARN")
    //val business: LoginDataMapper = MapperFactory.createMapper(classOf[LoginDataMapper], "business")
    for (dayFlag <- (1 to range).reverse) {
      val map= new util.HashMap[String,Object]()
      map.put("dayFlag",dayFlag.toString)//查询当天的时间
      //业务用户详情表查询
      println("------------------业务数据查询")
      val mapper: AccountDetailMapper = SqlSessionFactory.getAccountDetailMapper
      val detailList: util.List[AccountDetail] = mapper.selectAccountDetailList(map)
      val detail: mutable.Buffer[AccountDetail] = JavaConversions.asScalaBuffer(detailList)
      val detailRDD: RDD[AccountDetail] = sc.parallelize(detail,parNum)

      //行为数据分析查询
      println("------------------行为数据查询")
      val loginResultMapper: LoginResultMapper = SqlSessionFactory.getLoginResultMapper
      val userActionInfoMapper: TotalUserActionInfoMapper = SqlSessionFactory.getTotalUserActionInfoMapper
      // map.put("businessSource",UserActionConstants.BUSINESS_SOURCE_ACK)
      val infoList: util.List[TotalUserActionInfo] = userActionInfoMapper.selectTotalUserActionInfoList(map)
      val scalaBuffer: mutable.Buffer[TotalUserActionInfo] = JavaConversions.asScalaBuffer(infoList)
      val parallelize: RDD[TotalUserActionInfo] = sc.parallelize(scalaBuffer,parNum)
      analyseUserData(dayFlag,parallelize,loginResultMapper,detailRDD: RDD[AccountDetail])
    }
  }

  /*
  * 分析用户行为数据,登录情况.
  * */
  def analyseUserData(dayFlag:Int,parallelize: RDD[TotalUserActionInfo],loginResultMapper: LoginResultMapper,detailRDD: RDD[AccountDetail])={
    //val filter: RDD[TotalUserActionInfo] = parallelize.filter(_.getBusiness_source==2)//获得用户行为轨迹数据
    val map: RDD[(Integer, TotalUserActionInfo)] = parallelize.map(line => {
      val user_id: Integer = line.getUser_id
      (user_id, line)
    })
    val idPhoneRdd: RDD[(Integer, String)] = detailRDD.map(line => {
      val accountId: Integer = line.getiAccountId()
      val phone: String = line.getsPhone()
      (accountId, phone)
    })
    val join: RDD[(Integer, (TotalUserActionInfo, String))] = map.join(idPhoneRdd)

    val reduceByKey: RDD[((Integer, String, Date), Int)] = join.map(line => {
      val userId: Integer = line._1
      val userInfo: TotalUserActionInfo = line._2._1
      val create_time: String = userInfo.getCreate_time
      val string2Date: Date = TimeUtil.formatString2Date("yyyy-MM-dd HH", create_time)
      val phone: String = line._2._2
      ((userId, phone, string2Date), 1)
    }).reduceByKey(_ + _)
    reduceByKey.foreach(line => {
      val infoes: util.ArrayList[LoginResult] = new util.ArrayList[LoginResult]()
      val info: LoginResult = new LoginResult
      val userId: Integer = line._1._1
      val date: Date = line._1._3
      val count: Int = line._2
      val phone: String = line._1._2
      info.setInsert_time(date)
      info.setLogin_count(count)
      info.setUser_id(userId)
      info.setPhone(phone)
      infoes.add(info)
      val loginResultMapper: LoginResultMapper = SqlSessionFactory.getLoginResultMapper
      val insertResult: Int = loginResultMapper.insertLoginResult(infoes)
      println("insert结果"+insertResult)
     /* val updateResult: Int = loginResultMapper.updateLoginResult(info)
      println("update结果" + updateResult)*/
    })
  }
}
