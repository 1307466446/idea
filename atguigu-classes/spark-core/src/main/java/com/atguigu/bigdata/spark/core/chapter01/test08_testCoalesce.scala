package com.atguigu.bigdata.spark.core.chapter01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * coalesce 缩减分区
 * 默认不走分区，不会有shuffle
 */
object test08_testCoalesce {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8), 4)
    value.mapPartitionsWithIndex((index, list) => list.map((index, _))).collect().foreach(println)

    println("==========================")

    val value2: RDD[Int] = value.coalesce(2)
    println(value2.partitioner)

//    value2.mapPartitionsWithIndex((index, list) => list.map((index, _))).collect().foreach(println)


    /**
     * repartition() 增加分区
     * 底层调用的是 coalesce(4,true)
     * 走shuffle
     */
    println("==========================")

    val value1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8), 2)

    value1.repartition(4).mapPartitionsWithIndex((index, list) => list.map((index, _))).collect().foreach(println)

    sc.stop()


  }

}
