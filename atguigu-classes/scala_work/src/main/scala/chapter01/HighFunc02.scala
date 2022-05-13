package chapter01


import scala.collection.mutable.ListBuffer

object HighFunc02 {

  def main(args: Array[String]): Unit = {

    // 写一个方法，方法作用就是对两个数进行操作(任意操作，加减乘除等等)

    def operate_xy(a: Int, b: Int, f: (Int, Int) => Int): Iterator[Int] = {
      val i: Int = f(a, b)
      List(i).toIterator
    }

    //
    val a: Int = 1
    val b: Int = 2
    val ints: Iterator[Int] = operate_xy(a, b, (x: Int, y: Int) => {
      x + y
    })
    ints.foreach(println)

    val ints1: Iterator[Int] = operate_xy(a, b, _ - _)


    def mapTest(a: Any, f: Any => Any): Iterator[Any] = {
      val value: Any = f(a)
      val buffer: ListBuffer[Any] = new ListBuffer[Any]()
      buffer.append(value)
      buffer.toIterator
    }

    val ints2: List[Int] = List(1, 2, 3, 4)

    val iterator: Iterator[Any] = mapTest("xxx", _ + "ooooo")
    iterator.foreach(println)

    val iterator1: Iterator[Any] = mapTest("hello world", _.toString.split(" ").toList)
    iterator1.foreach(println)


    val f1: String => Int => Char => Boolean =
      s11 => s22 => s33 => !(s11 == "" && s22 == 0 && s33 == '0')


    def func(a: String)(b: Int)(c: Char): Boolean = {
      !(a == "" && b == 0 && c == '0')
    }

    // 在函数的嵌套调用中一定存在闭包
    val function: Int => (Char => Boolean) = func("aaaa")
    println(function)
    val function1: Char => Boolean = function(1111)
    println(function1)
    val bool: Boolean = function1('9')
    println(bool)

    def sumByX(a: Int): Int => Int = {
      def sumByY(b: Int) = {
        a + b
      }

      sumByY
    }

    val function2: Int => Int = sumByX(1)
    val i: Int = function2(2)
    println(i)

    // 高阶函数的第3种用法，函数作为函数的返回值，需结合函数的闭包来讲
    // sumByX(1)返回值是函数sumByY，在此期间sumByX的参数1，会被打包放进function2中，这时sumByX就会被释放
    // 再调用function2(2)最终才会打印结果

    // 什么是闭包？
    // 函数式编程的标配
    // 比如高阶函数的第三种用法,内层函数会使用到外层函数的值时
    // 按照java中函数的一直调用无法释放=>造成栈堆积
    // 函数式编程中会使用闭包
    // 将外部变量赋值给一个常量打包放入内部函数中，外部函数就会释放
    // scala会自动闭包

    // 柯里化？
    // 高阶函数的第3种用法，函数作为函数的返回值的简写
    // def (a:String)(b:Int)(c:Char) :Boolean = !(a == "" && b == 0 && c == '0')


    def MR(data: String)(map: String => Int)(reduce: Int => String): String = {
      println("=======经过map处理=======")
      val i1: Int = map(data)
      println("========经过shuffle阶段========")
      println("========经过reduce处理========")
      val str: String = reduce(i1)
      str
    }

    val data = "hello world"
    // 记录数据处理阶段
    val dataStage: (String => Int) => (Int => String) => String = MR(data)
    // 记录map阶段
    val mapStage: (Int => String) => String = dataStage(_.length)
    // 记录reduce阶段
    val res: String = mapStage("length: " + _)
    println(res)

    // 再接着mapStage上继续处理数据
    val str1: String = mapStage("数据的长度:" + _)


    def f(n: Int): Int = {
      if (n == 1)
        1
      else
        n * f(n - 1)
    }

    // n * f(n - 1) 没法闭包  闭包规则：内层函数调用外层函数变量 这里多余了一个n
    // 尾递归优化

    val sum = 1

    def f2(n: Int, sum: Int): Int = {
      if (n == 1)
        sum
      else {
        f2(n - 1, sum * n)
      }
    }

    println(f2(5, 1))

    val ints3: List[Int] = List(1, 2, 3)
    val ints4: List[Int] = ints3.map(_ * 2)
    println(ints4)

    def f3 = (name: String) => println(name)

    val function3 = (name: String) => println(name)


    def sayHi(age: Int): Unit = {
      println("sayHi的调用")
      println(s"hi $age")
    }

    var k = 1
    sayHi({
      k=k+1
      k
    })

    println("=======================")

    // 名调用    => String
    def sayHi1(name: => String): Unit = {
      println("sayHi1的调用")
      println(s"hi $name")
      println(s"hi $name")
    }

    var n = 1
    sayHi1({
      println("代码块-字符串1")
      n += 1
      "linhai" + n
    })

  }

}

