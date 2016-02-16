package org.tunalytics.loader.topology.signals

import java.util.Map

import backtype.storm.spout.SpoutOutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichSpout
import backtype.storm.tuple.Fields
import backtype.storm.tuple.Values

class SignalSpout extends BaseRichSpout {

    private var confgiguration: Map[_,_] = _
    private var context: TopologyContext = _
    private var collector: SpoutOutputCollector = _

    private var index: Integer = 1

    override def nextTuple() {
      Thread.sleep(1000)
      collector.emit(new Values("signal message #" + index))
      index += 1;
    }

    override def declareOutputFields(declarer: OutputFieldsDeclarer) {
        declarer.declare(new Fields("message"))
    }

    override def open(configuration: Map[_, _], context: TopologyContext,
            collector: SpoutOutputCollector) {
        this.confgiguration = configuration
        this.context = context
        this.collector = collector
    }
}
