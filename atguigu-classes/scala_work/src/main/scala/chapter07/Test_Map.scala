package chapter07

import scala.collection.mutable

object Test_Map {
  def main(args: Array[String]): Unit = {
    var map: Map[Char, Int] = Map(('a', 1), ('b', 1))
    val map1: Map[Char, Int] = map.updated('c', 1)

    map=map1

    for (elem <- map) {
      val key: Char = elem._1
      val value: Int = elem._2
      println(s"$key->$value")
    }

    val keys: Iterable[Char] = map.keys
    val values: Iterable[Int] = map.values
    for (elem <- keys) {
      println(s"$elem->${map(elem)}")
    }

    // Option下有两个对象Some，None
    // map.get()返回的是Option对象
    val maybeInt: Option[Int] = map.get('a')
    println(maybeInt) // Some(1)
    val maybeInt1: Option[Int] = map.get('c')
    println(maybeInt1) // None

    val value: Int = maybeInt.get
    println(value)
    // 对None用get报错
    //    val value1: Int = maybeInt1.get
    //    println(value1)

    // getOrElse是经过优化的get方法
    val i: Int = map.getOrElse('a', 0)
    println(i)
    val i1: Int = map.getOrElse('c', 0)
    println(i1)


    // 可变Map

    val map2: mutable.HashMap[String, Int] = new mutable.HashMap[String, Int]()
    // put和update的作用一样
    // put底层就是update
    map2.put("a", 1)
    map2.put("b", 1)
    map2.put("c", 1)

    map2.update("d", 1) // 没有就添加
    println(map2)

    val maybeInt2: Option[Int] = map2.remove("a")
    if (maybeInt2.isDefined) {
      println(maybeInt2.get)
    } else {
      println(map2)
    }

  }

}
