package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Value_sortBy {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)


    val value: RDD[Int] = sc.makeRDD(List(4, 7, 0, 8, 3, 1, 9, 2, 10), 2)
    value.mapPartitionsWithIndex((i, list) => list.map((i, _))).collect().foreach(println)
    println("======================")
    value.sortBy(i => i).mapPartitionsWithIndex((i, list) => list.map((i, _))).collect().foreach(println)


    Thread.sleep(1000000)

    //4.关闭连接
    sc.stop()
  }

}
