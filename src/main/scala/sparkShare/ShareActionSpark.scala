package sparkShare

import java.util
import java.util.Date

import _root_.util.{TimeUtil, IpUtil}
import bean.AccountDetail
import bean.collect.TotalUserActionInfo
import bean.share.ShareBusiness
import com.alibaba.fastjson.JSON
import constant.UserActionConstants
import dao.factory.SqlSessionFactory
import mapper.{TotalUserActionInfoMapper, LoginResultMapper, AccountDetailMapper}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.{JavaConversions, mutable}

/**
  * Created by lenovo on 2018/9/17.
  */



object ShareActionSpark {
  def main(args: Array[String]) {
    val range = args(0).toInt
    val parNum: Int = args(1).toInt
    val local: Boolean = IpUtil.judgeLocal()
    var sparkConf: SparkConf = null
    if (local) {
      sparkConf = new SparkConf().setAppName("ShareActionSpark").setMaster("local[*]")
    } else {
      sparkConf = new SparkConf().setAppName("ShareActionSpark")
    }
    val sc: SparkContext = new SparkContext(sparkConf)
    sc.setLogLevel("WARN")
    //val business: LoginDataMapper = MapperFactory.createMapper(classOf[LoginDataMapper], "business")
    for (dayFlag <- (1 to range).reverse) {
      val map = new util.HashMap[String, Object]()
      map.put("dayFlag", dayFlag.toString) //查询当天的时间

      //获取业务数据
      val detailMapper: AccountDetailMapper = SqlSessionFactory.getAccountDetailMapper
      val sduaList: util.List[AccountDetail] = detailMapper.selectSduaList(map)
      val sduaBuffer: mutable.Buffer[AccountDetail] = JavaConversions.asScalaBuffer(sduaList)
      val sduaRdd: RDD[AccountDetail] = sc.parallelize(sduaBuffer, 10)
      //获取行为数据
      val mapper: TotalUserActionInfoMapper = SqlSessionFactory.getTotalUserActionInfoMapper
      val infoList: util.List[TotalUserActionInfo] = mapper.selectTotalUserActionInfoList(map)
      val scalaBuffer: mutable.Buffer[TotalUserActionInfo] = JavaConversions.asScalaBuffer(infoList)
      val parallelize: RDD[TotalUserActionInfo] = sc.parallelize(scalaBuffer, 10)

    }
  }

  def analyzeShareAction(parallelize: RDD[TotalUserActionInfo], sduaRdd: RDD[AccountDetail]): Unit = {
    //过滤出来分享回流数据
    val filter: RDD[TotalUserActionInfo] = parallelize.filter(_.getBusiness_source == UserActionConstants.BUSINESS_SOURCE_SHARE)
    //行为数据
    val userIdLine: RDD[(Integer, TotalUserActionInfo)] = filter.map(line => {
      val user_id = line.getUser_id
      (user_id, line)
    })
    //app渠道数据
    val accountIdChVal: RDD[(Integer, String)] = sduaRdd.map(line => {
      val accountId = line.getiAccountId()
      val sDUA = line.getsDUA()
      var chVal: String = null
      if (sDUA != null) {
        val split: Array[String] = sDUA.split("&")
        val channel: String = split(6)
        val channelVals: Array[String] = channel.split("=")
        chVal = channelVals(1).replaceAll("-", "").trim
      }
      (accountId, chVal)
    })
    val join: RDD[(Integer, (TotalUserActionInfo, Option[String]))] = userIdLine.leftOuterJoin(accountIdChVal)
    val allMap: RDD[((Date, Integer, Integer, Integer, String, Integer, Integer, String), (String, Integer))] = join.map(line => {
      val user_id: Integer = line._1
      val option: Option[String] = line._2._2
      val orElse: String = option.getOrElse(null) //APP渠道
      val info: TotalUserActionInfo = line._2._1
      val business_content: String = info.getBusiness_content
      val toObject: ShareBusiness = getJsonToObject(business_content)
      val first_persion: Integer = toObject.getFirst_persion //是否第一人称
      val read_user_id: String = toObject.getRead_user_id //分享阅读userId
      val share_channel: Integer = toObject.getShare_channel //渠道
      val share_creative: String = toObject.getShare_creative //文案
      val share_scene: Integer = toObject.getShare_scene //场景
      val superior_name: String = toObject.getSuperior_name //上级入口名称
      val supreior_page_id: Integer = toObject.getSupreior_page_id //上级入口id
      val user_source: Integer = toObject.getUser_source //站外/站内
      val create_time: String = info.getCreate_time
      val string2Date = TimeUtil.formatString2Date("yyyy-MM-dd", create_time)
      ((string2Date, user_id, first_persion, share_channel, share_creative, share_scene, supreior_page_id, orElse), (read_user_id, user_source))
    })
    val groupByKey: RDD[((Date, Integer, Integer, Integer, String, Integer, Integer, String), Iterable[(String, Integer)])] = allMap.groupByKey()
    groupByKey.map(line => {
      val _1: (Date, Integer, Integer, Integer, String, Integer, Integer, String) = line._1
      val iterable: Iterable[(String, Integer)] = line._2
      val readPv = iterable.size //阅读pv
      val set: mutable.HashSet[String] = mutable.HashSet[String]() //计算阅读uv
      val backSet: mutable.HashSet[String] = mutable.HashSet[String]() //计算回流用户uv
      for (elem <- iterable) {
        val user_id: String = elem._1
        val source: Integer = elem._2
        if (source == 0 && user_id != null) {
          //站内
          backSet.add(user_id)
        }
        set.add(user_id)
      }
      val backUserCount: Int = backSet.size//回流用户
      val readUv = set.size//阅读uv
      (_1, readPv, readUv, backUserCount)
    })
  }

  //将字符串转换成相应的对象
  def getJsonToObject(content: String): ShareBusiness = {
    val business: ShareBusiness = new ShareBusiness
    val clazz: Class[_ <: ShareBusiness] = business.getClass
    try {
      JSON.parseObject(content, clazz)
    } catch {
      case e: Exception => println(e.getMessage)
        business
    }
  }
}
