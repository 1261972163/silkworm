package com.jengine.data.mq.kafka.monitor;

import com.jengine.common.utils.StringUtils;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * content
 *
 * @author nouuid
 * @date 4/19/2017
 * @since 0.1.0
 */
public class KafkaMonitor {

  private static final Logger logger = LoggerFactory.getLogger(KafkaMonitor.class);
  private String bootstrapServers = null;
  private KafkaConsumer kafkaConsumer = null;
  private String group = null;
  private KafkaMonitorOffsetCache kafkaMonitorOffsetCache = null;

  public KafkaMonitor(String bootstrapServers, String group) {
    this.bootstrapServers = bootstrapServers;
    this.group = group;
    Thread thread = new Thread(new Runnable() {
      public void run() {
        startCache();
      }
    });
    thread.start();
    kafkaConsumer = initKafkaConsumer(group);
  }

  public void close() {
    kafkaConsumer.close();
    kafkaMonitorOffsetCache.stop();
  }

  public void startCache() {
    kafkaMonitorOffsetCache = KafkaMonitorOffsetCache.getInstance(bootstrapServers);
    kafkaMonitorOffsetCache.start();
  }

  public List<OffsetInfo> monitor(String topic, int[] partitions) {
    List<OffsetInfo> offsetInfos = new LinkedList<OffsetInfo>();
    for (int partition : partitions) {
      OffsetInfo offsetInfo = monitor(topic, partition);
      if (offsetInfo == null) {
        continue;
      }
      offsetInfos.add(offsetInfo);
    }
    return offsetInfos;
  }

  public OffsetInfo monitor(String topic, int partition) {
    String key = "[" + group + "," + topic + "," + partition + "]";
    List<PartitionInfo> partitionInfos = kafkaConsumer.partitionsFor(topic);
    boolean isExisted = false;
    for (PartitionInfo partitionInfo : partitionInfos) {
      if (partitionInfo.partition() == partition) {
        isExisted = true;
        break;
      }
    }
    if (!isExisted) {
      return null;
    }
    long logSize = getLogSize(kafkaConsumer, topic, partition);
    String consumerOffsetStr = kafkaMonitorOffsetCache.getOffsetCahe().get(key);
    long consuemrOffset = 0l;
    if (StringUtils.isNotBlank(consumerOffsetStr)) {
      consuemrOffset = Long.parseLong(consumerOffsetStr);
    }
    long lag = logSize - consuemrOffset;
    String owner = partition + "";
    OffsetInfo offsetInfo = buildOffsetInfo(topic, partition, logSize, consuemrOffset, lag, owner);
    return offsetInfo;
  }

  private long getLogSize(KafkaConsumer kafkaConsumer, String topic, int partition) {
    TopicPartition topicPartition = new TopicPartition(topic, partition);
    kafkaConsumer.assign(Arrays.asList(new TopicPartition[]{topicPartition}));
    kafkaConsumer.seekToEnd(Arrays.asList(new TopicPartition[]{topicPartition}));
    long logSize = kafkaConsumer.position(topicPartition);
    return logSize;
  }

  private KafkaConsumer initKafkaConsumer(String group) {
    Properties consumerProps = new Properties();
    consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, group);
    consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
    consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerProps);
    return kafkaConsumer;
  }


  private OffsetInfo buildOffsetInfo(String topic, int partition, long logSize, long consumerOffset,
      long lag, String owner) {
    OffsetInfo monitorInfo = new OffsetInfo();
    monitorInfo.setTopic(topic);
    monitorInfo.setPartition(partition);
    monitorInfo.setLogSize(logSize);
    monitorInfo.setConsumerOffset(consumerOffset);
    monitorInfo.setLag(lag);
    monitorInfo.setOwner(owner);
    return monitorInfo;
  }
}
