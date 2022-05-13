package com.atguigu.bigdata.spark.core.chapter01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * distinct走shuffle
 */
object test07_testDistinct {


  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 1, 2, 4), 2)
    //    value.mapPartitionsWithIndex((index, list) => list.map((index, _))).collect().foreach(println)


    println("=======================")
    //    value.distinct().mapPartitionsWithIndex((index, list) => list.map((index, _))).collect().foreach(println)
    val value1: RDD[(Int, Null)] = value.map((_, null)).reduceByKey((res, elem) => elem)
    value1.foreach(println)
    sc.stop()
  }

}
