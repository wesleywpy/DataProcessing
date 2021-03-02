package com.wesley.growth

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
  * 使用Scala开发Flink的实时处理应用程序
  */
object StreamingWCScalaApp {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment


    StreamExecutionEnvironment.createLocalEnvironment()

    // 引入隐式转换
    import org.apache.flink.api.scala._

    val text = env.socketTextStream("localhost",9999)


    text.flatMap(_.split(","))
      // 转换为 WC(word,count)
      .map(x => WC(x.toLowerCase, 1))
      // _.word 中 下划线表示第一个参数
      .keyBy(_.word)
      .countWindow(1)
      .sum("count")
      .print()
      .setParallelism(1)

    env.execute("StreamingWCScalaApp")
  }

  case class WC(word: String, count: Int)

}
