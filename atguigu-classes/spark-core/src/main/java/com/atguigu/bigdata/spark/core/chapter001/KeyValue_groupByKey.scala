package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object KeyValue_groupByKey {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("SCtest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[(Char, Int)] = sc.makeRDD(List(('a', 1), ('b', 1), ('a', 2), ('b', 2)), 2)
    val value1: RDD[(Char, Iterable[Int])] = value.groupByKey()
    val value2: RDD[(Char, Int)] = value1.map(t => (t._1, t._2.sum))
    value2.collect().foreach(println)
    sc.stop()
  }

}
