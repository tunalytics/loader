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
package org.tunalytics.loader.transformer.topology

import backtype.storm.generated.StormTopology
import backtype.storm.topology.TopologyBuilder
import com.typesafe.scalalogging.LazyLogging

import org.tunalytics.loader.logging.LoggerAware

object TopologyFactory extends LazyLogging {

  def create(configuration: TopologyConfiguration): StormTopology = {
    val builder = new TopologyBuilder
    // TODO: build a real ETL topology
    builder.createTopology
  }
}
