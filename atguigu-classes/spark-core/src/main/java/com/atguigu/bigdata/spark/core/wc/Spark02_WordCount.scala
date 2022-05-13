package com.atguigu.bigdata.spark.core.wc

import org.apache.spark.{SparkConf, SparkContext}

object Spark02_WordCount {


  def main(args: Array[String]): Unit = {

    // TODO 建立与spark的连接
    val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(sparkConf)

    // TODO 执行业务
    val lines = sc.textFile("datas")
    val words = lines.flatMap(_.split(" "))
    val wordToOne = words.map((_, 1))
    // 先分组
    val wordGroup = wordToOne.groupBy(_._1) // (hello,Seq((hello,1), (hello,1), (hello,1), (hello,1)))
    // 再聚合
    val wordToCount = wordGroup.map({
      case (word, list) => {
        list.reduce((t1, t2) => (t1._1, t1._2 + t2._2))
      }
    })
    val tuples = wordToCount.collect()
    tuples.foreach(println)

    // TODO 关闭连接
    sc.stop()

  }

}
