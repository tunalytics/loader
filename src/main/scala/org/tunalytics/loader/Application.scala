package org.tunalytics.loader

import backtype.storm.Config
import backtype.storm.LocalCluster
import backtype.storm.topology.TopologyBuilder
import backtype.storm.utils.Utils

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.config.LoggerConfig
import org.apache.logging.log4j.status.StatusLogger

import org.tunalytics.loader.topology.signals.SignalBolt
import org.tunalytics.loader.topology.signals.SignalSpout

object Application {

    @transient
    private lazy val logger = LogManager.getLogger()

    def main(arguments: Array[String]) {
        configureLoggers();
        submitSignalTopology()
        // TODO: implement real topology building and submitting
    }

    private def submitSignalTopology() {

        val config = new Config

        val builder = new TopologyBuilder
        builder.setSpout("signalSpout", new SignalSpout, 1)
        builder.setBolt("signalBolt", new SignalBolt, 1)
                .shuffleGrouping("signalSpout")

        logger.info("Submitting the topology locally for 30 seconds...")
        val cluster = new LocalCluster
        cluster.submitTopology("testTopology", config, builder.createTopology())

        Utils.sleep(30000);

        logger.info("Killing the topology...")
        cluster.killTopology("testTopology");
        cluster.shutdown();
    }

    private def configureLoggers() {

        StatusLogger.getLogger().setLevel(Level.OFF)

        val context = LogManager.getContext(false).asInstanceOf[LoggerContext]
        val contextConfig = context.getConfiguration()

        val root = contextConfig.getLoggerConfig(LogManager.ROOT_LOGGER_NAME) 

        val loader = new LoggerConfig
        loader.setParent(root)
        loader.setLevel(Level.INFO)
        contextConfig.addLogger("org.tunalytics.loader", loader)

        val storm = new LoggerConfig
        storm.setParent(root)
        storm.setLevel(Level.ERROR)
        contextConfig.addLogger("org.apache.storm", storm)

        context.updateLoggers();
    }
}
