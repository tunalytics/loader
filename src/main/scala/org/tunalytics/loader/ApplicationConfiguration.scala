package org.tunalytics.loader

import org.apache.logging.log4j.Level
import org.tunalytics.loader.logging.LogConfiguration

class ApplicationConfiguration(val log: LogConfiguration) {

}

object ApplicationConfiguration {

  def parse(arguments: Array[String]): ApplicationConfiguration = {
    val log = new LogConfiguration(level = Level.TRACE)
    new ApplicationConfiguration(log)
  }
}
