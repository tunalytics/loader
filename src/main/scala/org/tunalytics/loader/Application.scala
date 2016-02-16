package org.tunalytics.loader

import backtype.storm.Config
import backtype.storm.LocalCluster
import backtype.storm.topology.TopologyBuilder
import backtype.storm.utils.Utils

import org.tunalytics.loader.topology.signals.SignalBolt
import org.tunalytics.loader.topology.signals.SignalSpout

object Application {

    def main(arguments: Array[String]) {
        submitSignalTopology()
        // TODO: implement real topology building and submitting
    }

    private def submitSignalTopology() {

        val config = new Config
        config.setDebug(false)

        val builder = new TopologyBuilder
        builder.setSpout("signalSpout", new SignalSpout, 1)
        builder.setBolt("signalBolt", new SignalBolt, 1)
                .shuffleGrouping("signalSpout")

        val cluster = new LocalCluster
        cluster.submitTopology("testTopology", config, builder.createTopology())

        Utils.sleep(30000);

        cluster.killTopology("testTopology");
        cluster.shutdown();
    }
}
