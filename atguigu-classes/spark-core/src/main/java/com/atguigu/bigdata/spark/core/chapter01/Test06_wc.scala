package com.atguigu.bigdata.spark.core.chapter01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test06_wc {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)
    val value: RDD[String] = sc.makeRDD(List("Hello Scala", "Hello Spark", "Hello World"))
    val value1: RDD[String] = value.flatMap(_.split(" "))
    val value2: RDD[(String, Int)] = value1.map((_, 1))
    val value3: RDD[(String, Iterable[(String, Int)])] = value2.groupBy(_._1)
    val value4: RDD[(String, Int)] = value3.map(tuple => (tuple._1, tuple._2.size))
    value4.collect().foreach(println)
    sc.stop()
  }


}
