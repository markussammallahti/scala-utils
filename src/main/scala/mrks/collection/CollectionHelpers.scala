package mrks.collection

import scala.collection.generic.CanBuildFrom
import scala.concurrent.{ExecutionContext, Future}
import scala.language.higherKinds

trait CollectionHelpers {
  implicit class RichSeq[A, M[X] <: Seq[X]](seq: M[A]) {
    def toMapBy[C](key: A => C): Map[C,A] = {
      seq.map(item => key(item) -> item).toMap
    }

    def toMapBy[C,D](key: A => C, value: A => D): Map[C,D] = {
      seq.map(item => key(item) -> value(item)).toMap
    }

    def filterWith(p: A => Future[Boolean])(implicit cbf: CanBuildFrom[M[A], A, M[A]], ec: ExecutionContext): Future[M[A]] = {
      Future.sequence(seq.map(item => p(item).map(result => item -> result))).map { results =>
        results.foldLeft(cbf(seq)) { case (builder, (item, result)) =>
          if (result) builder += item
          builder
        }
      }.map(_.result())
    }
  }
}

object CollectionHelpers extends CollectionHelpers
