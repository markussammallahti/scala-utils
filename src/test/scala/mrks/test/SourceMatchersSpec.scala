package mrks.test

import akka.stream.scaladsl.Source
import mrks.BaseSpec
import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.matchers.MatchResult

class SourceMatchersSpec extends BaseSpec
  with MaterializerPerSuite
  with SourceMatchers
  with PatienceConfiguration {

  private val source1 = Source(List())
  private val source2 = Source(List(1, 2))
  private val source3 = Source(List(1, 2, 3))
  private val source4 = Source(List('a', 'b'))

  "sourceOf" should {
    "return match result" in {
      val matcher1 = sourceOf(1, 2)
      val matcher2 = sourceOf('a', 'b')

      matcher1.apply(source1) mustBe MatchResult(matches = false, "empty source did not have elements 1, 2", "source did have elements 1, 2")
      matcher1.apply(source2) mustBe MatchResult(matches = true, "source of 1, 2 did not have elements 1, 2", "source did have elements 1, 2")
      matcher1.apply(source3) mustBe MatchResult(matches = false, "source of 1, 2, 3 did not have elements 1, 2", "source did have elements 1, 2")
      matcher1.apply(source4) mustBe MatchResult(matches = false, "source of a, b did not have elements 1, 2", "source did have elements 1, 2")

      matcher2.apply(source1) mustBe MatchResult(matches = false, "empty source did not have elements a, b", "source did have elements a, b")
      matcher2.apply(source2) mustBe MatchResult(matches = false, "source of 1, 2 did not have elements a, b", "source did have elements a, b")
      matcher2.apply(source3) mustBe MatchResult(matches = false, "source of 1, 2, 3 did not have elements a, b", "source did have elements a, b")
      matcher2.apply(source4) mustBe MatchResult(matches = true, "source of a, b did not have elements a, b", "source did have elements a, b")
    }
    "work as matcher" in {
      source2 mustBe sourceOf(1, 2)
      source2 must not be sourceOf(1, 2, 3)
      source4 mustBe sourceOf('a', 'b')
    }
  }

  "emptySource" should {
    "return match result" in {
      val matcher = emptySource

      matcher.apply(source1) mustBe MatchResult(matches = true, "source of  was not empty", "source was empty")
      matcher.apply(source2) mustBe MatchResult(matches = false, "source of 1, 2 was not empty", "source was empty")
      matcher.apply(source3) mustBe MatchResult(matches = false, "source of 1, 2, 3 was not empty", "source was empty")
      matcher.apply(source4) mustBe MatchResult(matches = false, "source of a, b was not empty", "source was empty")
    }
    "work as matcher" in {
      source1 mustBe emptySource
      source2 must not be emptySource
      source3 must not be emptySource
    }
  }
}
