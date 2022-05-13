package com.atguigu.bigdata.spark.core.sparkCore_exercise

import com.atguigu.bigdata.spark.core.sparkCore_exercise.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.{immutable, mutable}

object Test_000001ACC {

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

    val acc: MyAcc = new MyAcc
    sc.register(acc, "acc")

    value.foreach(user => {
      acc.add(user)
    })

    val value1: mutable.Map[(String, String), Long] = acc.value

    val map: Map[String, mutable.Map[(String, String), Long]] = value1.groupBy(_._1._1)

    val infoes: immutable.Iterable[CategoryCountInfo] = map.map({
      case (id, map) =>
        val l: Long = map.getOrElse((id, "click"), 0)
        val l1: Long = map.getOrElse((id, "order"), 0)
        val l2: Long = map.getOrElse((id, "pay"), 0)
        CategoryCountInfo(id, l, l1, l2)
    })
    infoes.toList.sortBy(i => (i.clickCount, i.orderCount, i.payCount))(Ordering[(Long, Long, Long)].reverse).take(10).foreach(println)

    //4.关闭连接
    sc.stop()
  }

}


class MyAcc extends AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]] {

  val map: mutable.HashMap[(String, String), Long] = mutable.HashMap()

  override def isZero: Boolean = map.isEmpty

  override def copy(): AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]] = new MyAcc

  override def reset(): Unit = map.clear()

  override def add(v: UserVisitAction): Unit = {
    if (v.click_category_id != "-1") {
      val key: (String, String) = (v.click_category_id, "click")
      map.put(key, map.getOrElse(key, 0L) + 1L)
    } else if (v.order_category_ids != "null") {
      v.order_category_ids.split(",").foreach(id => {
        val key: (String, String) = (id, "order")
        map.put(key, map.getOrElse(key, 0L) + 1L)
      })
    } else if (v.pay_category_ids != "null") {
      v.pay_category_ids.split(",").foreach(id => {
        val key: (String, String) = (id, "pay")
        map.put(key, map.getOrElse(key, 0L) + 1L)
      })
    }
  }

  override def merge(other: AccumulatorV2[UserVisitAction, mutable.Map[(String, String), Long]]): Unit = {
    // 两个map进行合并
    val value1: mutable.Map[(String, String), Long] = other.value
    value1.foreach({
      case ((id, name), count) => map.put((id, name), map.getOrElse((id, name), 0L) + count)
    })
  }

  override def value: mutable.Map[(String, String), Long] = map
}
