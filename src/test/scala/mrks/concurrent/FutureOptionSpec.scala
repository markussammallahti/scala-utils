package mrks.concurrent

import mrks.BaseSpec

import scala.concurrent.Future

class FutureOptionSpec extends BaseSpec {
  "FutureOption" should {
    "handle for-yield sequence" in {
      val result: Future[Option[Int]] = for {
        r1 <- FutureOption(Future(Some(1)))
        r2 <- FutureOption(Some(r1 + 1))
        r3 <- FutureOption(Future(r2 + 1))
      } yield {
        r1 + r2 + r3
      }

      whenReady(result) {
        _ mustBe Some(6)
      }
    }
    "support filtering" in {
      val result1: Future[Option[Int]] = for {
        r1 <- FutureOption(Future(Some(1)))
        r2 <- FutureOption(Some(r1 + 1)) if r2 > 3
        r3 <- FutureOption(Future(r2 + 1))
      } yield {
        r1 + r2 + r3
      }

      whenReady(result1) {
        _ mustBe None
      }

      val result2: Future[Option[Int]] = for {
        r1 <- FutureOption(Future(Some(1)))
        r2 <- FutureOption(Some(r1 + 1)) if r2 < 3
        r3 <- FutureOption(Future(r2 + 1))
      } yield {
        r1 + r2 + r3
      }

      whenReady(result2) {
        _ mustBe Some(6)
      }
    }
    "exit sequence on none result" in {
      val result: Future[Option[String]] = for {
        _ <- FutureOption(Future.successful(Some(1)))
        _ <- FutureOption(Future.successful(None: Option[Int]))
        _ <- FutureOption(Future.successful(Some(2)))
      } yield {
        "success"
      }

      whenReady(result) {
        _ mustBe None
      }
    }
    "exit sequence on failed result" in {
      val result: Future[Option[String]] = for {
        _ <- FutureOption(Future(Some(1)))
        _ <- FutureOption(Future.failed[Int](new Exception("error")))
        _ <- FutureOption(Future(Some(2)))
      } yield {
        "success"
      }

      whenReady(result.failed) {
        _ mustBe an[Exception]
      }
    }
  }
}
