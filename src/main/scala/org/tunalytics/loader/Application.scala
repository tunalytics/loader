package org.tunalytics.loader

import backtype.storm.Config
import backtype.storm.LocalCluster
import backtype.storm.topology.TopologyBuilder
import backtype.storm.utils.Utils

import org.tunalytics.loader.logging.LoggerAware
import org.tunalytics.loader.logging.LoggerConfigurator
import org.tunalytics.loader.topology.signals.Message
import org.tunalytics.loader.topology.signals.Signal
import org.tunalytics.loader.topology.signals.SignalBolt
import org.tunalytics.loader.topology.signals.SignalSpout

object Application extends LoggerAware {

    def main(arguments: Array[String]) {
        LoggerConfigurator.configure
        submitSignalTopology
        // TODO: implement real topology building and submitting
    }

    private def submitSignalTopology {

        val config = new Config
        config.registerSerialization(Signal.getClass)
        config.registerSerialization(Message.getClass)

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
}
