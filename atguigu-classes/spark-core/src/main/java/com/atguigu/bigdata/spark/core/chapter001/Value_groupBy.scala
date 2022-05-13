package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Value_groupBy {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("sctest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)
    val value: RDD[String] = sc.makeRDD(List("hello scala", "hello spark", "hello flink"))
    val value1: RDD[String] = value.flatMap(_.split(" "))
    val value2: RDD[(String, Iterable[String])] = value1.groupBy(s => s)
    val value3: RDD[(String, Int)] = value2.mapValues(_.size)
    value3.collect().foreach(println)
    value3.mapPartitionsWithIndex((index, list) => list.map((index, _))).collect().foreach(println)

    val value4: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val value5: RDD[(Int, Iterable[Int])] = value4.groupBy(_ % 2)
    value5.map(tuple => (tuple._1, tuple._2.toList)).collect().foreach(println)
    value5.collect().foreach(println)
    sc.stop()

  }


}
