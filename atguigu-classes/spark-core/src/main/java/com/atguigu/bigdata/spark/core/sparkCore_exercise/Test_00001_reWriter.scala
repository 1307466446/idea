package com.atguigu.bigdata.spark.core.sparkCore_exercise

import com.atguigu.bigdata.spark.core.sparkCore_exercise.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test_00001_reWriter {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val lineRDD: RDD[String] = sc.textFile("input/user_visit_action.txt")

    val value: RDD[UserVisitAction] = lineRDD.map(line => {
      val data: Array[String] = line.split("_")
      UserVisitAction(data(0), data(1), data(2), data(3), data(4), data(5), data(6), data(7), data(8), data(9), data(10), data(11), data(12))
    })

    val value1: RDD[CategoryCountInfo] = value.flatMap(data => {
      if (data.click_category_id != "-1") {
        List(CategoryCountInfo(data.click_category_id, 1, 0, 0))
      } else if (data.order_category_ids != "null") {
        data.order_category_ids.split(",").map(CategoryCountInfo(_, 0, 1, 0)).toList
      } else if (data.pay_category_ids != "null") {
        data.pay_category_ids.split(",").map(CategoryCountInfo(_, 0, 0, 1)).toList
      } else {
        Nil
      }
    })

    val value2: RDD[(String, CategoryCountInfo)] = value1.map(i => (i.categoryId, i))

    val value3: RDD[(String, CategoryCountInfo)] = value2.reduceByKey((res, elem) => {
      res.clickCount += elem.clickCount
      res.orderCount += elem.orderCount
      res.payCount += elem.payCount
      res
    })

    val value4: RDD[CategoryCountInfo] = value3.map(i => (i._2))

    value4.sortBy(i=>(i.clickCount,i.orderCount,i.payCount),false).take(10).foreach(println)





    //4.关闭连接
    sc.stop()
  }

}
