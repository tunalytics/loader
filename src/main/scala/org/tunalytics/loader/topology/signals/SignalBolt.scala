package org.tunalytics.loader.topology.signals

import java.util.Map

import backtype.storm.tuple.Tuple
import backtype.storm.task.OutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichBolt

import org.apache.logging.log4j.LogManager

// FIXME: remove this class in production-ready version
class SignalBolt extends BaseRichBolt {

    @transient
    private lazy val logger = LogManager.getLogger()

    private var configuration: Map[_,_] = _
    private var context: TopologyContext = _
    private var collector: OutputCollector = _

    override def execute(signal: Tuple) {
        val message = signal.getString(0)
        logger.info("Signal consumed: [" + message + "]")
        collector.ack(signal)
    }

    override def prepare(configuration: Map[_,_], context: TopologyContext,
            collector: OutputCollector) {
        logger.info("Preparing a signal bolt...")
        this.configuration = configuration
        this.context = context
        this.collector = collector
    }

    override def declareOutputFields(declarer: OutputFieldsDeclarer) {

    }
}
