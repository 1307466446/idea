package chapter06

/**
 * 子类继承父类
 * 执行流程
 */
object Test03 {

  def main(args: Array[String]): Unit = {

    val student0: Student03 = new Student03()
    println("=============================")
    val student01: Student03 = new Student03(10)
  }

}

class Persosn03() {
  println("我是父类主构造器")
  var name: String = _

  def this(name: String) = {
    this()
    this.name = name
    println("我是父类的子构造器  " + name)
  }
}


// scala中继承本质是继承一个构造器
// 继承Person03主构造器就会先把Person03主构造器代码执行一遍
// 子类都会去先调用父类的主构造器
class Student03 extends Persosn03() {
  println("我是子类的主构造器")
  var age: Int = _

  def this(age: Int) = {
    this()
    this.age = age
    println("我是子类地辅助构造器  " + age)
  }
}
