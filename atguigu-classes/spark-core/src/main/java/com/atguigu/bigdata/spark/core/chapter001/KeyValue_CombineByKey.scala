package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object KeyValue_CombineByKey {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)
    val value: RDD[(String, Int)] = sc.makeRDD(List(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)), 2)
    // 累加求相应的平均值
    // 第一个分区 (88,1) => 去和 91 相加 => (179,2) => 分区间map合并 (274,3)
    // 第二个分区 (95,1) => 只有一个     => (95,1)
    val value1: RDD[(String, (Int, Int))] = value.combineByKey((_, 1), (res: (Int, Int), elem) => (res._1 + elem, res._2 + 1), (res: (Int, Int), elem: (Int, Int)) => (res._1 + elem._1, res._2 + elem._2))
    value1.collect().foreach(println)

    //
    val value2: RDD[(String, Int)] = value1.map(tuple => (tuple._1, tuple._2._1 / tuple._2._2))

    value2.collect().foreach(println)
    //4.关闭连接
    sc.stop()
  }

}
