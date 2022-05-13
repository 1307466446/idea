package chapter09

object Test_match {
  def main(args: Array[String]): Unit = {
    val a = 10
    val b = 20
    val c: Char = '+'

    c match {
      case '+' =>
        println("+")
        println(a + b)
      case '-' => println(a - b)
      case '*' => println(a * b)
      case '/' => println(a / b)
      case _ => println("无匹配")
    }


    val person: Person = Person("qq", 11)
    person match {
      case Person("qq", 11) => println("success")
      case _ => println("failed")
    }


  }

}

// 样例类的属性默认是val修饰
// 样例类中含有apply，unapply，toString，equals，hashCode，copy 方法
case class Person(name: String, age: Int)

//class Person(val name: String, val age: Int) {}
//
//object Person {
//  def apply(name: String, age: Int): Person = new Person(name, age)
//
//  def unapply(arg: Person): Option[(String, Int)] = {
//    if (arg == null) None
//    else Some(arg.name, arg.age)
//  }
//}
