package org.sun.lius

import akka.actor.Actor
/**
  * Created by Administrator on 2016/8/9.
  */
//最小的actor，负责统计一行字符串中的单词数量
class StringCounterActor extends Actor{
  def countWordsInLine(wordsInLine : Int) = {
    wordsInLine match{
      case 1 => {
        println("only one word in line!")
        lineProcessedMsg(wordsInLine)
      }
      case _ => {
        println("multi words in line!")
        lineProcessedMsg(wordsInLine)
      }
    }
  }
  def receive = {
    case toProcessLineMsg(line) => {
      println("StringCounterActor:received toProcessLineMsg")
      val wordsInLine = line.split(" ").length
      sender ! countWordsInLine(wordsInLine)
    }
    case _ => println("error:message not recognized!")
  }
}
