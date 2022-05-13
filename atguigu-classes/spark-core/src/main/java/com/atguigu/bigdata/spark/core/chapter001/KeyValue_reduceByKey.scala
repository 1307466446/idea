package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object KeyValue_reduceByKey {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("SCtest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 4), ("a", 2), ("b", 5), ("a", 3), ("b", 6)), 2)

    val value1: RDD[(String, Int)] = value.reduceByKey(_ + _)
    print(value1.partitioner)

    value1.collect().foreach(println)

    println("=========================")
    val value2: RDD[(String, Int)] = value1.reduceByKey(_ + _)
    print(value2.partitioner)

    value2.collect().foreach(println)
    sc.stop()
  }

}
