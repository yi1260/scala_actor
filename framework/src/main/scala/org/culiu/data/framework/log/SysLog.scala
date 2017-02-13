//package org.culiu.data.framework.log
//
//import ch.qos.logback.classic.LoggerContext
//import ch.qos.logback.classic.joran.JoranConfigurator
//import ch.qos.logback.core.joran.spi.JoranException
//import ch.qos.logback.core.util.StatusPrinter
//import org.culiu.data.common.SysConfManager
//import org.slf4j.LoggerFactory
//
///**
//  * Created by Admins on 008 2016/9/8.
//  */
//object SysLog {
//
//  def readLogPath(): Unit = {
//    val lc: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
//    val configurator: JoranConfigurator = new JoranConfigurator
//    configurator.setContext(lc)
//    lc.reset
//    val path = SysConfManager.programSettings.frameworkLogPath
//    try {
//      configurator.doConfigure(path)
//    }
//    catch {
//      case e: JoranException => {
//        e.printStackTrace
//      }
//    }
//    StatusPrinter.printInCaseOfErrorsOrWarnings(lc)
//  }
//}
