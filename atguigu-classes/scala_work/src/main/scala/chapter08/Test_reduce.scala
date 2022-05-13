package chapter08

object Test_reduce {

  def main(args: Array[String]): Unit = {

    // reduce 归约函数
    val list: List[Int] = List(0, 1, 2, 3, 4)
    //(op: (A1, A1) => A1): A1  res：结果值，elem是集合一个一个的元素
    // res和elem类型必须相同,res默认值是集合的第一个元素
    // res+elem的结果再当作参数传进函数res
    val str: Int = list.reduce((res, elem) => res + elem)
    println(str)
    // 上下等价
    var res = 0
    for (elem <- list) {
      res = res + elem
    }
    println(res)

    val sum: Int = list.reduce(_ + _)
    println(sum)


  }

}
