package lius

import akka.actor.{Actor,ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import akka.dispatch.ExecutionContexts._

/**
  * Created by Administrator on 2016/8/9.
  */
object App{
  //  implicit val ec = global
  def main(args : Array[String]): Unit ={
    val system = ActorSystem("wordCount_system")
    val actor = system.actorOf(Props(new WordCounterActor("C:\\Users\\Administrator\\Desktop\\aaa.txt")))
    actor ! startProcessFileMsg()
    Thread.sleep(2000)
    system.shutdown

    //采用同步方式
    //    val future = actor ? startProcessFileMsg()
    //    future.map{ result => {
    //      println("totoal words in file" + args(0) + "is :" + result)
    //      system.shutdown}
    //    }
  }
}


class App extends Actor{
  def receive = {
    case  resultMsg(result) => {
      println(result)
    }
    case _ => {
      println("error:message not recognized!")
    }
  }
}
