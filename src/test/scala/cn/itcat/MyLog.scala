package cn.itcat

import org.apache.log4j.{Logger, Level}

/**
  * Created by lenovo on 2018/7/25.
  */
object MyLog {
  def setLogLeavel(level: Level): Unit = {
    val flag = Logger.getRootLogger.getAllAppenders.hasMoreElements
    if (!flag) {
      println("set log level ->" + level)
      Logger.getRootLogger.setLevel(level)
    }
  }
}
