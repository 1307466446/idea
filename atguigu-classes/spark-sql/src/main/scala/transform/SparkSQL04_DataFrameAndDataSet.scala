package transform

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object SparkSQL04_DataFrameAndDataSet {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")

    //2.创建SparkSession，该对象是提交Spark SQL的入口
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    //  开发中更多的是通过rdd或df转换为ds

    val df: DataFrame = spark.read.json("input/user.json")

    import spark.implicits._

    // df转ds
    val ds: Dataset[User] = df.as[User]

    // ds转df
    val df2: DataFrame = ds.toDF("a", "b")

    df2.show()



    //4.关闭连接
    spark.stop()
  }

}
