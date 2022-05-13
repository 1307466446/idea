package chapter08

object Test_Fold {
  def main(args: Array[String]): Unit = {
    val list: List[Int] = List(0, 1, 2, 3, 4)


    // foldLeft()传入的第一个值就是对result=的初始值
    // result+sum
    //def foldLeft[B](z: B)(op: (B, A) => B): B
    val str: String = list.foldLeft("a")(_ + _)
    println(str)
    // def fold(z: A)(op: (A, A) => A): A
    val i: Int = list.fold(10)(_ + _)
    println(i)

    val tuple: (String, Int) = list.foldLeft(("value", 0))((res, elem) => (res._1, res._2 + elem))
    println(tuple)
  }

}
