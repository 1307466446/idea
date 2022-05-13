package chapter01

object HighFunc {

  def main(args: Array[String]): Unit = {
    def test(name: String) = {
      println(name)
      name
    }

    val test1: String => String = test
    val test2 = test _

    def sayHi() = {
      println("dddd")
      "1234"
    }

    val hi: () => String = sayHi
    println(hi)
    val hi1: String = sayHi
    val hi2 = sayHi
    val hi3 = sayHi _
    println(hi3)


  }

}
