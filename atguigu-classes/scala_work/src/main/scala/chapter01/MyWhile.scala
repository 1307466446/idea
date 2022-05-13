package chapter01


object MyWhile {

  def main(args: Array[String]): Unit = {

    def myWhile(b: => Boolean)(op: => Unit): Unit = {
      if (b) {
        op
      }
      myWhile(b)(op)
    }

    var i = 0
    myWhile(i < 5)({
      println(i)
      i += 1
    })

  }


}
