package org.culiu.data.framework

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import akka.pattern.ask
import com.typesafe.config.ConfigFactory
import org.culiu.data.framework.protobuf.ProtoRemoteData

import scala.concurrent.duration._

/**
  * author http://lxw1234.com
  */

case class SendMsg2RemoteServer(bytes: Array[Byte])

class Client extends Actor {

  //远程Actor
  var remoteActor : ActorSelection = null
  import context.dispatcher

  override def preStart(): Unit = {
    remoteActor = context.system.actorSelection("akka.tcp://remote-server-sys@127.0.0.1:2555/user/remote-server")
    println("远程服务端地址 : " + remoteActor)
  }

  def receive  = {

    case SendMsg2RemoteServer(bytes) => {
      val res  = {
        remoteActor.ask(bytes)(1 seconds)
      }
      res.onSuccess{
        case res: String => println(res)
        case res: Array[Byte] => println(ProtoRemoteData.getResultData(res))
      }
      res.onFailure{
        case exp: Throwable => println(s"error: $exp")
      }
    }
    case _ => println("RemoteClient: msg not recognized! ")
  }
}

object Client {
  def main(args: Array[String]) : Unit = {

    val clientSystem = ActorSystem("remote-client", ConfigFactory.parseString("""
      akka {

              actor {
                provider = "akka.remote.RemoteActorRefProvider"
              }
    } """))


//    // Get the Serialization Extension
//    val serialization = SerializationExtension(clientSystem)
//
//    // Have something to serialize
//    val original = RemoteData("rank_session_id", "strategy", "common_fea_key", List())
//
//    // Find the Serializer for it
//    val serializer = serialization.findSerializerFor(original)
//
//    // Turn it into bytes
//    val bytes = serializer.toBinary(original)

    val client = clientSystem.actorOf(Props[Client])

    val bytes = ProtoRemoteData.createOriData("brandsort", "防滑鞋平底", List("100056481880", "100128062173"))

    import clientSystem.dispatcher
    clientSystem.scheduler.schedule(0 millis, 1000 millis, client, SendMsg2RemoteServer(bytes))

  }
}
