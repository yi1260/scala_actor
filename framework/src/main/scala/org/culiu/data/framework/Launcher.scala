package org.culiu.data.framework

import org.culiu.data.framework.server.AkkaHttpService

//import org.culiu.data.framework.log.SysLog

/**
  * Created by Admins on 025 2016/8/25.
  */

object Launcher extends App {
  println("launch rank server...")

//  SysLog.readLogPath())

  println("featureSource init begin")
  // todo: init feature source

  println("launch rank server")
  SystemManager.run()
  AkkaHttpService.start()

  // todo: remote start
}