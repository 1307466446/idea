package com.atguigu.bigdata.spark.core.case01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test_Top03 {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    //
    val lineRDD: RDD[String] = sc.textFile("input\\agent.log")
    lineRDD.map(line => {
      val word: Array[String] = line.split(" ")
      ((word(1), word(4)), 1)
    }).reduceByKey(_ + _).map {
      case ((province, advertisement), count) => (province, (advertisement, count))
    }.groupByKey().mapValues(_.toList.sortWith(_._2 > _._2).take(3)).collect().foreach(println)


    //4.关闭连接
    sc.stop()
  }

}
