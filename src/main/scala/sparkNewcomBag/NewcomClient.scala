package sparkNewcomBag

import java.io.{FileWriter, File, PrintWriter}
import java.util

import _root_.util.{TimeUtil, IpUtil}
import bean.{RegisterInfo, AiAccountResigterInfo, TCollect}
import bean.newcombag.NewcomBag
import constant.Constants
import dao.ITCollectDao
import dao.factory.{DAOFactory, SqlSessionFactory}
import mapper.NewcomBagMapper
import org.apache.commons.lang3.StringUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.{mutable, JavaConversions}


/**
  * Created by lenovo on 2018/8/8.
  */
object NewcomClient {
  def main(args: Array[String]) {

    //1,各个banner位的引流量
    val range = args(0).toInt
    for (dayFlag <- (1 to range).reverse) {
      println("查询的类容范围偏移量offset：" + dayFlag)
      println("查询的类容范围偏移量offset：" + dayFlag)
      println("查询的类容范围偏移量offset：" + dayFlag)
      val local: Boolean = IpUtil.judgeLocal()
      var sparkConf: SparkConf = null
      if (local) {
        sparkConf = new SparkConf().setAppName("NewcomClient").setMaster("local[*]")
      } else {
        sparkConf = new SparkConf().setAppName("NewcomClient")
      }
      val sc: SparkContext = new SparkContext(sparkConf)
      sc.setLogLevel("WARN")
      //获取活动用户行为数据
      val mapper: NewcomBagMapper = SqlSessionFactory.getNewcomBagMapper
      val map= new util.HashMap[String,Object]()
      map.put("dayFlag",dayFlag.toString)
      val bagList: util.List[NewcomBag] = mapper.selectNewcomBagList(map)
      //java=>scala
      val scalaBuffer: mutable.Buffer[NewcomBag] = JavaConversions.asScalaBuffer(bagList)
      val cache: RDD[NewcomBag] = sc.parallelize(scalaBuffer, 10).cache()
      val getInnerData: RDD[NewcomBag] = cache.filter(_.getUserType=="1")//得到站内的数据
      val path="C:\\Users\\lenovo\\Desktop\\新人大礼包result\\banResult.xls"
      //获取注册表单数据
      val registerInfoMapper = SqlSessionFactory.getRegisterInfoMapper
      val registerInfoList = registerInfoMapper.selectRegisterInfoList(map)
      val registerInfoes = JavaConversions.asScalaBuffer(registerInfoList)
      val resigterInfoRdd: RDD[RegisterInfo] = sc.parallelize(registerInfoes,10).cache()
      //新人大礼包弹窗展现数
      upWinFunction(getInnerData)
      //点击每一个按钮的数量
      clickEveryFunction(getInnerData)
      //活动页面的pv,uv
      countPagePvUv(getInnerData,path)
      //各个banner位的引流量
      countBannerPvUv(getInnerData,path)
      //有多少用户通过新人大礼包正式注册
      val regUserCount: Long = getRegisterUser(getInnerData,resigterInfoRdd)
      //点击活动新装机的用户数量
      val innerCount: Long = getInnerDataPvUv(getInnerData)
      //转化率 新装机用户/注册用户
      val d: Double = regUserCount/innerCount.toDouble
      println("转化滤:"+d)
      //=============================================================================
      //各个banner位置的pv,uv
      //countBannerPvUv(getInnerData,path)
      //=============================================================================
      //分享次数
     /* val count: Long = getInnerData.filter(_.getShare!=null).count()
      val contentCount="分享次数\tshareCount:"+count+"\n\n\n"
      printToFile(path,contentCount)*/
      //=============================================================================

      //=============================================================================
      //统计活动页面停留时间
     // countPageTime(getInnerData,path)
      //C:\Users\lenovo\Desktop\新人大礼包result
      //=============================================================================
      //统计注册用户数量
      //val regUserCount: Long = getRegisterUser(getInnerData,resigterInfoRdd)
      //=============================================================================
      //计算站外业务数据
      //getOutPvUv(cache)
      //统计引入新用户数量
      // newCount: Long = getOutDataPvUv(getInnerData)
      //计算注册转化滤
      //println("新用户注册转化率:"+regUserCount/newCount)
      //一键领取过大礼包的用户有多少
      //val bagUserCount: Long = getBagUserCountFunction(getInnerData: RDD[NewcomBag])
      sc.stop()
      val stopped: Boolean = sc.isStopped
      println("任务是否执行结束" + stopped)
    }
  }
  //新人大礼包弹窗展现数
  def upWinFunction(getInnerData: RDD[NewcomBag]): Unit ={
    //过滤掉空的事件名称字段
    val filter: RDD[NewcomBag] = getInnerData.filter(_.getEventName!=null).filter(_.getEventName!="").filter(_.getEventName!="null")
    val filterUp: RDD[NewcomBag] = filter.filter(line => {
      //过滤出弹窗的数量
      val name: String = line.getEventName
      val indexOf: Int = name.indexOf("弹窗")
      if (indexOf >= 0) {
        true
      } else {
        false
      }
    })
    val map: RDD[(String, Int)] = filterUp.map(line => {
      val eventName = line.getEventName
      (eventName, line)
    }).groupByKey().map(line => {
      val eventName = line._1
      val it = line._2
      val size = it.size
      (eventName, size)
    })
    val toList: List[(String, Int)] = map.collect().toList
    println("2个新人大礼包弹窗展现数:"+toList)
  }
  //点击每一个按钮的数量
  def clickEveryFunction(getInnerData: RDD[NewcomBag]): Unit ={
    val filter: RDD[NewcomBag] = getInnerData.filter(line => {
      //val userId: String = line.getUserId
      val eventName: String = line.getEventName
      if (eventName != null && eventName != "" && eventName != "null") {
        true
      } else {
        false
      }
    })
    val eventLine: RDD[(String, NewcomBag)] = filter.map(line => {
      val eventName = line.getEventName
      (eventName, line)
    })
    val groupByKey: RDD[(String, Iterable[NewcomBag])] = eventLine.groupByKey()
    val map: RDD[(String, Int, Int)] = groupByKey.map(line => {
      val eventName = line._1
      val it = line._2
      val pageCount = it.size
      val set = mutable.HashSet[String]()
      for (elem <- it) {
        val device: String = elem.getDevice
        set.add(device)
      }
      val userCount: Int = set.size
      (eventName, pageCount, userCount)
    })
    map.collect().toList
  }
  //领取过大礼包的用户
  def getBagUserCountFunction(getInnerData: RDD[NewcomBag]): Long ={
    val getBagData: RDD[NewcomBag] = getInnerData.filter(line => {
      val userId = line.getUserId
      val eventName = line.getEventName
      if (userId != "0" && userId != null && userId != "null" && userId != "" && eventName == "大礼包-一键领取") {
        //条件表示登录和领取过礼包的用户
        true
      } else {
        false
      }
    })
    val count: Long = getBagData.map(line => {
      val userId = line.getUserId
      val eventName = line.getEventName
      userId
    }).distinct().count()
    println("领取过大礼包的用户数量:"+count)
    count
  }
  //站内引流
  def getInnerDataPvUv(getInnerData: RDD[NewcomBag]) :Long={
    val filter = getInnerData.filter(line => {//得到未登录用户数据
      val userId = line.getUserId
      if (userId == null || userId == "" || userId == "null" || userId == "0") {
      true
    } else {
      false
    }
  })
    val map = filter.map(line => {
      line.getDevice
    })
    val userCount: Long = map.distinct().count()
    println("引进的用户数量"+userCount)
    userCount
  }

