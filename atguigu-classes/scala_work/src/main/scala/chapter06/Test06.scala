package chapter06


/**
 * 辅助构造器中首行一定要调用主构造器
 * 任何情况下主构造器都一定先执行
 */
object Test06 {
  def main(args: Array[String]): Unit = {
    val p1: Person06 = new Person06()
  }
}

// 主构造器直接在类的后面使用括号表示
class Person06(name: String) {
  // 整个类的大括号都是主构造器的代码
  println("我是主构造器")
  println(name)

  def this() {
    this("lisi")
    println("直接调用主构造器的辅助构造器1")
  }

  // 辅助函数的调用是讲究顺序的,如果要调用this()则this()要写在this(age:Int)前面
  def this(age: Int) {
    this()
    println("间接调用主构造器的辅助构造器2")
  }

}
