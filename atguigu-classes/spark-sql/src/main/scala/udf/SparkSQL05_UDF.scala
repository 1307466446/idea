package udf

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkSQL05_UDF {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")

    //2.创建SparkSession，该对象是提交Spark SQL的入口
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val df: DataFrame = spark.read.json("input/user.json")
    df.createTempView("user")

    spark.udf.register("addName", (name: String) => {
      "name:" + name
    })
    spark.sql("select addName(name), age from user").show()




    //4.关闭连接
    spark.stop()
  }

}
