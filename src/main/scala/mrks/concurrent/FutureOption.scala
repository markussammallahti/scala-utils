package mrks.concurrent

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions

case class FutureOption[A](future: Future[Option[A]]) {
  def flatMap[B](f: A => FutureOption[B])(implicit ec: ExecutionContext): FutureOption[B] = {
    FutureOption(future.flatMap {
      case Some(a) =>
        f(a).future

      case None =>
        Future.successful(None)
    })
  }

  def map[B](f: A => B)(implicit ec: ExecutionContext): FutureOption[B] = {
    FutureOption(future.map(_.map(f)))
  }

  def withFilter(p: A => Boolean)(implicit ec: ExecutionContext): FutureOption[A] = {
    FutureOption(future.map(_.filter(p)))
  }
}

object FutureOption {
  implicit def futureOptionToFuture[A](fo : FutureOption[A]) : Future[Option[A]] = fo.future

  def apply[A](future: Future[A])(implicit ec: ExecutionContext): FutureOption[A] = FutureOption(future.map(Option(_)))
  def apply[A](option: Option[A]): FutureOption[A] = FutureOption(Future.successful(option))
}
