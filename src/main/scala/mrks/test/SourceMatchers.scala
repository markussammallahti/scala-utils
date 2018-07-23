package mrks.test

import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.matchers.{BeMatcher, MatchResult}

import scala.concurrent.Await

trait SourceMatchers { this: PatienceConfiguration =>
  def sourceOf[A,B](head: A, tail: A*)(implicit config: PatienceConfig, mat: Materializer): BeMatcher[Source[B,_]] = (left: Source[B,_]) => {
    val actual = sourceToSeq(left)(config, mat)
    val expected = head +: tail.toSeq

    MatchResult(
      actual == expected,
      if (actual.isEmpty) s"empty source did not have elements ${expected.mkString(", ")}" else s"source of ${actual.mkString(", ")} did not have elements ${expected.mkString(", ")}",
      s"source did have elements ${expected.mkString(", ")}"
    )
  }

  def emptySource(implicit config: PatienceConfig, mat: Materializer): BeMatcher[Source[Any,_]] = (left: Source[Any, _]) => {
    val actual = sourceToSeq(left)(config, mat)

    MatchResult(
      actual.isEmpty,
      s"source of ${actual.mkString(", ")} was not empty",
      "source was empty"
    )
  }

  private def sourceToSeq[A](source: Source[A, _])(implicit config: PatienceConfig, mat: Materializer) = {
    Await.result(source.runWith(Sink.seq), config.timeout)
  }
}