  //计算站外业务数据
  def getOutPvUv(cache: RDD[NewcomBag]): Unit ={
    //计算站外数据pv,uv
    val outData = cache.filter(_.getUserType=="0")
    val outPv = outData.count()
    println("站外pv:"+outPv)
    val outUv = outData.map(_.getUserId).distinct().count()
    println("站外uv:"+outUv)
    val map = cache.map(line => {
      val userId = line.getUserId
      val source = line.getSource
      (source, line)
    })
    val groupByKey = map.groupByKey()
    val userPageUser = groupByKey.map(line => {
      val source: String = line._1
      val lines = line._2
      val set = mutable.HashSet[String]()
      for (elem <- lines) {
        val userId = elem.getUserId
        set.add(userId)
      }
      val userCount = set.size
      val pageCount = lines.size
      (source, pageCount, userCount)
    })
    val toList = userPageUser.collect().toList
    println("各个站外来源的pv,uv:"+toList)
  }
   //各个banner位置的pv,uv
  def countBannerPvUv(getInnerData: RDD[NewcomBag],path:String): Unit ={
   val filter: RDD[NewcomBag] = getInnerData.filter(_.getBannerId!=null).filter(_.getBannerId!="null").filter(_.getBannerId!="0")//过滤掉bannerId为空的数据
    val bannerIdLines: RDD[(String, Iterable[NewcomBag])] = filter.map(line => {
        val bannerId = line.getBannerId
        (bannerId, line)
      }).groupByKey()
    val bannerIdPvUv: RDD[(String,Int, Int)] = bannerIdLines.map(line => {
      val bannerId = line._1
      val lines = line._2
      val pv: Int = lines.size //当前banner pv
      val set = mutable.HashSet[String]() //当前banner uv
      for (elem <- lines) {
        val device = elem.getDevice//利用设备唯一标识计算 uv
        if (device != null) {
          //set.add(userId)
          set+=device
        }
      }
      val uv = set.size //当前banner uv
      (bannerId, pv, uv)
    })
     val toList: List[(String, Int, Int)] = bannerIdPvUv.collect().toList
     println("各个banner位的引流量"+toList)
//    bannerIdPvUv.collect().foreach(line => {
//      //文件写入
//      val bannerId = line._1
//      val pv: Int = line._2
//      val uv: Int = line._3
//      val content="各个banner位置的pv,uv\tbannerId:"+bannerId+"\t"+"pv:"+pv+"\t"+"uv:"+uv+"\n"
//      printToFile(path,content)
//    })
  }
  //活动页面的pv,uv
  def countPagePvUv(getInnerData: RDD[NewcomBag],path:String): Unit ={
    val activityData: RDD[NewcomBag] = getInnerData.filter(f = line => {
      val eventName = line.getEventName
      if (eventName == "大礼包-查看" || eventName == "大礼包-一键领取") {
        true
      } else {
        false
      }
    })
    val pv: Long = activityData.count()//pv
    val deviceRdd: RDD[String] = activityData.map(line => {
      val device = line.getDevice
      device
    })
    val userCount: Long = deviceRdd.distinct().count() //uv
    println("活动页面PV/UV:pv="+pv+"   uv="+userCount)
    /*
      val content="活动页面pvuv\tpageId:"+pageId+"\t"+"pv:"+pageCount+"\t"+"uv:"+userCount+"\n"
    })*/
  }

