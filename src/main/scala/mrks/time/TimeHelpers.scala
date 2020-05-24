package mrks.time

import java.time.temporal.{ChronoField, ChronoUnit, TemporalAdjusters}
import java.time._


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
    def toStartOfDay: Instant = instant.truncatedTo(ChronoUnit.DAYS)
    def toEndOfDay: Instant   = instant.truncatedTo(ChronoUnit.DAYS).plusNanos(ChronoField.NANO_OF_DAY.range.getMaximum)

    def toStartOfMonth: Instant = {
      instant
          .atOffset(ZoneOffset.UTC)
          .`with`(TemporalAdjusters.firstDayOfMonth())
          .truncatedTo(ChronoUnit.DAYS)
          .toInstant
    }

    def toEndOfMonth: Instant   = {
      instant
          .atOffset(ZoneOffset.UTC)
          .`with`(TemporalAdjusters.lastDayOfMonth())
          .truncatedTo(ChronoUnit.DAYS)
          .plusNanos(ChronoField.NANO_OF_DAY.range.getMaximum)
          .toInstant
    }

    def <=(other: Instant): Boolean = instant.isBefore(other) || instant.equals(other)
    def >=(other: Instant): Boolean = instant.isAfter(other) || instant.equals(other)
  }
}

object TimeHelpers extends TimeHelpers
