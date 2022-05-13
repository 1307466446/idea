package com.atguigu.bigdata.spark.core.chapter01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test03_testMapPartition {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4),2)
    val value1: RDD[Int] = value.mapPartitions(list => {
      list.map(_ * 2)
    })
    value1.collect().foreach(println)

    println("===============================")

    // 简写
    value.mapPartitions(_.map(_ * 2)).collect().foreach(println)


    sc.stop()
  }


}
