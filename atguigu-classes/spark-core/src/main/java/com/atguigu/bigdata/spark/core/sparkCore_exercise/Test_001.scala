package com.atguigu.bigdata.spark.core.sparkCore_exercise

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test_001 {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    // 点击总数  (品类id，点击总数)
    val value: RDD[String] = sc.textFile("input/user_visit_action.txt")
    val lineRDD: RDD[String] = value.filter(_.split("_")(6) != "-1")
    val clickTupleRDD: RDD[(String, Int)] = lineRDD.map(line => (line.split("_")(6), 1))
    val clickResultRDD: RDD[(String, Int)] = clickTupleRDD.reduceByKey(_ + _)

    // 下单总数  (品类id，下单总数)
    val lineRDD1: RDD[String] = value.filter(_.split("_")(8) != "null")
    val orderTupleRDD: RDD[(String, Int)] = lineRDD1.flatMap(_.split("_")(8).split(",").map((_, 1)))
    val orderResultRDD: RDD[(String, Int)] = orderTupleRDD.reduceByKey(_ + _)

    // 支付总数 (品类id，支付总数)
    val lineRDD2: RDD[String] = value.filter(_.split("_")(10) != "null")
    val value1: RDD[String] = lineRDD2.flatMap(_.split("_")(10).split(","))
    val payTupleRDD: RDD[(String, Int)] = value1.map((_, 1))
    val payResultRDD: RDD[(String, Int)] = payTupleRDD.reduceByKey(_ + _)

    // 处理的手法很重要
    val clickCountRDD: RDD[(String, (Int, Int, Int))] = clickResultRDD.map(i => (i._1, (i._2, 0, 0)))
    val orderCountRDD: RDD[(String, (Int, Int, Int))] = orderResultRDD.map(i => (i._1, (0, i._2, 0)))
    val payCountRDD: RDD[(String, (Int, Int, Int))] = payResultRDD.map(i => (i._1, (0, 0, i._2)))

    val unionCountRDD: RDD[(String, (Int, Int, Int))] = clickCountRDD.union(orderCountRDD).union(payCountRDD)

    val reduceCountRDD: RDD[(String, (Int, Int, Int))] = unionCountRDD.reduceByKey {
      case (res, elem) => (res._1 + elem._1, res._2 + elem._2, res._3 + elem._3)
    }

    reduceCountRDD.sortBy(_._2, false).take(10).foreach(println)






    //4.关闭连接
    sc.stop()
  }

}
