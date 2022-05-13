package chapter07

import scala.collection.mutable.ListBuffer

object Test_ListBuffer {

  def main(args: Array[String]): Unit = {
    val buffer: ListBuffer[Int] = ListBuffer()
    val buffer1: ListBuffer[Int] = new ListBuffer[Int]

    buffer.append(1, 2, 3)
    buffer.remove(0)
    buffer(0)=10
    buffer.update(0,100)
    println(buffer)
  }

}
