package transform

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQL02_RDDAndDataFrame {
  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")

    //2.创建SparkSession，该对象是提交Spark SQL的入口
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val sc: SparkContext = spark.sparkContext

    val lineRDD: RDD[String] = sc.textFile("input/user.txt")

    val value: RDD[(String, String)] = lineRDD.map(line => {
      val data: Array[String] = line.split(",")
      (data(0), data(1))
    })

    import spark.implicits._

    // 普通的rdd转换df
    value.toDF().show()


    // 讲样例类的rdd转换为df
    val value1: RDD[User] = lineRDD.map(line => {
      val data: Array[String] = line.split(",")
      User(data(0), data(1).toLong)
    })
    val df2: DataFrame = value1.toDF()

    // 相互转换

    val userRDD: RDD[Row] = df2.rdd

    userRDD.foreach(row => println(row.getString(0), row.getLong(1)))


    //4.关闭连接
    spark.stop()
  }

}

case class User(name: String, age: Long) {}
