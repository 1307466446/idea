package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Value_map {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("SCtest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)
    val value: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val value1: RDD[Int] = value.map(_ * 2)
    value1.collect().foreach(println)
    sc.stop()
  }

}
