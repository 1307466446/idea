package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Value_filter {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)
    val value: RDD[Int] = sc.makeRDD(1 to 4, 2)
    val value1: RDD[Int] = value.filter(_ % 2 == 0)
    value1.mapPartitionsWithIndex((i, list) => list.map((i, _))).collect().foreach(println)
    sc.stop()
  }

}
