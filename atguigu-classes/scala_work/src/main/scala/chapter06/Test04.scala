package chapter06

object Test04 {
  def main(args: Array[String]): Unit = {
    val person0: Person04 = new Person04("lili", 18, 1)
    //age就是成员属性了
    person0.age = 19
  }
}

// 关于主构造器的修饰的
// 没有修饰：不能最为类的成员属性
// var修饰: 可作为类的成员属性,且可被赋值
// val修饰: 可作为类的成员属性
class Person04(name: String, var age: Int, val no: Int) {

}
