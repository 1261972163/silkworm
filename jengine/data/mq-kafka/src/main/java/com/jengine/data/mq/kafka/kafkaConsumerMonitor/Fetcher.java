package com.jengine.data.mq.kafka.kafkaConsumerMonitor;

import com.jengine.common.javacommon.utils.StringUtils;
import com.jengine.data.mq.kafka.kafkaConsumerMonitor.cache.CacheUtils;
import com.jengine.data.mq.kafka.kafkaConsumerMonitor.sink.Sink;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

/**
 * Created by nouuid on 10/29/17.
 */
public class Fetcher {
  private String bootstrapServers;
  private KafkaConsumer kafkaMetricConsumer;
  private Sink sink;

  public Fetcher(String bootstrapServers, Sink sink) {
    this.bootstrapServers = bootstrapServers;
    this.sink = sink;
    Properties consumerProps = new Properties();
    consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
    consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
    consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    kafkaMetricConsumer = new KafkaConsumer(consumerProps);
  }

  /**
   * generate consumer metrics
   */
  public void fetch() {
    Set<String> keys = Monitor.cache.getKeys();
    java.util.List<TopicPartition> topicPartitionList = new ArrayList<TopicPartition>();
    for (String key : keys) {
      Metric metric = CacheUtils.parseKey(key);
      TopicPartition topicPartition = new TopicPartition(metric.getTopic(), metric.getPartition());
      topicPartitionList.add(topicPartition);
    }
    kafkaMetricConsumer.assign(topicPartitionList);
    kafkaMetricConsumer.seekToEnd(topicPartitionList);
    ArrayList<Metric> metrics = new ArrayList<Metric>();
    for (String key : keys) {
      Metric metric = CacheUtils.parseKey(key);
      fillLogLagOwner(metric);
      metrics.add(metric);
      if (metrics.size()==1000) {
        sink.save(metrics);
        metrics.clear();
      }
    }
    if (metrics.size()>0) {
      sink.save(metrics);
    }
  }

  private Metric fillLogLagOwner(Metric metric) {
    String key = CacheUtils.buildKey(metric.getGroup(), metric.getTopic(), metric.getPartition() + "");
    TopicPartition topicPartition = new TopicPartition(metric.getTopic(), metric.getPartition());
    long logSize = kafkaMetricConsumer.position(topicPartition);
    String consumerOffsetStr = Monitor.cache.getOffset(key);
    long offset = StringUtils.isBlank(consumerOffsetStr) ? 0l : Long.parseLong(consumerOffsetStr);
    long lagSize = logSize - offset;
    String owner = Monitor.cache.getConumserOwner(key);
    metric.setLogSize(logSize);
    metric.setLogSize(logSize);
    metric.setConsumerOffset(offset);
    metric.setLag(lagSize);
    metric.setOwner(owner);
    return metric;
  }

}
