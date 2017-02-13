package org.culiu.data.framework

/**
  * Created by Admins on 025 2016/8/25.
  */
sealed trait SysMsg extends Serializable

case class StartRankServer(sysName: String, confPath: String) extends SysMsg

case class StopActor(name: String) extends SysMsg

case class ShutDown() extends SysMsg



case class SystemData(system: String, path: String) extends SysMsg // 同时只能存在一个rec？

case class RankResult(status: String, jsonStr: String) extends SysMsg

// todo: change
case class QueryData(cmnFeaKeyList: List[String], nonCmnFeaKeyList: List[String])

