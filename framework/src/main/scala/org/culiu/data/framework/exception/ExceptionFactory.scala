package org.culiu.data.framework.exception

/**
  * Created by lijh on 2016/12/28.
  */

case class TomlParseFailException(msg: String) extends RuntimeException(msg)

case class KeyNotFoundException(msg: String) extends RuntimeException(msg)

case class TableNotFoundException(msg: String) extends RuntimeException(msg)

case class ValueNotContainedException(msg: String) extends RuntimeException(msg)

case class SystemNotFoundException(msg: String) extends RuntimeException(msg)

case class SystemAlreadyRegisterException(msg: String) extends RuntimeException(msg)

case class SystemRegisterFailException(msg: String) extends RuntimeException(msg)

case class HandlerNotFoundException(msg: String) extends RuntimeException(msg)

case class InputKeyNotFoundException(msg: String) extends RuntimeException(msg)



object ExceptionFactory {

  def tomlParseFail(detail: String): RuntimeException =
    TomlParseFailException(s"toml parse failed : ${detail}")

  def keyNotFound(table: String, key: String): RuntimeException =
    KeyNotFoundException(s"${table} : Key ${key} is not found")

  def tableNotFound(table: String): RuntimeException =
    TableNotFoundException(s"${table} is not found")

  def valueNotContained(table: String, containMap: String, containedValue: String): RuntimeException =
    ValueNotContainedException(s"${table} : ${containedValue} is not found in ${containMap}")

  def systemNotFound(system: String, module: String): RuntimeException =
    SystemNotFoundException(s"system ${system} is not found, ${module} failed")

  def systemAlreadyRegister(system: String): RuntimeException =
    SystemAlreadyRegisterException(s"system ${system} is already existed, register failed")

  def systemRegisterFail(system: String, errorMsg: String): RuntimeException =
    SystemRegisterFailException(s"""register system ${system} failed, error "${errorMsg}" """)

  def handlerNotFound(system: String, bucketConfId: String, handlerName: String): RuntimeException =
    HandlerNotFoundException(s"handler ${handlerName} is not found in bucket ${bucketConfId} by system ${system}")

  def inputKeyNotFound(module: String, errorMsg: String): RuntimeException =
    InputKeyNotFoundException(s"""${module} failed, error "${errorMsg}" """)

}
