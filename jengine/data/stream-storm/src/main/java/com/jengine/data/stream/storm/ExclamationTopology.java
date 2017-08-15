package com.jengine.data.stream.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

/**
 * @author nouuid
 * @description Topology要把Spout和Bolt的关系建立起来，建立关系的方法主要是通过名称建立。例如指定Spout输出流的处理Bolt时，通过设置shuffleGrouping中的名字即可，即将名字设置为Spout的名字”name”。
 * 最后，载入配置，并执行。这里通过参数区分本地模式和远程模式，如果含有参数，则为远程模式，否则是本地模式。
 * @date 8/3/16
 */
public class ExclamationTopology {

  public static void main(String[] args) throws Exception {

    TopologyBuilder builder = new TopologyBuilder();

    builder.setSpout("name", new NamesSpout(), 5);
    builder.setBolt("exclaim", new ExclamationBolt(), 5).shuffleGrouping("name");

    Config conf = new Config();
    conf.setDebug(false);
    conf.put(Config.TOPOLOGY_DEBUG, false);

    if (args != null && args.length > 0) {
      conf.setNumWorkers(10);
      StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
    } else {
      LocalCluster cluster = new LocalCluster();
      cluster.submitTopology("test", conf, builder.createTopology());
      Utils.sleep(10000);
      cluster.killTopology("test");
      cluster.shutdown();
    }
  }
}


