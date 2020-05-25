package mrks.test

import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.{Millis, Span}

import scala.concurrent.duration.FiniteDuration


trait PatienceConfigurationUtils { this: PatienceConfiguration =>
  object patience {
    def config(timeout: FiniteDuration, interval: FiniteDuration): PatienceConfig = {
      PatienceConfig(
        timeout  = scaled(Span(timeout.toMillis, Millis)),
        interval = scaled(Span(interval.toMillis, Millis))
      )
    }

    def scaledBy(times: Int): PatienceConfig = {
      PatienceConfig(
        timeout   = patienceConfig.timeout.scaledBy(times),
        interval  = patienceConfig.interval.scaledBy(times),
      )
    }

    def timeoutScaledBy(times: Int): PatienceConfiguration.Timeout = {
      timeout(patienceConfig.timeout.scaledBy(times))
    }
  }
}
