package com.jengine.data.dataprocess.storm;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nouuid
 * @description
 * 这里定义了一个map，用来统计名字出现的次数，另外名字修改后会打印到控制台信息中。
 * 统计计算部分都在execute接口中实现，较复杂的情况下，可以拆分为多个Bolt来分别执行不同的计算部分。
 * @date 8/3/16
 */
public class ExclamationBolt extends BaseRichBolt {

    OutputCollector m_collector;

    public Map<String, Integer> NameCountMap = new HashMap<String, Integer>();

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        m_collector = collector;
    }

    public void execute(Tuple input) {
        // 第一步，统计计算
        Integer value = 0;
        if (NameCountMap.containsKey(input.getString(0))) {
            value = NameCountMap.get(input.getString(0));
        }
        NameCountMap.put(input.getString(0), ++value);

        // 第二步，输出
        System.out.println(input.getString(0) + "!!!");
        System.out.println(value);
        m_collector.ack(input);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("name"));
    }
}
