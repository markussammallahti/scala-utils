package mrks.time

import java.time.temporal.TemporalAdjusters
import java.time.{Instant, LocalDate, LocalDateTime, LocalTime}

trait TimeHelpers {
  implicit class RichLocalDate(date: LocalDate) {
    def toStartOfDay: LocalDateTime = date.atStartOfDay()
    def toEndOfDay: LocalDateTime   = date.atTime(LocalTime.MAX)
    def toStartOfMonth: LocalDate   = date.`with`(TemporalAdjusters.firstDayOfMonth())
    def toEndOfMonth: LocalDate     = date.`with`(TemporalAdjusters.lastDayOfMonth())

    def <=(other: LocalDate): Boolean = date.isBefore(other) || date.isEqual(other)
    def >=(other: LocalDate): Boolean = date.isAfter(other) || date.isEqual(other)
  }

  implicit class RichInstant(instant: Instant) {
    def <=(other: Instant): Boolean = instant.isBefore(other) || instant.equals(other)
    def >=(other: Instant): Boolean = instant.isAfter(other) || instant.equals(other)
  }
}

object TimeHelpers extends TimeHelpers
