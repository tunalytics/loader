/*
  Copyright 2016 Tunalytics Foundation

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

          "http://www.apache.org/licenses/LICENSE-2.0".

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package org.tunalytics.loader.logging

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.config.Configuration
import org.apache.logging.log4j.core.config.LoggerConfig
import org.apache.logging.log4j.status.StatusLogger

/**
  * Application logging services configurator.
  */
object LoggerConfigurator {

  StatusLogger.getLogger.setLevel(Level.OFF)

  def use(configuration: LogConfiguration): Unit = {
    logger(configuration.level, "org.tunalytics.loader")
    logger(configuration.stormLevel, "org.apache.storm")
    logger(configuration.stormLevel, "backtype.storm")
    context.updateLoggers;
  }

  private def logger(level: Level, pattern: String): Unit = {
    val loader = new LoggerConfig
    loader.setParent(rootLoggerConfiguration)
    loader.setLevel(level)
    contextConfiguration.addLogger(pattern, loader)
  }

  private def rootLoggerConfiguration: LoggerConfig = {
    contextConfiguration.getLoggerConfig(LogManager.ROOT_LOGGER_NAME)
  }

  private def contextConfiguration: Configuration = {
    context.getConfiguration
  }

  private def context: LoggerContext = {
    LogManager.getContext(false).asInstanceOf[LoggerContext]
  }
}
