package chapter06

object Test10 {

  def main(args: Array[String]): Unit = {

    //
    val computer: Computer = new Computer
    // 特质的混入
    // 匿名子类
    val tv: TV with Monitor = new TV with Monitor {
      override val monitorName: String = "LG"
      override val monitorModel: String = "p0009"

      override def display(): Unit = println("棒")
    }
  }

  class TV {

  }

}

// trait和Java中接口一样,既有抽象属性和方法 也有非抽象属性和方法
trait Monitor {
  val monitorName: String
  val monitorModel: String

  def display(): Unit

  val monitorName01: String = "三星"
  val monitorModel01: String = "A9870"

  def display01() = {
    println(s"品牌 $monitorName01 型号 $monitorModel01 显示等级A+")
  }
}

trait CPU {
  val cpuName: String

  def play(): Unit

  val cpuName01 = "AMD"

  def play01(): Unit = {
    println("good")
  }
}


class Computer extends Monitor with CPU {
  override val monitorName: String = "dell"
  override val monitorModel: String = "D0000"

  override def display(): Unit = {}

  override val cpuName: String = "Intel"

  override def play(): Unit = {}
}
