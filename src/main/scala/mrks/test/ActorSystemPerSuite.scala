package mrks.test

import akka.actor.ActorSystem
import org.scalatest.{BeforeAndAfterAll, Suite}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

trait ActorSystemPerSuite extends BeforeAndAfterAll { this: Suite =>
  implicit val actorSystem: ActorSystem = ActorSystem(s"spec-actor-system${scala.util.Random.nextInt}")

  override protected def afterAll(): Unit = {
    super.afterAll()
    Await.ready(actorSystem.terminate(), Duration.Inf)
  }
}
