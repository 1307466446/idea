object Test_wc {

  def main(args: Array[String]): Unit = {
    val list: List[String] = List("hello atguigu", "hello world")

    list.flatMap(_.split(" ")).groupBy(s => s).mapValues(_.length).toList.sortWith(_._2>_._2).take(3).foreach(println)
  }

}
