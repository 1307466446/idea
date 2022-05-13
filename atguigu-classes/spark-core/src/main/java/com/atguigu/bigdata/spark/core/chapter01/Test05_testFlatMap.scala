package com.atguigu.bigdata.spark.core.chapter01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test05_testFlatMap {


  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[List[Int]] = sc.makeRDD(List(List(1, 2, 3), List(4, 5, 6)), 2)
    val value3: RDD[Int] = value.flatMap(list => list)
    value3.mapPartitionsWithIndex((index, list) => list.map((index, _))).foreach(println)

    println("=============================")

    val value1: RDD[String] = sc.makeRDD(List("hello world", "hello tommorrow", "hello ev"), 2)
    val value2: RDD[String] = value1.flatMap(_.split(" "))
    value2.mapPartitionsWithIndex((index, list) => list.map((index, _))).collect().foreach(println)


    println("=============================")


    val value4: RDD[String] = sc.textFile("input/1.sql",1)
    val value5: RDD[Array[String]] = value4.glom()
    val value6: RDD[String] = value5.map(arr => arr.mkString("\n"))
    value6.collect().foreach(println)

    sc.stop()
  }

}
