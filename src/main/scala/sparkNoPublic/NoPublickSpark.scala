package sparkNoPublic

import java.util
import java.util.Date

import _root_.util.{TimeUtil, IpUtil}
import _root_.util.sparkutil.SparkJson
import bean.AccountDetail
import bean.collect.TotalUserActionInfo
import bean.nopublic.NoPublicBusiness
import dao.factory.SqlSessionFactory
import mapper.{TotalUserActionInfoMapper, AccountDetailMapper}
import org.apache.commons.lang3.StringUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.{JavaConversions, mutable}

/**
  * Created by lenovo on 2018/9/20.
  */
object NoPublickSpark {
  def main(args: Array[String]): Unit = {
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
    /*  //获取业务数据
      val detailMapper: AccountDetailMapper = SqlSessionFactory.getAccountDetailMapper
      val sduaList: util.List[AccountDetail] = detailMapper.selectSduaList(map)
      val sduaBuffer: mutable.Buffer[AccountDetail] = JavaConversions.asScalaBuffer(sduaList)
      val sduaRdd: RDD[AccountDetail] = sc.parallelize(sduaBuffer, 10)*/
      //获取行为数据
      val mapper: TotalUserActionInfoMapper = SqlSessionFactory.getTotalUserActionInfoMapper
      val infoList: util.List[TotalUserActionInfo] = mapper.selectTotalUserActionInfoList(map)
      val scalaBuffer: mutable.Buffer[TotalUserActionInfo] = JavaConversions.asScalaBuffer(infoList)
      val parallelize: RDD[TotalUserActionInfo] = sc.parallelize(scalaBuffer, 10)

    }
  }

  def analyzeNoPublic(parallelize: RDD[TotalUserActionInfo]): Unit ={
    //过滤出公众号数据
    val filter: RDD[TotalUserActionInfo] = parallelize.filter(_.getBusiness_source==1)
    val map: RDD[((String, Date, Integer), TotalUserActionInfo)] = filter.map(line => {
      val business_content: String = line.getBusiness_content
      val business: NoPublicBusiness = SparkJson.getJsonToObject[NoPublicBusiness](business_content)
      val business_name: String = business.getBusiness_name
      val catalog_id: Integer = business.getCatalog_id //章节id
      val couser_id: Integer = business.getCouser_id //课程id
      val live_name: String = business.getLive_name //直播名称
      val name_user: String = business.getName_user //用户昵称
      val phone_user: String = business.getPhone_user //用户手机
      val wechar_name: String = business.getWechar_name //微信昵称
      val watch_time: Integer = business.getWatch_time //观看时长
      val teacher_id: Integer = business.getTeacher_id //老师id
      val create_time: String = line.getCreate_time
      val string2Date: Date = TimeUtil.formatString2Date("yyyy-MM-dd", create_time)
      ((phone_user, string2Date, teacher_id), line)
    })
    val groupByKey: RDD[((String, Date, Integer), Iterable[TotalUserActionInfo])] = map.groupByKey()
    groupByKey.map(line => {
      val key: (String, Date, Integer) = line._1
      val it: Iterable[TotalUserActionInfo] = line._2
      val liveFilter = it.filter(line => {
        val event_name: String = line.getEvent_name
        if(event_name.indexOf("直播")>=0){//过滤出来直播数据
          true
        }else{
          false
        }
      })

      val timeList: Iterable[Double] = liveFilter.map(line => {
        val enter_time: String = line.getEnter_time
        val leave_time: String = line.getLeave_time
        var difference:Double=0;
        if(StringUtils.isNotEmpty(enter_time)&&StringUtils.isNotEmpty(leave_time)){
          difference= TimeUtil.printDifference(enter_time, leave_time, "yyyy-MM-dd HH:mm:ss")*60
        }
        difference
      })
      val sum: Double = timeList.sum//直播时长
      val size: Int = liveFilter.size//直播浏览次数

      val special: Iterable[TotalUserActionInfo] = it.filter(line => {
        val event_name: String = line.getEvent_name
        if (event_name.indexOf("首席观察") > 0 || event_name.indexOf("名师讲堂") > 0) {
          true
        } else {
          false
        }
      })
      val specialClickData: Iterable[TotalUserActionInfo] = special.filter(_.getEvent_name.indexOf("内容点击")>0)
      val specialClickCount: Int = specialClickData.size//专栏阅读数
      val specialBrowse = special.size//专栏浏览数

    })

  }
}
