package mrks.time

import java.time.Instant

import mrks.BaseSpec
import org.scalatest.Inspectors


class RichInstantSpec extends BaseSpec with Inspectors {
  import TimeHelpers.RichInstant

  private val testTime = Instant.now

  "toStartOfDay" should {
    "return Instant with time set to midnight" in {
      forAll(Seq(
        "2020-02-28T00:00:00.001Z" -> "2020-02-28T00:00:00Z",
        "2020-04-25T12:34:56.789Z" -> "2020-04-25T00:00:00Z"
      )) { case (input, expected) =>
        Instant.parse(input).toStartOfDay mustBe Instant.parse(expected)
      }
    }
  }

  "toEndOfDay" should {
    "return Instant with time set to last nanosecond of the day" in {
      forAll(Seq(
        "2020-01-01T00:00:00Z"      -> "2020-01-01T23:59:59.999999999Z",
        "2020-04-25T12:34:56.789Z"  -> "2020-04-25T23:59:59.999999999Z"
      )) { case (input, expected) =>
        Instant.parse(input).toEndOfDay mustBe Instant.parse(expected)
      }
    }
  }

  "toStartOfMonth" should {
    "return Instant with first day of the month at midnight" in {
      forAll(Seq(
        "2020-01-02T00:10:00Z"      -> "2020-01-01T00:00:00Z",
        "2020-04-25T12:34:56.789Z"  -> "2020-04-01T00:00:00Z"
      )) { case (input, expected) =>
        Instant.parse(input).toStartOfMonth mustBe Instant.parse(expected)
      }
    }
  }

  "toEndOfMonth" should {
    "return Instant with " in {
      forAll(Seq(
        "2019-02-01T00:00:00Z"      -> "2019-02-28T23:59:59.999999999Z",
        "2020-02-01T00:00:00Z"      -> "2020-02-29T23:59:59.999999999Z",
        "2020-04-25T12:34:56.789Z"  -> "2020-04-30T23:59:59.999999999Z",
        "2020-05-30T12:34:56.789Z"  -> "2020-05-31T23:59:59.999999999Z"
      )) { case (input, expected) =>
        Instant.parse(input).toEndOfMonth mustBe Instant.parse(expected)
      }
    }
  }

  "<=" should {
    "return true" when {
      "date is before" in {
        testTime <= testTime.plusNanos(1)   mustBe true
        testTime <= testTime.plusMillis(1)  mustBe true
        testTime <= testTime.plusSeconds(1) mustBe true
      }
      "date is equal" in {
        Instant.ofEpochMilli(123456L) <= Instant.ofEpochMilli(123456L) mustBe true
      }
    }
    "return false" when {
      "date is after" in {
        testTime <= testTime.minusNanos(1)    mustBe false
        testTime <= testTime.minusMillis(1)   mustBe false
        testTime <= testTime.minusSeconds(1)  mustBe false
      }
    }
  }

  ">=" should {
    "return true" when {
      "date is after" in {
        testTime >= testTime.minusNanos(1)    mustBe true
        testTime >= testTime.minusMillis(1)   mustBe true
        testTime >= testTime.minusSeconds(1)  mustBe true
      }
      "date is equal" in {
        Instant.ofEpochMilli(123456L) <= Instant.ofEpochMilli(123456L) mustBe true
      }
    }
    "return false" when {
      "date is before" in {
        testTime >= testTime.plusNanos(1)   mustBe false
        testTime >= testTime.plusMillis(1)  mustBe false
        testTime >= testTime.plusSeconds(1) mustBe false
      }
    }
  }
}
