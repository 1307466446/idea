package chapter07

object Test_List {
  def main(args: Array[String]): Unit = {

    // List是个抽象类，我们调用apply方法产生对象
    val list: List[Int] = List(1, 2, 3, 4)
    val list1: List[Int] = list :+ 5
    val list3: List[Int] = 6 :: list1
    println(list1)

    // 链表的头插法
    // NIL 等价于 List()
    val list2: List[Int] = 10 :: 20 :: 30 :: Nil
    println(list2)

    // 集合间合并
    val list4: List[Int] = list2 ::: list3
    println(list4)

    val str: String = list4.mkString(",")
    println(str)

    val tuples: List[(Int, Int)] = list2.zip(list3)
    println(tuples)


    val iterator: Iterator[List[Int]] = list4.sliding(3, 1)
    for (elem <- iterator) {
      println(elem)
    }

    val iterator1: Iterator[List[Int]] = list3.sliding(5, 5)
    for (elem <- iterator1) {
      println(elem)
    }

  }

}
