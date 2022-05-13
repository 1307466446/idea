package sparkOnHive

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSQL12_Hive {
  def main(args: Array[String]): Unit = {

    System.setProperty("HADOOP_USER_NAME","atguigu")
    //1.创建SparkConf并设置App名称
    val conf: SparkConf = new SparkConf().setAppName("SparkSQLTest").setMaster("local[*]")

    //2.创建SparkSession，该对象是提交Spark SQL的入口
    val spark: SparkSession = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()

    spark.sql("show tables").show()


    //4.关闭连接
    spark.stop()
  }

}
