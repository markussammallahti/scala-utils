package mrks.concurrent

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions

case class FutureEither[A,B](future: Future[Either[A,B]]) {
  def flatMap[C](f: B => FutureEither[A,C])(implicit ec: ExecutionContext): FutureEither[A,C] = {
    FutureEither(future.flatMap {
      case Right(b) =>
        f(b).future

      case Left(a) =>
        Future.successful(Left(a))
    })
  }

  def map[C](f: B => C)(implicit ec: ExecutionContext): FutureEither[A,C] = {
    FutureEither(future.map(_.right.map(f)))
  }
}

object FutureEither {
  implicit def futureEitherToFuture[A,B](fe: FutureEither[A,B]): Future[Either[A,B]] = fe.future

  def apply[A,B](future: Future[B])(implicit ec: ExecutionContext): FutureEither[A,B] = FutureEither(future.map(Right(_)))
  def apply[A,B](either: Either[A,B]): FutureEither[A,B] = FutureEither(Future.successful(either))
}
