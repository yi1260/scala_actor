package lius

/**
  * Created by Administrator on 2016/8/9.
  */
sealed trait Message
case class startProcessFileMsg()
case class toProcessLineMsg(line:String)
case class lineProcessedMsg(wc:Int)
case class resultMsg(result:Int)
