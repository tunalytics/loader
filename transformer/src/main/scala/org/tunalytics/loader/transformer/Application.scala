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
package org.tunalytics.loader.transformer

import backtype.storm.topology.TopologyBuilder
import backtype.storm.utils.Utils
import backtype.storm.{Config, LocalCluster}
import com.typesafe.scalalogging.LazyLogging
import org.tunalytics.loader.transformer.topology.signals.{Message, Signal, SignalBolt, SignalSpout}


/**
  * Application instance.
  */
object Application extends LazyLogging {

  /**
    * Application execution entry point.
    *
    * @param arguments command line arguments.
    */
  def main(arguments: Array[String]): Unit = {
    submitSignalTopology()
    // TODO: implement real topology building and submitting
  }

  private def submitSignalTopology() {

    val config = new Config
    config.registerSerialization(Signal.getClass)
    config.registerSerialization(Message.getClass)

    val builder = new TopologyBuilder
    builder.setSpout("signalSpout", new SignalSpout, 1)
    builder.setBolt("signalBolt", new SignalBolt, 1)
      .shuffleGrouping("signalSpout")

    logger.trace("submitting the topology locally for 30 seconds...")
    val cluster = new LocalCluster
    cluster.submitTopology("testTopology", config, builder.createTopology())

    Utils.sleep(30000);

    logger.trace("killing the topology...")
    cluster.killTopology("testTopology");
    cluster.shutdown();
  }
}
