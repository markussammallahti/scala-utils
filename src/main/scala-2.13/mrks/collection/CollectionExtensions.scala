package mrks.collection

import scala.collection.BuildFrom
import scala.concurrent.{ExecutionContext, Future}


trait CollectionExtensions {
  implicit class CollectionExtensions[A, M[X] <: Seq[X]](seq: M[A]) {
    def toMapBy[B](key: A => B): Map[B,A] = SeqUtils.toMap(seq, key)
    def toMapBy[B, C](key: A => B, value: A => C): Map[B, C] = SeqUtils.toMap(seq, key, value)

    def filterWith(p: A => Future[Boolean])(implicit bf: BuildFrom[M[A], A, M[A]], ec: ExecutionContext): Future[M[A]] = {
      Future.sequence(seq.map(item => p(item).map(result => item -> result))).map { results =>
        results.foldLeft(bf.newBuilder(seq)) { case (builder, (item, result)) =>
          if (result) builder += item
          builder
        }
      }.map(_.result())
    }
  }
}

object CollectionExtensions extends CollectionExtensions
