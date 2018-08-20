package sparkNewcomBag

import java.io.{FileWriter, File, PrintWriter}
import java.util

import _root_.util.{TimeUtil, IpUtil}
import bean.TCollect
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
      val mapper: NewcomBagMapper = SqlSessionFactory.getNewcomBagMapper
      val map= new util.HashMap[String,Object]()
      map.put("dayFlag",dayFlag.toString)
      val bagList: util.List[NewcomBag] = mapper.selectNewcomBagList(map)
      //java=>scala
      val scalaBuffer: mutable.Buffer[NewcomBag] = JavaConversions.asScalaBuffer(bagList)
      val cache: RDD[NewcomBag] = sc.parallelize(scalaBuffer, 10).cache()
      val getInnerData: RDD[NewcomBag] = cache.filter(_.getUserType=="1")//得到站内的数据
      val path="C:\\Users\\lenovo\\Desktop\\新人大礼包result\\banResult.xls"
      
      //=============================================================================
      //各个banner位置的pv,uv
      //countBannerPvUv(getInnerData,path)
      //=============================================================================
      //分享次数
     /* val count: Long = getInnerData.filter(_.getShare!=null).count()
      val contentCount="分享次数\tshareCount:"+count+"\n\n\n"
      printToFile(path,contentCount)*/
      //=============================================================================
      //活动页面的pv,uv
      countPagePvUv(getInnerData,path)
      //=============================================================================
      //统计活动页面停留时间
     // countPageTime(getInnerData,path)
      //C:\Users\lenovo\Desktop\新人大礼包result
      sc.stop()
      val stopped: Boolean = sc.isStopped
      println("任务是否执行结束" + stopped)
    }
  }

   //各个banner位置的pv,uv
  def countBannerPvUv(getInnerData: RDD[NewcomBag],path:String): Unit ={
    val filter = getInnerData.filter(_.getBannerId!=null)//过滤掉bannerId为空的数据
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
    bannerIdPvUv.collect().foreach(line => {
      //文件写入
      val bannerId = line._1
      val pv: Int = line._2
      val uv: Int = line._3
      val content="各个banner位置的pv,uv\tbannerId:"+bannerId+"\t"+"pv:"+pv+"\t"+"uv:"+uv+"\n"
      printToFile(path,content)
    })
  }
  //活动页面的pv,uv
  def countPagePvUv(getInnerData: RDD[NewcomBag],path:String): Unit ={
    val filterPageId1: RDD[NewcomBag] = getInnerData.filter(_.getPageId!=null)//过滤出来只有pageId不为空的
    val filterPageId2: RDD[NewcomBag] = getInnerData.filter(_.getPageId!="")//过滤出来只有pageId不为空的

    val pageIdline: RDD[(String, NewcomBag)] = filterPageId2.map(line =>(line.getPageId,line))
    val pageIdLines: RDD[(String, Iterable[NewcomBag])] = pageIdline.groupByKey()
    val pageIdCountUser: RDD[(String, Int, Int)] = pageIdLines.map(line => {
      val pageId = line._1
      val lines = line._2
      val pageCount: Int = lines.size //pv
      val set: mutable.HashSet[String] = mutable.HashSet[String]()
      for (elem <- lines) {
        //val userId = elem.getUserId
        val device = elem.getDevice//利用设备唯一标识区分用户
        if (device != null) {
          set += device
        }
      }
      val userCount = set.size //uv
      (pageId, pageCount, userCount)
    })
    pageIdCountUser.foreach(line => {
      val pageId: String = line._1
      val pageCount: Int = line._2
      val userCount: Int = line._3
      val content="活动页面pvuv\tpageId:"+pageId+"\t"+"pv:"+pageCount+"\t"+"uv:"+userCount+"\n"
      printToFile(path,content)
    })
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
  def printToFile(path:String,content:String): Unit ={
    val writer = new PrintWriter(new FileWriter(new File(path),true))
    writer.write(content)
    writer.flush()
    writer.close()
  }
}
