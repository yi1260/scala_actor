package org.culiu.data.framework.protobuf

import scala.collection.JavaConverters._

/**
  * Created by Administrator on 2017/1/12.
  */

case class OriRemoteData(strategy: String, common_fea_key: String, non_com_fea_key_list: List[String])
case class ResultRemotaData(comFeaKey: String, nonComFeaKey2ScoreMap: Map[String, Double])

object ProtoRemoteData{

  // create Array[Byte]
  def createOriData(strategy: String, common_fea_key: String, non_com_fea_key_list: List[String]) : Array[Byte] = {
    val oriData = RemoteData.OriData.newBuilder()
    oriData.setStrategy(strategy)
    oriData.setCommonFeaKey(common_fea_key)
    non_com_fea_key_list.foreach{
      non_com_fea_key => oriData.addNonComFeaKeyList(non_com_fea_key)
    }

    oriData.build().toByteArray
  }

  def createResultData(comFeaKey: String, nonComFeaKey2ScoreMap: Map[String, Double]) : Array[Byte] = {
    val resultData = RemoteData.ResultData.newBuilder()
    resultData.setComFeaKey(comFeaKey)
    nonComFeaKey2ScoreMap.foreach{ case (key, score) =>
        resultData.addResult(createKV(key, score))
    }

    resultData.build().toByteArray
  }

  def createKV(common_fea_key: String, score: Double) : RemoteData.NonComFeaKey2Score.Builder = {
    val kv = RemoteData.NonComFeaKey2Score.newBuilder()
    kv.setKey(common_fea_key)
    kv.setScore(score)

    kv
  }

  // parse from Array[Byte]
  def getOriData(bytes: Array[Byte]) : OriRemoteData = {
    val res = RemoteData.OriData.parseFrom(bytes)
    val commonFeaKey = res.getCommonFeaKey
    val nonComonFeaKeylist = res.getNonComFeaKeyListList.toArray.toList.map(_.asInstanceOf[String])
    val strategy = res.getStrategy
    OriRemoteData(strategy, commonFeaKey, nonComonFeaKeylist)
  }

  // parse from Array[Byte]
  def getResultData(bytes: Array[Byte]) : ResultRemotaData = {
    val res = RemoteData.ResultData.parseFrom(bytes)
    val comFeaKey = res.getComFeaKey
    val nonComFeaKey2Score = res.getResultList.asScala.map{ kv =>
      (kv.getKey, kv.getScore)
    }.toMap
    ResultRemotaData(comFeaKey, nonComFeaKey2Score)
  }

}
