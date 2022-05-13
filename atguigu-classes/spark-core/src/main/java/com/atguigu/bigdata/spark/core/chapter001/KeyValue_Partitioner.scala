package com.atguigu.bigdata.spark.core.chapter001

import org.apache.spark.{Partitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object KeyValue_Partitioner {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("sctest").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)
    val value: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)
    val value1: RDD[(Int, Int)] = value.map((_, 1))
    val value2: RDD[(Int, Int)] = value1.partitionBy(new Partitioner {
      // spark只能按key进行分区
      override def numPartitions: Int = 2
      override def getPartition(key: Any): Int =
        key match {
          case i: Int => if (i % 2 == 0) 1 else 0
          case _ => 0
        }
    })
    value2.mapPartitionsWithIndex((i, list) => list.map((i, _))).collect().foreach(println)

    Thread.sleep(10000000)

    sc.stop()
  }

}
