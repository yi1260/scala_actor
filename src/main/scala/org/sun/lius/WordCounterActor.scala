package org.sun.lius

import akka.actor.{Actor, ActorRef, Props}

import scala.io.Source._

/**
  * Created by Administrator on 2016/8/9.
  */
class WordCounterActor(file:String) extends Actor{
  private var totalLines = 0
  private var fileSender : Option[ActorRef] = None
  private var result : Int = 0
  private var linesProcessed : Int = 0

  def receive = {
    case startProcessFileMsg() => {
      println("WordCounterActor:received startProcessFileMsg")
      fileSender = Some(sender)
      fromFile(file).getLines.foreach{
        line => context.actorOf(Props[StringCounterActor]) ! toProcessLineMsg(line)
        totalLines += 1
      }
    }
    case lineProcessedMsg(wordsInLine) => {
      println("WordCounterActor:received lineProcessedMsg")
      result += wordsInLine
      linesProcessed += 1
      if(linesProcessed == totalLines){
        fileSender.map(_ ! result)
        println(result)
      }
    }
    case _ => println("error:message not recognized!")
  }
}
