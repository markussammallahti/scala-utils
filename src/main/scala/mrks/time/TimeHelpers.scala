package mrks.time

import java.time.temporal.TemporalAdjusters
import java.time.{LocalDate, LocalDateTime, LocalTime}

trait TimeHelpers {
  implicit class RichLocalDate(date: LocalDate) {
    def toStartOfDay: LocalDateTime = date.atStartOfDay()
    def toEndOfDay: LocalDateTime   = date.atTime(LocalTime.MAX)
    def toStartOfMonth: LocalDate   = date.`with`(TemporalAdjusters.firstDayOfMonth())
    def toEndOfMonth: LocalDate     = date.`with`(TemporalAdjusters.lastDayOfMonth())

    def <=(other: LocalDate): Boolean = date.isBefore(other) || date.isEqual(other)
    def >=(other: LocalDate): Boolean = date.isAfter(other) || date.isEqual(other)
  }
}

object TimeHelpers extends TimeHelpers
