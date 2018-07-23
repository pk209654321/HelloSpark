package cn.itcast.action

import java.net.InetAddress
import java.util
import cn.itcast.bean.TAccountResigterInfo
import cn.itcast.tool.ConnectionTool
import org.apache.commons.lang3.StringUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.{mutable, JavaConversions, JavaConverters}

/**
  * Created by lenovo on 2018/6/26.
  */
object ResigterInfoAction {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("WC")
    val linux: Int = judgeLocalOrLinux()
    if(linux==0){
      conf.setMaster("local[*]")
    }
      //.setMaster("local[*]")
    val sc = new SparkContext(conf)
    val infoList: util.List[TAccountResigterInfo] = ConnectionTool.getAllResigterInfoList()
    val scalaBuffer: mutable.Buffer[TAccountResigterInfo] = JavaConversions.asScalaBuffer(infoList)
    val parallelize: RDD[TAccountResigterInfo] = sc.parallelize(scalaBuffer.toSeq)

    //
    val map: RDD[TAccountResigterInfo] = parallelize.map(line => {
      val sDUA = line.getsDUA()
      //println("sDUA----"+sDUA)
      //SN=IOSCJPH13_GA& VN=130062218& BN=0& VC=APPLE& MO=iPad& RL=768_1024& CHID=1000& LCID=0&RV=& OS=9.3.5& DV=V1
      val splits = sDUA.split("&")
      val info = new TAccountResigterInfo()
      info.setiAccountId(line.getiAccountId())
      info.setsUserName(line.getsUserName())
      info.setsPhone(line.getsPhone())
      info.setTimeStr(line.getTimeStr)
      if(splits.size==11){
        val brand = getNeedField(splits(3).trim) //品牌
        val model = getNeedField(splits(4).trim) //型号
        val channel = getNeedField(splits(6).trim) //渠道
        info.setBrand(brand)
        info.setModel(model)
        info.setChannel(channel)
      }
      info
    })
    //val by: RDD[TAccountResigterInfo] = map.sortBy(x=>Temp(x.getiAccountId().toInt,x.getsUserName(),x.getsPhone(),x.getBrand,x.getModel,x.getChannel,x.getTimeStr))
    map.collect().foreach(line =>{
      val i: Int = ConnectionTool.insertResigterInfo(line)
      if(i>0){
      System.out.println("时间="+line.getTimeStr+"    insert ResigterInfo success")
      }
    })
    sc.stop()
  }

  def getNeedField(field:String): String ={
    val split= field.split("=")
    if (split.length==2){
      split(1)
    }else{
      ""
    }
  }

  //判断是否在本地运行
  def judgeLocalOrLinux(): Int ={
    val string: String = InetAddress.getLocalHost().getHostAddress().toString()
    if (string=="192.168.136.101"){
      1 //在linxu上
    }else{
      0 //本地
    }
  }
}

case class Temp(val iAccountId:Long,val sUserName:String,val sPhone:String,val brand:String,val model:String,val channel:String,val timeStr:String) extends Ordered[Temp] with Serializable {
  override def compare(that: Temp): Int = {
     val l: Long = this.iAccountId-that.iAccountId
    l.toInt
  }
}
