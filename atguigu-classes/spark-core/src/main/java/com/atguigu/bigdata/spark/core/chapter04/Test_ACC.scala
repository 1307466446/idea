package com.atguigu.bigdata.spark.core.chapter04

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 累加器：分布式共享只写变量
 */

object Test_ACC {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val value: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5), 2)

    val sumAccumulator: LongAccumulator = sc.longAccumulator("sum")

    // 累加器要在行动算子中调用，不然会有重复累加的问题
    // 和累加器最搭配的行动算子就是foreach，多线程的对每个分区的元素进行遍历,累加器在遍历的时候就可以进行累加
    value.foreach(sumAccumulator.add(_))

    println(sumAccumulator.value)

    //4.关闭连接
    sc.stop()
  }

}