  //统计活动页面停留时间
  def countPageTime(filterPageId: RDD[NewcomBag],path:String): Unit ={
    val newData = filterPageId.filter(_.getPageId=="newBigGift")
    val pageIdTwoTime = newData.map(line => {
      val startTime = line.getStartTime
      val endTime = line.getEndTime
//      val compareTo: Int = startTime.compareTo(endTime)
//      var difference=1.0
//      if(compareTo>0){
//        difference = TimeUtil.printDifference(endTime, startTime, "yyyy-MM-dd HH:mm:ss") * 60 //精确到秒
//      }else{
//        difference= TimeUtil.printDifference(startTime, endTime, "yyyy-MM-dd HH:mm:ss") * 60 //精确到秒
//      }
      val pageId: String = line.getPageId

      (pageId, List[String](startTime,endTime))
    })
    val pageIdTime = pageIdTwoTime.groupByKey()

    val pageTime = pageIdTime.map( f = line => {
      val pageId = line._1
      val iterable: Iterable[List[String]] = line._2
      val flatten: Iterable[String] = iterable.flatten
      //val filter = flatten.filter(_!="1970-01-01 08:00:00.0")
      val setTimes = mutable.HashSet[String]()
      for (elem <- flatten) {
        setTimes.add(elem)
      }
      if (flatten.size==0){
        setTimes.add("2018-08-11 00:00:00")
      }
      val head = setTimes.head
      val last = setTimes.last
      val difference = TimeUtil.printDifference(head, last, "yyyy-MM-dd HH:mm:ss") * 60
      val round = Math.round(difference)
      (pageId, round)
    })
    pageTime.foreach(line => {
      val pageId = line._1
      val round = line._2
      val content="活动页面停留时间\tpageId:"+pageId+"\t"+"round:"+round+"\n"
      printToFile(path,content)
    })
  }

  //计算有多少用户通过该活动注册
  def getRegisterUser(getInnerData: RDD[NewcomBag],resigterInfoRdd: RDD[RegisterInfo]): Long ={
    //过滤掉了userId为空的用户
    val filter: RDD[NewcomBag] = getInnerData.filter(_.getUserId!=null).filter(_.getUserId!="0").filter(_.getUserId!="").filter(_.getUserId!="null")
    val userIdLine: RDD[(String, NewcomBag)] = filter.map(line => {
      val userId = line.getUserId
      (userId, line)
    })
    val accountIdLine: RDD[(String, RegisterInfo)] = resigterInfoRdd.map(line => {
      val accountId = line.getiAccountId()
      (accountId.toString, line)
    })
    val registerCount: Long = accountIdLine.join(userIdLine).count()
    val join: RDD[(String, (RegisterInfo, NewcomBag))] = accountIdLine.join(userIdLine)//获取注册用户详情
    join.map(line=> {
      val userId = line._1
      val reg = line._2._1
      val newcomBag = line._2._2
      val phone = reg.getsPhone()
      val userName = reg.getsUserName()
    })
    println("通过活动注册的用户数量:"+registerCount)
    registerCount
  }

  //判断有多少
  def printToFile(path:String,content:String): Unit ={
    val writer = new PrintWriter(new FileWriter(new File(path),true))
    writer.write(content)
    writer.flush()
    writer.close()
  }
}
