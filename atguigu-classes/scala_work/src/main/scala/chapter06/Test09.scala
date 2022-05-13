package chapter06

object Test09 {
  def main(args: Array[String]): Unit = {
    // 用apply方法创建一个对象
    // apply方法是object和类中定义的一个方法，用于返回一个对象 (规定了)
    // 但是apply方法的用途不仅于此，apply方法我们可以自定义

    // apply方法调用时，方法的名可以省略,写成如下形式
    // 在没有new关键字时，通过Person09()返回兑现兑现搞得都是调用了apply方法
    val person0: Person09 = Person09()
    println(person0.name)

    val name: Unit = Person09("萨萨")

    // apply方法在类中的使用
    val person01: Person09 = new Person09
    person01()
  }

}

class Person09  {
  val name:String = "tony"

  def apply(): Person09 = new Person09()

}

// 伴生对象中的属性和方法都是静态的
object Person09 {
  def apply(): Person09 = new Person09()

  def apply(s:String)=
    println(s"我是 ${s}")


}
