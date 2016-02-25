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
package org.tunalytics.loader.transformer.topology.signals

import java.util.Map

import backtype.storm.spout.SpoutOutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichSpout
import backtype.storm.tuple.Fields
import backtype.storm.tuple.Values
import com.typesafe.scalalogging.LazyLogging


/**
  * Signal emitting spout.
  *
  * For testing purposes! To be removed in production-ready version.
  */
class SignalSpout extends BaseRichSpout with LazyLogging {

  // TODO: remove this class in production-ready version

  logger.trace("instance created")

  private var confgiguration: Map[_, _] = _
  private var context: TopologyContext = _
  private var collector: SpoutOutputCollector = _

  private var index: Integer = 0

  def nextTuple() {
    logger.trace("emitting new tuple...")
    Thread.sleep(100)
    val signal = nextSignal()
    logger.debug("emitting: " + signal + "...")
    collector.emit(new Values(signal))
    logger.trace("tuple emitted")
  }

  def declareOutputFields(declarer: OutputFieldsDeclarer) {
    logger.trace("declaring fields...")
    declarer.declare(new Fields("signal"))
    logger.trace("fields declared")
  }

  def open(configuration: Map[_, _], context: TopologyContext,
                    collector: SpoutOutputCollector) {
    logger.trace("preparing...")
    this.confgiguration = configuration
    this.context = context
    this.collector = collector
    logger.trace("prepared...")
  }

  private def nextSignal(): Signal = {
    index += 1
    new Signal(new Message("serial #" + index))
  }
}
