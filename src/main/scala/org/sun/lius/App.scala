package org.sun.lius

import akka.actor.{ActorSystem, Props}
import lius.WordCounterActor

import scala.concurrent.duration._

/**
  * Created by Administrator on 2016/8/9.
  */
object App{
  //  implicit val ec = global
  def main(args : Array[String]): Unit ={
    val system = ActorSystem("wordCount_system")
    val actor = system.actorOf(Props(new WordCounterActor("C:\\Users\\Administrator\\Desktop\\aaa.txt")))

    import system.dispatcher
    val cancellable = system.scheduler.schedule(0 seconds, 1 seconds, actor, startProcessFileMsg())
//    actor ! startProcessFileMsg()
    Thread.sleep(1000)
    system.shutdown

    //采用同步方式
    //    val future = actor ? startProcessFileMsg()
    //    future.map{ result => {
    //      println("totoal words in file" + args(0) + "is :" + result)
    //      system.shutdown}
    //    }
  }
}

