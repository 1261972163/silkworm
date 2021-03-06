package com.jengine.data.mq.kafka.kafkaConsumerMonitor.cache;

import com.jengine.data.mq.kafka.kafkaConsumerMonitor.Metric;

/**
 * @author nouuid
 */
public class CacheUtils {
  public static String buildKey(String group, String topic, String partition) {
    return "[" + group + "," + topic + "," + partition + "]";
  }

  public static Metric parseKey(String key) {
    String[] splits = key.split("\\[|,|\\]");
    Metric metric = new Metric();
    metric.setGroup(splits[1]);
    metric.setTopic(splits[2]);
    metric.setPartition(Integer.parseInt(splits[3]));
    return metric;
  }
}
