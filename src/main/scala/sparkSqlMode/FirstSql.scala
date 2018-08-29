package sparkSqlMode

import org.apache.spark.sql.SparkSession

/**
  * Created by lenovo on 2018/8/24.
  */
object FirstSql {
  def main(args: Array[String]) {
    val spark: SparkSession = SparkSession.builder().appName("FirstSql").master("local[*]").getOrCreate()
    spark.read.json("")
  }
}
