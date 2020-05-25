package mrks.collection

import scala.collection.generic.CanBuildFrom
import scala.concurrent.{ExecutionContext, Future}
import scala.language.higherKinds


trait CollectionExtensions {
  implicit class CollectionExtensions[A, M[X] <: Seq[X]](seq: M[A]) {
    def toMapBy[B](key: A => B): Map[B,A] = SeqUtils.toMap(seq, key)
    def toMapBy[B, C](key: A => B, value: A => C): Map[B, C] = SeqUtils.toMap(seq, key, value)

    def groupMap[B, C](key: A => B)(value: A => C): Map[B, Seq[C]] = {
      seq.groupBy(key).map { case (k, values) =>
        k -> values.map(value)
      }
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

object CollectionExtensions extends CollectionExtensions
