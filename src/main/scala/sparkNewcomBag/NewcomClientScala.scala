package sparkNewcomBag

import java.io.{File, FileWriter, PrintWriter}
import java.util
import java.util.Date

import _root_.util.sparkutil.ExcelInsertUtil
import _root_.util.{ExcelOperaation, IpUtil, TimeUtil}
import bean.RegisterInfo
import bean.newcombag.NewcomBag
import dao.factory.SqlSessionFactory
import mapper.NewcomBagMapper
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.{JavaConversions, mutable}


/**
  * Created by lenovo on 2018/8/8.
  */
object NewcomClientScala {
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
      map.put("activityName",null)
      val bagList: util.List[NewcomBag] = mapper.selectNewcomBagList(map)//新人大礼包活动数据
      map.put("activityName","0元活动")
      val zeroList: util.List[NewcomBag] = mapper.selectNewcomBagList(map)//零元活动数据
      val zeroBuffer: mutable.Buffer[NewcomBag] = JavaConversions.asScalaBuffer(zeroList)
      val zeroCache: RDD[NewcomBag] = sc.parallelize(zeroBuffer, 10).cache()

      //java=>scala
      val scalaBuffer: mutable.Buffer[NewcomBag] = JavaConversions.asScalaBuffer(bagList)
      val cache: RDD[NewcomBag] = sc.parallelize(scalaBuffer, 10).cache()

      val getInnerData: RDD[NewcomBag] = cache.filter(_.getUserType=="1")//得到站内的数据

