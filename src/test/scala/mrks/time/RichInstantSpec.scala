package mrks.time

import java.time.Instant

import mrks.BaseSpec

class RichInstantSpec extends BaseSpec {
  import TimeHelpers.RichInstant

  private val testTime = Instant.now()

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
