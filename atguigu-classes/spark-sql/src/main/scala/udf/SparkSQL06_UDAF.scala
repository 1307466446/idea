package udf

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{DataFrame, Encoder, Encoders, SaveMode, SparkSession, functions}

object SparkSQL06_UDAF {
  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")

    //2.创建SparkSession，该对象是提交Spark SQL的入口
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    // 默认读parquet文件
    //    spark.read.load()
    val df: DataFrame = spark.read.json("input/user.json")

    df.createTempView("user")

    spark.udf.register("myAvg", functions.udaf(MyAvg()))


    val df1: DataFrame = spark.sql("select myAvg(age) from user")

    // 指定保存的格式
    //    df1.write.json("output1")

    // 默认保存格式为二进制 parquet
    df.write.save("output2")

    // 指定文件的保存模式
    // 默认模式：文件夹存在了就报错
    //    df1.write.mode(SaveMode.ErrorIfExists).json("output1")

    //    df1.write.mode(SaveMode.Append).json("output1")
    //
    //    df1.write.mode(SaveMode.Overwrite).json("output1")
    // 文件夹存在了，就不操作
    //    df1.write.mode(SaveMode.Ignore).json("output1")


    //4.关闭连接
    spark.stop()
  }

}

case class Buff1(var sum: Long, var count: Long)

case class MyAvg() extends Aggregator[Long, Buff1, Double] {


  override def zero: Buff1 = Buff1(0L, 0L)

  override def reduce(b: Buff1, a: Long): Buff1 = {
    b.sum += a
    b.count += 1
    b
  }

  override def merge(b1: Buff1, b2: Buff1): Buff1 = {
    b1.sum += b2.sum
    b1.count += b2.count
    b1
  }

  override def finish(reduction: Buff1): Double = {
    reduction.sum.toDouble / reduction.count
  }

  //
  override def bufferEncoder: Encoder[Buff1] = Encoders.kryo

  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}
