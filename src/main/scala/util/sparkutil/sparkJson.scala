package util.sparkutil

import bean.share.ShareBusiness
import com.alibaba.fastjson.JSON

/**
  * Created by lenovo on 2018/9/21.
  */
object SparkJson {

  def getJsonToObject[T](content: String): T = {
    val business: T = new T
    val clazz: Class[_ <: T] = business.getClass
    try {
      JSON.parseObject(content, clazz)
    } catch {
      case e: Exception => println(e.getMessage)
        business
    }
  }
}
