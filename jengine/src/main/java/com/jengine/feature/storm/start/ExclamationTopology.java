package com.jengine.feature.storm.start;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.utils.Utils;

/**
 * @author nouuid
 * @description
 * @date 8/3/16
 */
public class ExclamationTopology {

    public static void main(String[] args) throws Exception {

        TopologyBuilder builder =  new TopologyBuilder();

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


