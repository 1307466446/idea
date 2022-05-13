package chapter06

/**
 * scala的抽象类中，既有抽象属性，也有抽象方法
 * 匿名子类就是多态的体现
 * 父类的引用指向子类的实现
 * 匿名子类里面定义的东西 person0 肯定用不了
 */
object Test07 {

  def main(args: Array[String]): Unit = {
    val student0: Student07 = new Student07
    student0.sayHi("tony")
    println(student0.name)
    println(student0.age)
    println("=========================")
    // 匿名子类
    // 多态的体现
    //new Person07其实是一个匿名的子类，父类的引用指向子类的实现
    val person0: Person07 = new Person07 {
      override val name: String = "jack"
      override var age: Int = 19

      override def sayHi(s: String): Unit = {
        println(s"hi ${s} 我是 $name")
      }
    }
  }

}


// scala的抽象类中 既有抽象属性也有抽象方法
abstract class Person07 {
  // scala中能有抽象属性，是因为底层封装了(类似java中的get,set)，底层属性的读写方法抽象了
  val name: String
  var age: Int

  def sayHi(s: String): Unit
}

class Student07 extends Person07{
  override val name: String = "Tony"
  override var age: Int = 10

  override def sayHi(s: String): Unit = {
    println(s"hi ${s}")
  }
}
