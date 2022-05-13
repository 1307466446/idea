package com.atguigu.bigdata.spark.core.chapter01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object Test04_testMapPartitionWithIndex {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    value.mapPartitionsWithIndex((index, list) => {
      list.map((index, _))
    }).collect().foreach(println)

    println("========================")

    value.mapPartitionsWithIndex((index, list) => list.map((index,_))).collect().foreach(println)

    sc.stop()

  }

}
