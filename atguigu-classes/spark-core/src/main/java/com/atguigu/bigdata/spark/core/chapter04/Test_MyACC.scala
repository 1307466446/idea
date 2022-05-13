package com.atguigu.bigdata.spark.core.chapter04

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object Test_MyACC {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val rdd: RDD[String] = sc.makeRDD(List("Hello", "Hello", "Hello", "Hello", "Hi", "Spark", "Spark"), 2)

    // 如何使用自定义ACC
    val acc: MyAcc = new MyAcc
    sc.register(acc)

    // 用foreach调用累加器
    rdd.foreach(i => acc.add(i))
    // 打印累加器结果
    println(acc.value)

    //4.关闭连接
    sc.stop()
  }

}


class MyAcc extends AccumulatorV2[String, mutable.Map[String, Int]] {

  val map: mutable.Map[String, Int] = mutable.Map()

  override def isZero: Boolean = map.isEmpty

  override def copy(): AccumulatorV2[String, mutable.Map[String, Int]] = new MyAcc

  override def reset(): Unit = map.clear()

  override def add(v: String): Unit = {
    if (v.startsWith("H")) {
      map.put(v, map.getOrElse(v, 0) + 1)
    }
  }

  // 将每个分区里面累加器与driver端的累加器合并
  // other是executor端的累加器
  override def merge(other: AccumulatorV2[String, mutable.Map[String, Int]]): Unit = {
    other.value.foreach {
      case (key, value) => map.put(key, map.getOrElse(key, 0) + value)
    }
  }

  override def value: mutable.Map[String, Int] = map
}
