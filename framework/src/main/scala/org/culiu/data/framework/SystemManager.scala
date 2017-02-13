package org.culiu.data.framework

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool
import com.typesafe.config.ConfigFactory
import akka.pattern.ask
import scala.concurrent.duration._


/**
  * Created by Admins on 025 2016/8/25.
  */

object SystemManager {

  val actorSystem = ActorSystem("RankServer", ConfigFactory.load.getConfig("RankServer"))
  val managerActor = actorSystem.actorOf(Props[SystemManager], name = "SystemManager")

  def run(): Unit = {
    val defaultSystem = "rank server"
    val defaultSystemPath = "F:\\Git_repository\\rank-server\\conf\\rank-server.toml"
    managerActor ! StartRankServer(defaultSystem, defaultSystemPath)
  }
}

class SystemManager extends Actor{

  var brokerPool : ActorRef = _

  def initOneSystem(system: String, path: String): Boolean = {
    try {
      // todo: init configManager classLoader handlerManager
      true
    } catch {
      case e: Exception => {
        println(e.printStackTrace.toString)
        false
      }
    }
  }

  def registerOneSystem(systemData: SystemData): Boolean = {
    val system = systemData.system
    val path = systemData.path
    if (initOneSystem(system, path)){
      // todo: add pool num to config
      brokerPool = context.actorOf(RoundRobinPool(10).props(Props(new Broker(systemData))), "BrokerPool")
      println(s"init system:${system}, path:${path} success!")
      true
    }
    else{
      println(s"init system:${system}, path:${path} fail!")
      false
    }
  }

  def receive = {
    case StartRankServer(sysName, confPath) => registerOneSystem(SystemData(sysName, confPath))
    case queryData: QueryData => brokerPool.ask(queryData)(1 seconds)
    case _ => println("SystemManager : msg not recongnized")
  }
}
