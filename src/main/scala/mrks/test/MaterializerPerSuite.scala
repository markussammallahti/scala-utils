package mrks.test

import akka.stream.{ActorMaterializer, Materializer}
import org.scalatest.Suite

trait MaterializerPerSuite extends ActorSystemPerSuite { this: Suite =>
  implicit val materializer: Materializer = ActorMaterializer()(actorSystem)
}
