package chapter06

/**
 * 抽象类中
 */
object Test08 {
  def main(args: Array[String]): Unit = {

    // 多态
    // scala中的多态调用会使用子类重写的属性和方法
    val person0: Person08 = new Student08
    println(person0.name01)
    println(person0.age01)

  }

}

// 抽象类中既可以有抽象属性和抽象方法 也可以有非抽象的属性和方法
abstract class Person08 {
  val name: String
  var age: Int

  def sayHi(): Unit

  val name01: String = "bob"
  var age01: Int = 19

  def sayHi01 =
    println("hi1")
}

// 对于非抽象 var修饰的属性，我们不能进行重写，只需要照常赋值即可
class Student08 extends Person08 {
  override val name: String = "titi"
  override var age: Int = 18

  override def sayHi(): Unit = println("hi")

  // 对于非抽象的属性和方法重写注意
  // var修饰的属性，重写时直接赋值就行，不用override
  override val name01: String = "buck"
  age01 = 20

  override def sayHi01: Unit = println("hi1的重写")
}
