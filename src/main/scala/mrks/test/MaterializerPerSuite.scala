package mrks.test

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import org.scalatest.{BeforeAndAfterAll, Suite}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

trait MaterializerPerSuite extends BeforeAndAfterAll { this: Suite =>
  implicit protected val actorSystem: ActorSystem = ActorSystem(s"spec-actor-system${scala.util.Random.nextInt()}")
  implicit protected val materializer: Materializer = ActorMaterializer()(actorSystem)

  override protected def afterAll(): Unit = {
    super.afterAll()
    Await.ready(actorSystem.terminate(), Duration.Inf)
  }
}
