package chapter07

/**
 * 不可变数组(特例)
 * 可以对数组中元素进行修改
 */
object Test_Array {

  def main(args: Array[String]): Unit ={

    val arr =  new Array[Int](5)
    val array: Array[Int] = Array(1, 2, 3)

    // 添加元素但是要创建一个新的数组
    val array1: Array[Int] = array :+ 4
    array1(0)=100
    array1.foreach(println)


  }

}
