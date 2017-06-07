package com.jengine.data.dataprocess.storm;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * @author nouuid
 * @description
 * 首先，自定义的Spout需要继承Storm的相关Spout的接口，例如BaseRichSpout或者IRichSpout等。
 * 其次，在open函数中，实现资源的初始化等操作，这里没有特殊操作，只将流获取绑定到本身Collector上即可。
 * 第三，声明输出流的格式，即 declareOutputFields函数。
 * 最后，实现流的生成操作nextTuple函数，这里在人名中随机选择一个，并通过emit进行发送，Bolt接收到这个人名，并进行下一步的处理。
 * @date 8/3/16
 */
public class NamesSpout extends BaseRichSpout {

    SpoutOutputCollector m_collector;

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        m_collector = collector;
    }

    public void nextTuple() {
        final String[] names = new String[]{"nathan", "mike", "jackson", "golda", "bertels"};
        final Random rand = new Random();
        final String name = names[rand.nextInt(names.length)];
        Utils.sleep(10);
        m_collector.emit(new Values(name));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("name"));
    }

}


