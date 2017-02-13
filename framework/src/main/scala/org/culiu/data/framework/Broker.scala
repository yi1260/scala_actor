package org.culiu.data.framework

import akka.actor.{Actor, ActorLogging, PoisonPill, Props, Terminated}
import akka.routing.RoundRobinPool
import akka.pattern.ask
import scala.concurrent.duration._

/**
  * Created by Administrator on 2016/12/30.
  */
class Broker(systemData: SystemData) extends Actor with ActorLogging{

  val worker = context.watch(
    context.actorOf(RoundRobinPool(10).props(Props(new Worker(systemData))), "Worker")
  )

  def receive = {
    case str: String => println(str)
    case queryData: QueryData => println("receive queryData") ; sender ! "this is a msg from broker"
    case ShutDown => {
      worker ! PoisonPill
      context become shuttingDown
    }
  }

  def shuttingDown: Receive = {
    case queryData: QueryData => sender ! "worker is killed!"
    case Terminated(`worker`) =>
      context stop self
  }
}
