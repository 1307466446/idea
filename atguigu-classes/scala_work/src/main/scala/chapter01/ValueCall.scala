package chapter01


/**
 * 值调用
 */
object ValueCall {

  def main(args: Array[String]): Unit = {

    def sayHi(name: String) = {
      println(s"名字是: ${name}")
      name
    }


    // 在赋值的时候执行一遍，然后把代码块的结果返回 赋值给name
    var a = 1
    val name: String = sayHi({
      a += 1
      println(a)
      "jack"
    })
    println(name)



  }





}
