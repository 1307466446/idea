package com.atguigu.bigdata.spark.core.sparkCore_exercise

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test01 {

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



    // (品类id,(点击总数,下单总数,支付总数))
    val value2: RDD[(String, (Iterable[Int], Iterable[Int], Iterable[Int]))] = clickResultRDD.cogroup(orderResultRDD, payResultRDD)

    val value3: RDD[(String, (Int, Int, Int))] = value2.mapValues {
      case (cr, or, pr) =>
        var click = 0
        var order = 0
        var pay = 0
        if (cr.nonEmpty)
          click = cr.head
        if (or.nonEmpty)
          order = or.head
        if (pr.nonEmpty)
          pay = pr.head
        (click, order, pay)
    }

    value3.sortBy(_._2,false).take(10).foreach(println)

    //4.关闭连接
    sc.stop()
  }

}
