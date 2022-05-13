package com.atguigu.bigdata.spark.core.chapter01

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 怎么分区？
 * 按多大去分区
 */
object Test01_filePartition {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local[*]")
    val sc: SparkContext = new SparkContext(conf)

    // 从文件读取数据后创建RDD
    val value: RDD[String] = sc.textFile("input/1.txt")

    // 为什么默认分区数是2，而我切出来3个文件  ???
    // 分区数并不是按我们设定的来的
    // 默认分区：math.min(defaultParallelism, 2)
    // 底层是用的hadoop的FileInputFormat的getSplits方法来切割的
    // totalSize= 文件长度的和(每一行都加上回车＋换行) => 13byte
    // goalSize = totalSize / (numSplits == 0 ? 1 : numSplits);
    // minSize = 1
    // 如果goalSize=0则会用minSize去切片
    // 核心部分，切分大小的判定                               默认128M
    // splitSize = Math.max(minSize, Math.min(goalSize, blockSize));  6
    // 最终分区个数: 拿splitSize 使用1.1倍原则去切分totalSize
    // 6,6,1
    value.saveAsTextFile("output")

    sc.stop()
  }

}
