package transform

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQL03_RDDAndDataSet {
  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")

    //2.创建SparkSession，该对象是提交Spark SQL的入口
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val sc: SparkContext = spark.sparkContext

    val lineRDD: RDD[String] = sc.textFile("input/user.txt")
    val value: RDD[User] = lineRDD.map(line => {
      val data: Array[String] = line.split(",")
      User(data(0), data(1).toLong)
    })

    import spark.implicits._
    val ds: Dataset[User] = value.toDS()
    ds.show()

    // 转换回去
    val rdd: RDD[User] = ds.rdd
    rdd.foreach(println)

    //4.关闭连接
    spark.stop()
  }


}
