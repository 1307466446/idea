package udf

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Encoder, Encoders, SparkSession, functions}
import org.apache.spark.sql.expressions.Aggregator

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object SparkSQL07_UDAF {


  def main(args: Array[String]): Unit = {
    //1.创建SparkConf并设置App名称
    System.setProperty("HADOOP_USER_NAME", "atguigu")
    val conf: SparkConf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")

    //2.创建SparkSession，该对象是提交Spark SQL的入口
    val spark: SparkSession = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()

    spark.udf.register("city_remark", functions.udaf(CityRemarkUDAF()))

    // 1. 查询出所有的点击记录，并和城市表产品表做内连接
    spark.sql(
      """
        | select
        |    c.area, --地区
        |    c.city_name, -- 城市
        |    p.product_name, -- 商品名称
        |    v.click_product_id -- 点击商品id
        | from (select * from user_visit_action where click_product_id > -1) v
        | join city_info c
        | on v.city_id = c.city_id
        | join product_info p
        | on v.click_product_id = p.product_id
        |""".stripMargin).createOrReplaceTempView("t1")

    // 2. 分组计算每个区域，每个产品的点击量
    spark.sql(
      """
        |select
        |    t1.area, --地区
        |    t1.product_name, -- 商品名称
        |    count(*) click_count, -- 商品点击次数
        |    city_remark(t1.city_name) --城市备注
        |from t1
        |group by t1.area, t1.product_name
            """.stripMargin).createOrReplaceTempView("t2")

    // 3. 对每个区域内产品的点击量进行倒序排列
    spark.sql(
      """
        |select
        |    *,
        |    rank() over(partition by t2.area order by t2.click_count desc) rank -- 每个区域内按照点击量，倒序排行
        |from t2
            """.stripMargin).createOrReplaceTempView("t3")

    // 4. 每个区域取top3
    spark.sql(
      """
        |select
        |    *
        |from t3
        |where rank <= 3
            """.stripMargin).show(false)

    //4.关闭连接
    spark.stop()

  }

}


/**
 *
 * @param totalCount 区域内某商品的点击总次数
 * @param cityMap    (city,点击次数)
 */
case class Buff(var totalCount: Long, cityMap: mutable.Map[String, Long])

case class CityRemarkUDAF() extends Aggregator[String, Buff, String] {

  override def zero: Buff = Buff(0L, mutable.Map())

  override def reduce(b: Buff, a: String): Buff = {
    b.totalCount += 1L
    val l: Long = b.cityMap.getOrElse(a, 0)
    b.cityMap.put(a, l + 1L)
    b
  }

  override def merge(b1: Buff, b2: Buff): Buff = {
    b1.totalCount += b2.totalCount
    for (elem <- b2.cityMap) {
      val l: Long = b1.cityMap.getOrElse(elem._1, 0)
      b1.cityMap.put(elem._1, l + elem._2)
    }
    b1
  }

  override def finish(reduction: Buff): String = {

    val remarkList = ListBuffer[String]()
    var sum: Double = 0
    // 前面的数据(city,点击次数)
    val data: List[(String, Long)] = reduction.cityMap.toList.sortBy(_._2)(Ordering[Long].reverse).take(2)
    data.foreach({
      case (city, count) =>
        val d = (count * 100).toDouble / reduction.totalCount
        remarkList.append(city + " " + d.formatted("%.1f") + "%")
        sum += d
    })
    if (reduction.cityMap.size > 2) {
      remarkList.append("其他 " + (100 - sum).formatted("%.1f") + "%")
    }
    remarkList.mkString(", ")
  }

  override def bufferEncoder: Encoder[Buff] = Encoders.kryo

  override def outputEncoder: Encoder[String] = Encoders.STRING

}
