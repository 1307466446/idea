package chapter07

/**
 * 元组属于不可变
 */
object Test_tuple {

  def main(args: Array[String]): Unit ={

    // 元组的好处就是保存了我们每个元素的类型List不行只有Any类型
    val tuple: (String, Int) = ("a", 1)
    val tuple1: (String, Int, Boolean, Char) = ("a", 1, true, 'c')

    // 也可以根据几个参数使用Tuple几，一般不用
    val tuple2: (String, Int) = new Tuple2[String, Int]("no", 1)
  }


}
