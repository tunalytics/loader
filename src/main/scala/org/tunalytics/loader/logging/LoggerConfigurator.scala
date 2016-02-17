package org.tunalytics.loader.logging

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.config.Configuration
import org.apache.logging.log4j.core.config.LoggerConfig
import org.apache.logging.log4j.status.StatusLogger

object LoggerConfigurator {

    def configure() {

        StatusLogger.getLogger().setLevel(Level.OFF)

        trace("org.tunalytics.loader")

        error("org.apache.storm")
        error("backtype.storm")

        context.updateLoggers;
    }

    private def trace(pattern: String) {
        logger(Level.TRACE, pattern)
    }

    private def info(pattern: String) {
        logger(Level.INFO, pattern)
    }

    private def warn(pattern: String) {
        logger(Level.WARN, pattern)
    }

    private def error(pattern: String) {
        logger(Level.ERROR, pattern)
    }

    private def logger(level: Level, pattern: String) {
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
