package sparkNewcomBag

import bean.newcombag.NewcomBag
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/**
  * Created by lenovo on 2018/8/23.
  *
  */
object ZeroActiveClient {

  /*
  * 分析零元活动数据方法
  * */
  def analysisZeroData(zeroCache: RDD[NewcomBag]): Unit ={
    val outFilter: RDD[NewcomBag] = zeroCache.filter(line => {
        val userType: String = line.getUserType
      if (userType==0){//站外数据
        true
      }else{
        false
      }
    })
    val innerFilter: RDD[NewcomBag] = zeroCache.filter(_.getUserType==1)//站内数据
    val map: RDD[(String, NewcomBag)] = outFilter.map(line => {
      val eventId = line.getEventId
      (eventId, line)
    })
    val outEventIdLines: RDD[(String, Iterable[NewcomBag])] = map.groupByKey()
    outEventIdLines.map(line=> {
      val eventId = line._1
      val it = line._2
      val pageCount = it.size//点击总量
      val set: mutable.HashSet[String] = new mutable.HashSet[String]()
      for (elem <- it) {
        val userId: String = elem.getUserId
        set.add(userId)
      }
      val userCount: Int = set.size
      (eventId,pageCount,userCount)
    })


    val eventIdObject: RDD[(String, NewcomBag)] = innerFilter.map(line => {
      val eventId = line.getEventId
      (eventId, line)
    })
    val eventIdObjects: RDD[(String, Iterable[NewcomBag])] = eventIdObject.groupByKey()
    eventIdObjects.map(line => {
      val eventId = line._1
      val it = line._2
      val pageCount = it.size
      val set: mutable.HashSet[String] = mutable.HashSet[String]()
      for (elem <- it) {
        val device: String = elem.getDevice
        set+=device
      }
      val userCount = set.size
      (eventId,pageCount,userCount)
    })
  }
}
