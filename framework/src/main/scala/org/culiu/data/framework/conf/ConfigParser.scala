package org.culiu.data.framework.conf

import java.io.File

import com.moandjiezana.toml.Toml

/**
  * Created by lijh on 2016/12/28.
  */

case class Config(strategyMap:Map[String,Strategy] , layerMap: Map[String, Layer], handlerMap: Map[String, Handler])


case class Handler(name: String, version:String ,jar: String, className: String)

case class LayerConf(id: String, params:Map[String,Any])

case class Layer(name:String, conf_id:String, common_handlers:String, non_common_handlers:String, merge_handlers:String)

case class StrategyLayer(ref:String)

case class Strategy(id: String, strategyLayerList:List[StrategyLayer])


object ConfigParser {

  lazy val handerTable = "handler"
  lazy val strategyTable = "strategy"
  lazy val layerTable = "layer"
  lazy val layerConfTable = "layer.conf"


//  def parse(file: File): Either[RuntimeException, Config] = {
//    try {
//      val toml = new Toml().read(file)
//
//      val res = for {
//        handler <- parseHandler(toml).right
//        strategy <- parseStrategy(toml, handler).right
//        bucketConf <- parseBucketConf(toml, strategy).right
//        globalConf <- parseGlobalConf(toml, handler).right
//        layer <- parseLayer(toml, bucketConf).right
//        scene <- parseScene(toml, layer).right
//      } yield Config(scene, layer, globalConf, bucketConf, strategy, handler)
//      res.fold(l => Left(l), r => Right(r))
//    } catch {
//      case e: RuntimeException => Left(e)
//    }
//  }



}