      val currentDay: String = getTime(0)
      val path="e:\\new_person_"+currentDay+".xls"
      delete(path)
      //获取注册表单数据
      val registerInfoMapper = SqlSessionFactory.getRegisterInfoMapper
      val registerInfoList = registerInfoMapper.selectRegisterInfoList(map)
      val registerInfoes = JavaConversions.asScalaBuffer(registerInfoList)
      val resigterInfoRdd: RDD[RegisterInfo] = sc.parallelize(registerInfoes,10).cache()
      //统计站外来源业务
      getOutPvUv(cache,dayFlag,path)
      //新人大礼包弹窗展现数
      upWinFunction(getInnerData,dayFlag,path)
      //点击每一个按钮的数量
      clickEveryFunction(getInnerData,dayFlag,path)
      //活动页面的pv,uv
      countPagePvUv(getInnerData,dayFlag,path)
      //活动页面的停留时长
      countPageTime(getInnerData,dayFlag,path)
      //各个banner位的引流量
      countBannerPvUv(getInnerData,dayFlag,path)
      //有多少用户通过新人大礼包正式注册
     val regUserCount: Long = getRegisterUser(getInnerData,resigterInfoRdd,dayFlag,path)
      //点击活动引入用户数量
     val innerCount: Long = getInnerDataPvUv(getInnerData,dayFlag,path)
      //转化率 新装机用户/注册用户
      //val d: Double = regUserCount/innerCount.toDouble
      //println("转化滤:"+d)
      //一键领取过大礼包的用户有多少
     val bagUserCount: Long = getBagUserCountFunction(getInnerData: RDD[NewcomBag],dayFlag,path)
      sc.stop()
      val stopped: Boolean = sc.isStopped
      println("任务是否执行结束" + stopped)
    }
  }

  //计算站外业务数据
  def getOutPvUv(cache: RDD[NewcomBag],dayFlag:Int,path:String): Unit ={
    //计算站外数据pv,uv
    val outData = cache.filter(_.getUserType=="0")
    val outPv = outData.count()
    println("站外pv:"+outPv)
    val outUv = outData.map(_.getUserId).distinct().count()
    println("站外uv:"+outUv)
    val top="站外PV,UV统计\n"
    printToFile(top,path)
    val title="时间\t任务名称\tPV\tUV\n"
    //printToFile(title,path)
    val time: String = getTime(dayFlag)
    val content=time+"\t站外PV,UV统计\t"+outPv+"\t"+outUv+"\n"
    val value1 =new util.ArrayList[AnyRef]()
    val valArray=Array[String](time,"站外PV,UV统计",String.valueOf(outPv),String.valueOf(outUv))
    ExcelInsertUtil.addExcel(path,valArray,0)
    //printToFile(content,path)
    //站外数据访问
    val filter: RDD[NewcomBag] = outData.filter(line => {
      val source: String = line.getSource
      if (source != null && source != "null" && source != "" && source != "0") {
        true
      } else {
        false
      }
    })
    val map = outData.map(line => {
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
    val titleSource="时间\t任务名称\t来源\tPV\tUV\n"
    val topsr="统计站外来源PV/UV\n"
    //printToFile(topsr,path)
    //printToFile(titleSource,path)
    //ExcelOperaation.addExcel(path,value2,0)
    userPageUser.foreach(line => {
      val source: String = line._1
      val pageCount: Int = line._2
      val userCount: Int = line._3
      val time1: String = getTime(dayFlag)
      val content=time1+"\t统计站外来源PV/UV\t"+source+"\t"+pageCount+"\t"+userCount+"\n"
      //printToFile(content,path)
      val arrayVal=Array[String](time1,"统计站外来源PV/UV",String.valueOf(source),String.valueOf(pageCount),String.valueOf(userCount))
      ExcelInsertUtil.addExcel(path,arrayVal,1)
    })
    val toList = userPageUser.collect().toList
    println("各个站外来源的pv,uv:"+toList)
  }


  //新人大礼包弹窗展现数
  def upWinFunction(getInnerData: RDD[NewcomBag],dayFlag:Int,path:String): Unit ={
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
    val map: RDD[(String, Int,Int)] = filterUp.map(line => {
      var eventName = line.getEventName
      if (eventName.indexOf("立即领取")>=0){
        eventName="立即领取"
      }else{
        eventName="登陆后领取"
      }
      (eventName, line)
    }).groupByKey().map(line => {
      val eventName = line._1
      val it = line._2
      val size = it.size
      val set: mutable.HashSet[String] = mutable.HashSet[String]()
      for (elem <- it) {
        val device: String = elem.getDevice
        set.add(device)
      }
      val userCount: Int = set.size
      (eventName, size,userCount)
    })
    val toList: List[(String, Int,Int)] = map.collect().toList
    println("2个新人大礼包弹窗展现数:"+toList)
    val title="时间\t统计任务名称\t窗口名称\t弹窗展现数\tUV\n"
    val top="2个新人大礼包弹窗展现数\n"
    //printToFile(top,path)
    //printToFile(title,path)
    map.foreach(line => {
      val eventName: String = line._1
      val size: Int = line._2
      val uv: Int = line._3
      val time: String = getTime(dayFlag)
      val content=time+"\t2个新人大礼包弹窗展现数\t"+eventName+"\t"+size+"\t"+uv+"\n"
      val arrayVal=Array[String](time,"2个新人大礼包弹窗展现数",eventName,String.valueOf(size),String.valueOf(uv))
      ExcelInsertUtil.addExcel(path,arrayVal,2)
      //printToFile(content,path)
    })
  }
  //点击每一个按钮的数量
  def clickEveryFunction(getInnerData: RDD[NewcomBag],dayFlag:Int,path:String): Unit ={
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
    val toList: List[(String, Int, Int)] = map.collect().toList
    println("点击每一个按钮的数量:"+toList)
    val title="时间\t统计任务名称\t按钮名称\t点击数量\tUV\n"
    val top="点击每一个按钮的数量\n"
    //printToFile(top,path)
    //printToFile(title,path)
    map.foreach(line => {
      val eventName: String = line._1
      val pv: Int = line._2
      val uv: Int = line._3
      val time: String = getTime(dayFlag)
      val content=time+"\t点击每一个按钮的数量\t"+eventName+"\t"+pv+"\t"+uv+"\n"
      //printToFile(content,path)
      val arrayVal=Array[String](time,"点击每一个按钮的数量",eventName,String.valueOf(pv),String.valueOf(uv))
      ExcelInsertUtil.addExcel(path,arrayVal,3)
    })
  }


  //活动页面的pv,uv
  def countPagePvUv(getInnerData: RDD[NewcomBag],dayFlag:Int,path:String): Unit ={
    //过滤掉空的
    val activityData: RDD[NewcomBag] = getInnerData.filter(line => {
      val name: String = line.getEventName
      val pageId: String = line.getPageId
      val bannerId: String = line.getBannerId

      if (pageId=="newBigGift"&&name!="大礼包-查看"&&name!="大礼包-一键领取"&&bannerId!=null||name=="登陆后弹窗-立即领取") {
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
    val title="时间\t任务名称\tPV\tUV\n"
    val top="活动页面PV/UV\n"
    ///printToFile(top,path)
    //printToFile(title,path)
    val time: String = getTime(dayFlag)
    val content=time+"\t活动页面PV/UV\t"+pv+"\t"+userCount+"\n"
    val arrayVal=Array[String](time,"活动页面PV/UV",String.valueOf(pv),String.valueOf(userCount))
    ExcelInsertUtil.addExcel(path,arrayVal,4)
    //printToFile(content,path)
    /*
      val content="活动页面pvuv\tpageId:"+pageId+"\t"+"pv:"+pageCount+"\t"+"uv:"+userCount+"\n"
    })*/
  }

  //统计活动页面停留时间
  def countPageTime(getInnerData: RDD[NewcomBag],dayFlag:Int,path:String): Unit ={
    //val newData = filterPageId.filter(_.getPageId=="newBigGift")
    val filter: RDD[NewcomBag] = getInnerData.filter(line => {
      //过滤出活动页面
      val eventName: String = line.getEventName
      if (eventName == "大礼包-查看" || eventName == "大礼包-一键领取") {
        true
      } else {
        false
      }
    })

    val sum: Double = filter.map(line => {
      val startTime = line.getStartTime
      val endTime = line.getEndTime
      val compareTo: Int = startTime.compareTo(endTime)
      var difference = 1.0
      if (compareTo > 0) {
        difference = TimeUtil.printDifference(endTime, startTime, "yyyy-MM-dd HH:mm:ss") * 60 //精确到秒
      } else {
        difference = TimeUtil.printDifference(startTime, endTime, "yyyy-MM-dd HH:mm:ss") * 60 //精确到秒
      }
      if (difference==0){
        difference=1.0
      }
      difference
    }).sum()
    println("活动页面停留时间:"+sum)
    val title="时间\t任务名称\t停留时间\n"
    val top="活动页面停留时间\n"
    //printToFile(top,path)
    //printToFile(title,path)
    val time: String = getTime(dayFlag)
    val content=time+"\t活动页面停留时间\t"+sum+"\n"
    val arrayVal=Array[String](time,"活动页面停留时间",String.valueOf(sum))
    ExcelInsertUtil.addExcel(path,arrayVal,5)
    //printToFile(content,path)
  }

  //各个banner位置的pv,uv
  def countBannerPvUv(getInnerData: RDD[NewcomBag],dayFlag:Int,path:String): Unit ={
    //过滤掉bannerId为空的数据
    val filter: RDD[NewcomBag] = getInnerData.filter(_.getBannerId!=null).filter(_.getBannerId!="null").filter(_.getBannerId!="0").filter(_.getBannerId!="")
    val bannerData: RDD[NewcomBag] = filter.filter(line => {
      val eventName: String = line.getEventName
      val pageId: String = line.getPageId

      if (pageId=="newBigGift") {
        true
      } else {
        false
      }
    })
    val bannerIdLines: RDD[(String, Iterable[NewcomBag])] = bannerData.map(line => {
      val bannerId = line.getBannerId
      val eventName: String = line.getEventName
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
    val title="时间\t任务名称\tbanner位置名称\tPV\tUV\n"
    val top="各个banner位的引流量\n"
    //printToFile(top,path)
    //printToFile(title,path)
    bannerIdPvUv.collect().foreach(line => {
      //文件写入
      val bannerId = line._1
      val pv: Int = line._2
      val uv: Int = line._3
      val time: String = getTime(dayFlag)
      val content=time+"\t各个banner位的引流量\t"+bannerId+"\t"+pv+"\t"+uv+"\n"
      // printToFile(content,path)
      val arrayVal=Array[String](time,"各个banner位的引流量",String.valueOf(bannerId),String.valueOf(pv),String.valueOf(uv))
      ExcelInsertUtil.addExcel(path,arrayVal,6)
    })
  }

  //计算有多少用户通过该活动注册
  def getRegisterUser(getInnerData: RDD[NewcomBag],resigterInfoRdd: RDD[RegisterInfo],dayFlag:Int,path:String): Long ={
    //过滤掉了userId为空的用户
    val filter: RDD[NewcomBag] = getInnerData.filter(_.getUserId!=null).filter(_.getUserId!="0").filter(_.getUserId!="").filter(_.getUserId!="null")
    val reData: RDD[NewcomBag] = filter.filter(_.getEventName=="登陆后弹窗-立即领取")
    val userIdLine: RDD[(String, NewcomBag)] = reData.map(line => {
      val userId = line.getUserId
      (userId, line)
    })
    val accountIdLine: RDD[(String, RegisterInfo)] = resigterInfoRdd.map(line => {
      val accountId = line.getiAccountId()
      (accountId.toString, line)
    })
    val registerCount: Long = accountIdLine.join(userIdLine).map(_._1).distinct().count()
    /* val join: RDD[(String, (RegisterInfo, NewcomBag))] = accountIdLine.join(userIdLine)//获取注册用户详情
     join.map(line=> {
       val userId = line._1
       val reg = line._2._1
       val newcomBag = line._2._2
       val phone = reg.getsPhone()
       val userName = reg.getsUserName()
     })*/
    println("通过活动注册的用户数量:"+registerCount)
    val title="时间\t任务名称\t用户数量\n"
    val top="通过活动注册的用户数量\n"
    //printToFile(top,path)
    //printToFile(title,path)
    val time: String = getTime(dayFlag)
    val content=time+"\t通过活动注册的用户数量\t"+registerCount+"\n"
    val arrayVal=Array[String](time,"通过活动注册的用户数量",String.valueOf(registerCount))
    ExcelInsertUtil.addExcel(path,arrayVal,7)
    //printToFile(content,path)
    registerCount
  }

  //站内--活动引入未登录用户量
  def getInnerDataPvUv(getInnerData: RDD[NewcomBag],dayFlag:Int,path:String) :Long={
    val filter = getInnerData.filter(line => {//得到未登录用户数据
    val userId = line.getUserId
      if (userId == null || userId == "" || userId == "null" || userId == "0") {
        true
      } else {
        false
      }
    })

    val activeClick: RDD[NewcomBag] = filter.filter(line => {
      val eventName: String = line.getEventName
      if (eventName == "开屏弹窗-登陆后领取"||eventName=="登陆后弹窗-立即领取") {
        true
      } else {
        false
      }
    })
    val map = activeClick.map(line => {
      val device = line.getDevice
      device
    })
    val userCount: Long = map.distinct().count()
    println("引进的用户数量"+userCount)
    val title="时间\t任务名称\t用户数量\n"
    val top="引进的用户数量\n"
    //printToFile(top,path)
    //(title,path)
    val time: String = getTime(dayFlag)
    val content=time+"\t活动引入的用户量\t"+userCount+"\n"
    val arrayVal=Array[String](time,"活动引入的用户量",String.valueOf(userCount))
    ExcelInsertUtil.addExcel(path,arrayVal,8)
    //printToFile(content,path)
    userCount
  }
  //领取过大礼包的用户
  def getBagUserCountFunction(getInnerData: RDD[NewcomBag],dayFlag:Int,path:String): Long ={
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
    val title="时间\t任务名称\t用户数量\n"
    val top="领取过大礼包的用户数量\n"
    //printToFile(top,path )
    //printToFile(title,path )
    val time: String = getTime(dayFlag)
    val content=time+"\t领取过大礼包的用户数量\t"+count+"\n"
    val arrayVal=Array[String](time,"领取过大礼包的用户数量",String.valueOf(count))
    ExcelInsertUtil.addExcel(path,arrayVal,9)
    //printToFile(content,path)
    count
  }




  //判断有多少
  def printToFile(content:String,path:String): Unit ={
    val writer = new PrintWriter(new FileWriter(new File(path),true))
    writer.write(content)
    writer.flush()
    writer.close()
  }

  def getTime(dayFlag:Int): String ={
    val date: Date = new Date()
    val nextDate: Date = TimeUtil.getNextDate(date,-dayFlag)
    TimeUtil.getDate2String("yyyy-MM-dd",nextDate)
  }
  def delete(path:String): Unit ={
    val file: File = new File(path)
    if(!file.exists()){
      return
    }else{
      file.delete()
    }
  }
  //比较时间的方法
  //      val compareTo: Int = startTime.compareTo(endTime)
  //      var difference=1.0
  //      if(compareTo>0){
  //        difference = TimeUtil.printDifference(endTime, startTime, "yyyy-MM-dd HH:mm:ss") * 60 //精确到秒
  //      }else{
  //        difference= TimeUtil.printDifference(startTime, endTime, "yyyy-MM-dd HH:mm:ss") * 60 //精确到秒
  //      }
}
