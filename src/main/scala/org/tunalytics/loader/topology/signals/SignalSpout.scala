package org.tunalytics.loader.topology.signals

import java.util.Map

import backtype.storm.spout.SpoutOutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichSpout
import backtype.storm.tuple.Fields
import backtype.storm.tuple.Values

import org.tunalytics.loader.logging.LoggerAware

// FIXME: remove this class in production-ready version
class SignalSpout extends BaseRichSpout with LoggerAware {

    private var confgiguration: Map[_,_] = _
    private var context: TopologyContext = _
    private var collector: SpoutOutputCollector = _

    private var index: Integer = 0

    override def nextTuple() {
      Thread.sleep(1000)
      val message = nextMessage()
      collector.emit(new Values(message))
      logger.info("Signal emitted: [" + message + "]")
    }

    override def declareOutputFields(declarer: OutputFieldsDeclarer) {
        declarer.declare(new Fields("message"))
    }

    override def open(configuration: Map[_, _], context: TopologyContext,
            collector: SpoutOutputCollector) {
        logger.info("Preparing a signal spout...")
        this.confgiguration = configuration
        this.context = context
        this.collector = collector
    }

    private def nextMessage(): String = {
        index += 1
        "signal #" + index
    }
}
