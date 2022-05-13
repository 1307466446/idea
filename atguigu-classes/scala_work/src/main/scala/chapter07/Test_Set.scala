package chapter07

import scala.collection.immutable.HashSet
import scala.collection.mutable


object Test_Set {

  def main(args: Array[String]): Unit = {
    val set: Set[Int] = Set(1, 2, 3, 1, 2, 3, 4, 5, 6)

    println(set.isInstanceOf[HashSet[Int]])

    // 当set元素小于等于4个时，不是HashSet，而是调用特殊的set对象
    val set1: Set[Int] = Set(1, 2, 3, 4)
    println(set1.isInstanceOf[HashSet[Int]]) // false


    // 可变set
    val set3: mutable.HashSet[Int] = new mutable.HashSet[Int]()
    val set2: mutable.Set[Int] = mutable.Set(11, 11, 22, 33, 44)

    set2.add(55)
    // 因为无序的就不能通过下标来删除数据
    val bool: Boolean = set2.remove(11)
    println(bool)

    set2.foreach(println)

  }

}
