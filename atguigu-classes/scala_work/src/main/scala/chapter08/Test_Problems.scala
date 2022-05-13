package chapter08

import scala.collection.mutable

object Test_Problems {

  def main(args: Array[String]): Unit = {
    val map: Map[String, Int] = Map(("hello", 10), ("spark", 20))
    val map1: Map[String, Int] = Map(("hello", 10), ("scala", 20))
    // 对两个map进行归约
    val map2: Map[String, Int] = map ++ map1.map(tuple => (tuple._1, tuple._2 + map.getOrElse(tuple._1, 0)))
    println(map2)

    //
    val map3: mutable.HashMap[String, Int] = new mutable.HashMap[String, Int]()
    val map4: mutable.Map[String, Int] = map3 ++ map ++ map1
    for (elem <- map; elem1 <- map1) {
      if (elem._1 == elem1._1)
        map4.put(elem._1, elem1._2 + elem._2)
    }
    println(map4)

    val map5: mutable.Map[String, Int] = mutable.Map(("hello", 10), ("spark", 20))
    val map6: Map[String, Int] = Map(("hello", 10), ("scala", 20))
    for (elem <- map6)
      map5.put(elem._1, elem._2 + map5.getOrElse(elem._1, 0))
    println(map5)


  }

}
