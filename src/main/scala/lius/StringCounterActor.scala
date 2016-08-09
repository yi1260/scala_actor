package lius
import akka.actor.{Actor}
/**
  * Created by Administrator on 2016/8/9.
  */
//最小的actor，负责统计一行字符串中的单词数量
class StringCounterActor extends Actor{
  def receive = {
    case toProcessLineMsg(line) => {
      println("StringCounterActor:received toProcessLineMsg")
      val wordsInLine = line.split(" ").length
      sender ! lineProcessedMsg(wordsInLine)
    }
    case _ => println("error:message not recognized!")
  }
}
