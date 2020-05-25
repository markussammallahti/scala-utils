package mrks.collection

private object SeqUtils {
  def toMap[A, B](seq: Seq[A], key: A => B): Map[B, A] = {
    seq.map(item => key(item) -> item).toMap
  }

  def toMap[A, B, C](seq: Seq[A], key: A => B, value: A => C): Map[B, C] = {
    seq.map(item => key(item) -> value(item)).toMap
  }
}
