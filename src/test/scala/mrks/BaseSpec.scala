package mrks

import org.scalatest.{MustMatchers, WordSpec}
import org.scalatest.concurrent.ScalaFutures

abstract class BaseSpec extends WordSpec
  with ScalaFutures
  with MustMatchers {

  implicit val ec = scala.concurrent.ExecutionContext.global
}
