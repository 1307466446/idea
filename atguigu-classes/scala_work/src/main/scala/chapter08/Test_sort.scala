package chapter08

object Test_sort {

  def main(args: Array[String]): Unit = {
    val tuples = List(("hello", 10), ("world", 2), ("scala", 9), ("haha", 4), ("hello", 1))

    val list: List[(String, Int)] = tuples.sorted
    println(list)

    val list1: List[(String, Int)] = tuples.sorted(Ordering[(String, Int)].reverse)
    println(list1)

    val list2: List[(String, Int)] = tuples.sortBy(_._2)(Ordering[Int].reverse)
    tuples.sortBy((tuple: (String, Int)) => tuple._2)(Ordering[Int].reverse)
    println(list2)




  }

}
