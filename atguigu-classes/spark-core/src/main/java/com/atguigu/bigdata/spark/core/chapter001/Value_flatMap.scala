package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Value_flatMap {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("sctest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)
    val value: RDD[List[Int]] = sc.makeRDD(List(List(1, 2), List(3, 4), List(5, 6), List(7)), 2)
    val value1: RDD[Int] = value.flatMap(list => list)
    value1.mapPartitionsWithIndex((index, list) => list.map((index, _))).collect().foreach(println)

    val value2: RDD[String] = sc.makeRDD(List("hello scala", "hello spark"),2)
    val value3: RDD[String] = value2.flatMap(_.split(" "))
    value3.mapPartitionsWithIndex((index, list) => list.map((index, _))).collect().foreach(println)
    sc.stop()
  }

}
