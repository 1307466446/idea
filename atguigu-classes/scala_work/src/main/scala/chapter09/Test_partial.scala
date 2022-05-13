package chapter09

object Test_partial {

  def main(args: Array[String]): Unit = {
    val list: List[Any] = List(1, 2, 3, 4, 5, 6,"hello")
    val list1: List[Any] = list.collect {
      case i: Int => i + 1
    }
    println(list1)


    // 偏函数再二元组或map时候使用的比较多
    val tuples: List[(String, Int)] = List(("a", 1), ("b", 2))
    val tuples1: List[(String, Int)] = tuples.map {
      case (str, i) => (str, i + 1)
    }
    println(tuples1)
  }

}
