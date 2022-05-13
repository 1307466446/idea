package com.atguigu.bigdata.spark.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{RangePartitioner, SparkConf, SparkContext}

object Test {

  def main(args: Array[String]): Unit = {


   //1.创建SparkConf并设置App名称
   val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

   //2.创建SparkContext，该对象是提交Spark App的入口
   val sc: SparkContext = new SparkContext(conf)

    val value: RDD[String] = sc.makeRDD(List("hello world","hello sss"), 2)


    val value1: RDD[String] = value.flatMap(_.split(" "))

    val value2: RDD[(String, Int)] = value1.map((_, 1))

    value2.collect().foreach(println)

   //4.关闭连接
   sc.stop()
  }


}
