package AiKnowledgeSpark

import java.util.Date

import bean.AiUserActionInfo
import org.apache.commons.lang3.time.FastDateFormat
import org.apache.spark.rdd.RDD
import util.TimeUtil
import util.sparkutil.CombineKeyFunction

import scala.collection.mutable

/**
  * Created by lenovo on 2018/7/11.
  */
object PlateAnalyzeClicent {
  def analyzePlateFlow(parallelize: RDD[AiUserActionInfo],sysFlag:Int): Unit ={
    val map: RDD[(String, AiUserActionInfo)] = parallelize.map(line => {
      val plateId = line.getPlateId
      (plateId, line)
    })
    val plateAiUserList: RDD[(String, List[AiUserActionInfo])] = map.combineByKey(CombineKeyFunction.createBine[AiUserActionInfo](_),
      CombineKeyFunction.mergeValue[AiUserActionInfo],
      CombineKeyFunction.mergeCombine[AiUserActionInfo])
    plateAiUserList.map(line=> {
      val plateId = line._1
      val list = line._2
      val size = list.size//点击数
      val dates:mutable.HashSet[Date] = mutable.HashSet[Date]()
      for (elem <- list) {
        val time: String = elem.getCreateTime
        val timeFormat: FastDateFormat = TimeUtil.getFastTimeFormat
        val date: Date = timeFormat.parse(time)
        dates.add(date)
      }
      val dayCount= dates.size//某模块的总天数
      val avgCount = size/dayCount//某模块的平均天数

    })
  }
}
