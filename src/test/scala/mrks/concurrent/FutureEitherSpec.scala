package mrks.concurrent

import mrks.BaseSpec

import scala.concurrent.Future

class FutureEitherSpec extends BaseSpec {
  "FutureEither" should {
    "handle for-yield sequence" in {
      val result: Future[Either[String,Int]] = for {
        r1 <- FutureEither(Future(Right(1): Either[String,Int]))
        r2 <- FutureEither(Right(r1 + 1))
        r3 <- FutureEither(Future(r2 + 1))
      } yield {
        r1 + r2 + r3
      }

      whenReady(result) {
        _ mustBe Right(6)
      }
    }
    "exit sequence on left result" in {
      val result: Future[Either[String,Int]] = for {
        _ <- FutureEither(Future(Right(1): Either[String,Int]))
        _ <- FutureEither(Left("error"): Either[String,Int])
        _ <- FutureEither(Future(Right(2)))
      } yield {
        3
      }

      whenReady(result) {
        _ mustBe Left("error")
      }
    }
    "exit sequence on failed result" in {
      val result: Future[Either[String,Int]] = for {
        _ <- FutureEither(Future(Right(1): Either[String,Int]))
        _ <- FutureEither(Future.failed[Either[String,Int]](new Exception("error")))
        _ <- FutureEither(Future(Right(2)))
      } yield {
        3
      }

      whenReady(result.failed) {
        _ mustBe an[Exception]
      }
    }
  }
}
