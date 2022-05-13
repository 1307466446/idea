package com.atguigu.chapter01

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD


object Test_wordCount {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("SCtest") // 在yarn上运行就不能写setMaster(“Local[*]”)
    val sc: SparkContext = new SparkContext(conf)
    val value: RDD[(String, Int)] = sc.textFile(args(0)).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
//    val value: RDD[(String, Int)] = sc.textFile(args(0)).flatMap(_.split(" ").map((_, 1)))
    sc.stop()
  }

}
