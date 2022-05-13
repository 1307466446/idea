package chapter01

object NameCall {

  def main(args: Array[String]): Unit = {

//    name:=>String 这里的String是代码块的返回值，需要申明
//    每次调用都会走一次代码块，并把返回值赋给name
    def sayHi(name: => String) = {
      println(s"名字是: ${name}")
      name
    }

    // 名调用
    // 将代码块赋值给name
    var a = 1
    val name: String = sayHi({
      a += 1
      println(a)
      "jack"
    })
    println(name)
  }

}
