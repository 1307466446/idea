package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Value_mapPartitions {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("SCtest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)
    val value: RDD[Int] = sc.makeRDD(1 to 4, 2)
    val value1: RDD[Int] = value.mapPartitions(_.map(_ * 2))

    // 简写
    val value2: RDD[Int] = value.mapPartitions(_.map(_ * 2))
    value1.collect().foreach(println)


    sc.stop()
  }


}
