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
package org.tunalytics.loader.topology.signals

import java.util.Map

import backtype.storm.tuple.Tuple
import backtype.storm.task.OutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichBolt

import org.tunalytics.loader.logging.LoggerAware

/**
  * Signal consuming bolt.
  *
  * For testing purposes! To be removed in production-ready version.
  */
class SignalBolt extends BaseRichBolt with LoggerAware {

  // TODO: remove this class in production-ready version

  logger.trace("instance created")

  private var configuration: Map[_, _] = _
  private var context: TopologyContext = _
  private var collector: OutputCollector = _

  def execute(tuple: Tuple) {
    logger.trace("processing tuple...")
    val signal = tuple.getValueByField("signal").asInstanceOf[Signal]
    logger.debug("received: " + signal)
    collector.ack(tuple)
    logger.trace("tuple processed")
  }

  def prepare(configuration: Map[_, _], context: TopologyContext,
                       collector: OutputCollector) {
    logger.trace("preparing...")
    this.configuration = configuration
    this.context = context
    this.collector = collector
    logger.trace("prepared")
  }

  def declareOutputFields(declarer: OutputFieldsDeclarer) {

  }
}
