package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object KV_foldByKey {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 4), ("a", 2), ("b", 5), ("a", 3), ("b", 6)),2)

    val value1: RDD[(String, Int)] = value.foldByKey(10)(_ + _)

    value1.mapPartitionsWithIndex((i,list)=>list.map((i,_))).collect().foreach(println)

    //4.关闭连接
    sc.stop()
  }

}
