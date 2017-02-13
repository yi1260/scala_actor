package org.culiu.data.framework.server

/**
  * Created by Administrator on 2017/1/4.
  */

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.{ActorMaterializer, Materializer}
import org.culiu.data.framework.{QueryData, SystemManager}
import spray.json.DefaultJsonProtocol
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContextExecutor, Future}
import akka.pattern.ask

case class FeaKeyList(cmnFeaKeyList: List[String], nonCmnFeaKeyList: List[String])

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val feaKeyList = jsonFormat2(FeaKeyList.apply)
}

trait WebService extends Directives with JsonSupport{

  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def processRecGetReq(queryData: QueryData): Future[Any] = {
    val future: Future[Any] = Future{
      println("begin send msg to managerActor")
      SystemManager.managerActor.ask(queryData)(1 seconds)
    }

    val recResultFuture = future.mapTo[Future[String]].flatMap { result => result }.mapTo[String]

    recResultFuture.flatMap { result =>
        Future(result)
    }
  }

  // todo: confirm request format
  val routes = pathPrefix("rank-server") {
    pathPrefix("test"){
      post{
        entity(as[FeaKeyList]){ feaKeyList =>
          println(feaKeyList.cmnFeaKeyList)
          println(feaKeyList.nonCmnFeaKeyList)
          complete(
            processRecGetReq(QueryData(feaKeyList.cmnFeaKeyList, feaKeyList.nonCmnFeaKeyList)).map[String] {
              case res: String => {
                res
              }
              case _ => {
                String.format("{\"code\":1, \"msg\":\"Internal exception!\"}")
              }
            }
          )
        }
      }
    }
  }
}

object AkkaHttpService extends WebService{

  override implicit val system = SystemManager.actorSystem
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  val akkaHttpServerBindIp = "127.0.0.1"
  val akkaHttpServerBindPort = 8989

  def start() = {
    println(s"Binding [${akkaHttpServerBindIp}:${akkaHttpServerBindPort}]")
    Http().bindAndHandle(routes, akkaHttpServerBindIp, akkaHttpServerBindPort)
    println(s"Binding [${akkaHttpServerBindIp}:${akkaHttpServerBindPort}] completely.")
  }
}
