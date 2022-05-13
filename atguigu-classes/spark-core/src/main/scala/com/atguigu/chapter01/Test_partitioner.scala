package com.atguigu.chapter01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test_partitioner {
  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)
    val value: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("a", 3), ("b", 1)), 2)

    println(value.partitioner)

    value.mapPartitionsWithIndex((i, list) => list.map((i, _))).collect().foreach(println)

    println("1111111")

    //4.关闭连接
    sc.stop()
  }
}
