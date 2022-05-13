package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object DoubleValue_union {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val rdd1: RDD[(String, Int)] = sc.makeRDD(List(("a", 1), ("b", 2), ("c", 3), ("d", 7)))
    val rdd2: RDD[(String, Int)] = sc.makeRDD(List(("a", 4), ("b", 5), ("c", 6)))
    val rdd3: RDD[(String, Int)] = sc.makeRDD(List(("a", 8), ("b", 9), ("c", 10)))

    val value: RDD[(String, Int)] = rdd1.union(rdd2).union(rdd3)
    val value1: RDD[(String, Iterable[Int])] = value.groupByKey()

    value1.foreach(println)

    //4.关闭连接
    sc.stop()
  }

}
