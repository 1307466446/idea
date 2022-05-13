package com.atguigu.bigdata.spark.core.wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark01_WordCount {


  def main(args: Array[String]): Unit = {

    // TODO 建立与spark的连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    // TODO 执行业务
    val lines: RDD[String] = sc.textFile("datas")
    val words = lines.flatMap(_.split(" "))
    val wordToOne = words.map((_, 1))
    wordToOne.collect().foreach(println)
    println("=============================")
    // 直接用reduceByKey(是spark里的方法)：将相同的key的value进行聚合
    val wordToCount = wordToOne.reduceByKey(_ + _)
    wordToCount.collect().foreach(println)


    // TODO 关闭连接
    sc.stop()

  }

}
