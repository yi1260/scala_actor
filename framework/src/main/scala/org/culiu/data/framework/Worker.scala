package org.culiu.data.framework

import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.RoundRobinPool

/**
  * Created by Administrator on 2016/12/30.
  */
class Worker(systemData: SystemData) extends Actor with ActorLogging{

  val worker = context.watch(
    context.actorOf(RoundRobinPool(10).props(Props(new Worker(systemData))), "Worker")
  )
  def receive = {
    case _ => println("aaaaaaaaaaa")
  }
}