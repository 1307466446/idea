package com.atguigu.chapter01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test_checkPoint {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    sc.setCheckpointDir("checkPoint")
    val value: RDD[String] = sc.makeRDD(List("hello world", "hello spark"))
    val value1: RDD[(String, Long)] = value.flatMap(_.split(" ")).map((_, System.currentTimeMillis()))

    value1.cache()

    value1.checkpoint()

    value1.collect().foreach(println)
    println("==========================")
    value1.collect().foreach(println)
    println("==========================")

    value1.collect().foreach(println)


    //4.关闭连接
    sc.stop()
  }

}
