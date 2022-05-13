import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming02_RDDStream {

  def main(args: Array[String]): Unit = {
    //1.初始化Spark配置信息
    val sparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[*]")

    //2.初始化SparkStreamingContext
    val ssc = new StreamingContext(sparkConf, Seconds(1))

    val lineDStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop102", 9999)
    val wordDStream: DStream[String] = lineDStream.flatMap(_.split(" "))
    val wordToOne: DStream[(String, Int)] = wordDStream.map((_, 1))

    val windowDStream: DStream[(String, Int)] = wordToOne.window(Seconds(4), Seconds(3))

    val value: DStream[(String, Int)] = windowDStream.reduceByKey(_ + _)
    value.print()

    //4 启动SparkStreamingContext
    ssc.start()
    // 将主线程阻塞，主线程不退出
    ssc.awaitTermination()
  }

}
