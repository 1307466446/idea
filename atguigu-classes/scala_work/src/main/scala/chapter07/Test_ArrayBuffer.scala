package chapter07

import scala.collection.mutable.ArrayBuffer

/**
 * 可变数组
 */
object Test_ArrayBuffer {
  def main(args: Array[String]): Unit = {
    val buffer: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    val buffer1: ArrayBuffer[Int] = ArrayBuffer[Int](1, 2, 3)

    buffer.append(1)
    buffer.foreach(println)


    buffer1.update(0, 10)
    buffer1(1) = 20
    // ArrayBuffer重写了toString方法
    // mkString(stringPrefix + "(", ", ", ")")
    println(buffer1)

    // 从下标0开始，往后删除几个
    buffer1.remove(0, 2)
    println(buffer1)


  }

}
