package com.atguigu.bigdata.spark.core.sparkCore_exercise

import com.atguigu.bigdata.spark.core.sparkCore_exercise.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test_00001 {

  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkCoreTest").setMaster("local[*]")

    //2.创建SparkContext，该对象是提交Spark App的入口
    val sc: SparkContext = new SparkContext(conf)

    val lineRDD: RDD[String] = sc.textFile("input/user_visit_action.txt")

    val value: RDD[CategoryCountInfo] = lineRDD.flatMap(s => {
      val data: Array[String] = s.split("_")
      val userVisitAction = UserVisitAction(data(0), data(1), data(2), data(3), data(4), data(5), data(6), data(7), data(8), data(9), data(10), data(11), data(12))
      if (data(6) != "-1") {
        List(CategoryCountInfo(userVisitAction.click_category_id, 1, 0, 0))
      } else if (data(8) != "null") {
        userVisitAction.order_category_ids.split(",").map(CategoryCountInfo(_, 0, 1, 0)).toList
      } else if (data(10) != "null") {
        userVisitAction.pay_category_ids.split(",").map(CategoryCountInfo(_, 0, 0, 1)).toList
      } else {
        Nil
      }
    })

    val value1: RDD[(String, CategoryCountInfo)] = value.map(i => (i.categoryId, i))

    val value2: RDD[(String, CategoryCountInfo)] = value1.reduceByKey((res, elem) => {
      res.clickCount += elem.clickCount
      res.orderCount += elem.orderCount
      res.payCount += elem.payCount
      res
//       如果属性不可变 val 需要重新创建一个对象返回
//      CategoryCountInfo(res.categoryId, res.clickCount + elem.clickCount, res.orderCount + elem.orderCount, res.payCount + elem.payCount)
    })

    value2.map(_._2).sortBy(i => (i.clickCount, i.orderCount, i.payCount), false).take(10).foreach(println)


    //4.关闭连接
    sc.stop()
  }

}
