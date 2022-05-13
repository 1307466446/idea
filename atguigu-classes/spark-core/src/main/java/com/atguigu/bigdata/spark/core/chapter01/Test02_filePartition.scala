package com.atguigu.bigdata.spark.core.chapter01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 分完区后数据进哪个分区？
 */
object Test02_filePartition {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    // 从文件读取数据后创建RDD
    val value: RDD[String] = sc.textFile("input",3)

    // input的数据长度13Byte, 拿所有文件从长度/numsplits切片数 = 得到的goalSize
    // long globalSize = totalSize / (numSplits == 0 ? 1 : numSplits);  = 12 / 3 =4
    // 按globalSize去分
    // [0,4]  4是第二行了，一旦读到这一行就会读完这一行所以是 1 289
    // [4,8]
    // [8,12]
    // mr切割假设正好切到128M的时候，正好切到一行的一半，程序会继续切下去的，把这一行切完
    // 假设分区数20 10/20=0 0不行会按1来分,就有12个分区
    value.saveAsTextFile("output1")

    sc.stop()
  }

}
