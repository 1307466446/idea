package chapter06


/**
 * 继承父类的辅助构造器
 */
object Test05 {
  def main(args: Array[String]): Unit = {

    val student0: Student05 = new Student05("kunkun")
    println("=======================================")
    val student01: Student05 = new Student05(10)
  }
}


class Person05() {
  println("父类的主构造器调用")
  var name: String = _

  def this(name: String) = {
    this()
    this.name = name
    println("父类的辅助构造器调用  " + this.name)
  }
}


// scala继承的本质就是继承一个构造器,去运行构造器里面的方法
// 如果继承父类的有参构造器，则 子类的主构造器参数 必须大于等于 父类的构造器参数
class Student05(name: String) extends Person05(name: String) {
  println("子类的主构造器方法")
  var age: Int = _

  def this(age: Int) = {
    this("lili")
    this.age = age
    println("子类的辅助构造方法" + name + "===" + this.age)
  }

}
