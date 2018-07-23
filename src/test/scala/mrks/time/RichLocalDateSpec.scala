package mrks.time

import java.time.{LocalDate, LocalDateTime}

import mrks.BaseSpec

class RichLocalDateSpec extends BaseSpec {
  import TimeHelpers.RichLocalDate

  private val testDate = LocalDate.of(2018, 7, 4)

  "toStartOfDay" should {
    "return LocalDateTime at start of day" in {
      LocalDate.of(2018, 7, 4).toStartOfDay mustBe LocalDateTime.of(2018, 7, 4, 0, 0, 0, 0)
      LocalDate.of(2017, 1, 1).toStartOfDay mustBe LocalDateTime.of(2017, 1, 1, 0, 0, 0, 0)
    }
  }

  "toEndOfDay" should {
    "return LocalDateTime at end of day" in {
      LocalDate.of(2018, 7, 4).toEndOfDay mustBe LocalDateTime.of(2018, 7, 4, 23, 59, 59, 999999999)
      LocalDate.of(2017, 1, 1).toEndOfDay mustBe LocalDateTime.of(2017, 1, 1, 23, 59, 59, 999999999)
    }
  }

  "toStartOfMonth" should {
    "return LocalDate with first day of month" in {
      LocalDate.of(2018, 1, 2).toStartOfMonth mustBe LocalDate.of(2018, 1, 1)
      LocalDate.of(2018, 2, 2).toStartOfMonth mustBe LocalDate.of(2018, 2, 1)
    }
  }

  "toEndOfMonth" should {
    "return LocalDate with last day of month" in {
      LocalDate.of(2018, 1, 1).toEndOfMonth   mustBe LocalDate.of(2018, 1, 31)
      LocalDate.of(2018, 2, 2).toEndOfMonth   mustBe LocalDate.of(2018, 2, 28)
      LocalDate.of(2016, 2, 28).toEndOfMonth  mustBe LocalDate.of(2016, 2, 29)
      LocalDate.of(2018, 4, 15).toEndOfMonth  mustBe LocalDate.of(2018, 4, 30)
    }
  }

  "<=" should {
    "return true" when {
      "date is before" in {
        testDate <= testDate.plusDays(1)    mustBe true
        testDate <= testDate.plusMonths(1)  mustBe true
        testDate <= testDate.plusYears(1)   mustBe true
      }
      "date is equal" in {
        LocalDate.of(2018, 1, 2) <= LocalDate.of(2018, 1, 2) mustBe true
      }
    }
    "return false" when {
      "date is after" in {
        testDate <= testDate.minusDays(1)   mustBe false
        testDate <= testDate.minusMonths(1) mustBe false
        testDate <= testDate.minusYears(1)  mustBe false
      }
    }
  }

  ">=" should {
    "return true" when {
      "date is after" in {
        testDate >= testDate.minusDays(1)   mustBe true
        testDate >= testDate.minusMonths(1) mustBe true
        testDate >= testDate.minusYears(1)  mustBe true
      }
      "date is equal" in {
        LocalDate.of(2018, 1, 2) >= LocalDate.of(2018, 1, 2) mustBe true
      }
    }
    "return false" when {
      "date is before" in {
        testDate >= testDate.plusDays(1)    mustBe false
        testDate >= testDate.plusMonths(1)  mustBe false
        testDate >= testDate.plusYears(1)   mustBe false
      }
    }
  }
}
