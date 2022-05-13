package com.atguigu.bigdata.spark.core.sparkCore_exercise

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test_0001_reWriter {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val lineRDD: RDD[String] = sc.textFile("input/user_visit_action.txt")

    val value: RDD[(String, (Int, Int, Int))] = lineRDD.flatMap(line => {
      val data: Array[String] = line.split("_")
      if (data(6) != "-1") {
        List((data(6), (1, 0, 0)))
      }
      else if (data(8) != "null") {
        data(8).split(",").map(i => (i, (0, 1, 0)))
      } else if (data(10) != "null") {
        data(10).split(",").map(i => (i, (0, 0, 1)))
      } else {
        Nil
      }
    })

    val value1: RDD[(String, (Int, Int, Int))] = value.reduceByKey((res, elem) => (res._1 + elem._1, res._2 + elem._2, res._3 + elem._3))

    val value2: RDD[(String, Double)] = value1.mapValues({
      case (i, i1, i2) => i * 0.2 + i1 * 0.3 + i2 * 0.5
    })

    value2.sortBy(_._2,false).take(10).foreach(println)





    //4.关闭连接
    sc.stop()
  }

}
