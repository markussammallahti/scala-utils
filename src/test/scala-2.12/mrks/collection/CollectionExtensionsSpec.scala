package mrks.collection

import mrks.BaseSpec

import scala.concurrent.Future


class CollectionExtensionsSpec extends BaseSpec {
  import CollectionExtensions._

  case class Data(key: String, value: Int)

  private val data1 = Data("A", 1)
  private val data2 = Data("B", 2)
  private val data3 = Data("B", 3)

  "toMapBy" should {
    "return map using function results as keys" in {
      val items = List(data1, data2)

      items.toMapBy(_.key) mustBe Map("A" -> data1, "B" -> data2)
      items.toMapBy(_.value) mustBe Map(1 -> data1, 2 -> data2)
    }
    "return map using function results as keys and values" in {
      val items = List(data1, data2)

      items.toMapBy(_.key, _.value + 1) mustBe Map("A" -> 2, "B" -> 3)
      items.toMapBy(_.value, _.key.head) mustBe Map(1 -> 'A', 2 -> 'B')
    }
  }

  "groupMap" should {
    "create and transform groups" in {
      val items = List(data1, data2, data3)

      items.groupMap(_.key)(_.value) mustBe Map("A" -> Seq(1), "B" -> Seq(2, 3))
    }
  }

  "filterWith" should {
    "return all elements that satisfy given async predicate" in {
      val numbers = Seq(1, 2, 3, 4)
      val words   = List("abc", "abcd", "abcde")

      def gt(other: Int)(v: Int) = Future { v > other }
      def length(l: Int)(v: String) = Future { v.length >= l }

      whenReady(numbers.filterWith(gt(0))) {
        _ must contain only (1, 2, 3, 4)
      }
      whenReady(numbers.filterWith(gt(2))) {
        _ must contain only (3, 4)
      }
      whenReady(numbers.filterWith(gt(4))) {
        _ mustBe Seq()
      }
      whenReady(words.filterWith(length(3))) {
        _ must contain only("abc", "abcd", "abcde")
      }
      whenReady(words.filterWith(length(4))) {
        _ must contain only("abcd", "abcde")
      }
      whenReady(words.filterWith(length(5))) {
        _ must contain only "abcde"
      }
    }
    "fail if any predicate fails" in {
      val numbers = Seq(2, 0, 2)

      whenReady(numbers.filterWith(n => Future { 10 % n == 5 }).failed) {
        _ mustBe an[ArithmeticException]
      }
    }
  }
}
