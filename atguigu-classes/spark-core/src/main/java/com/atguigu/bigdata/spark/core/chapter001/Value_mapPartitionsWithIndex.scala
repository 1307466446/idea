package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Value_mapPartitionsWithIndex {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("sctest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)
    val value: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val value1: RDD[(Int, Int)] = value.mapPartitionsWithIndex((index, list) => list.map((index, _)))
    value1.collect().foreach(println)
    sc.stop()
  }
}
