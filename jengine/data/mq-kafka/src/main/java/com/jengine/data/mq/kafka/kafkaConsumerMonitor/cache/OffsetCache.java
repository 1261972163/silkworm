package com.jengine.data.mq.kafka.kafkaConsumerMonitor.cache;

import com.google.common.collect.MapMaker;
import com.jengine.data.mq.kafka.monitor.KafkaMonitor;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import kafka.common.OffsetAndMetadata;
import kafka.coordinator.GroupMetadataManager;
import kafka.coordinator.OffsetKey;

/**
 * content
 *
 * @author nouuid
 * @date 4/20/2017
 * @since 0.1.0
 */
class OffsetCache {

  private static final Logger logger = LoggerFactory.getLogger(KafkaMonitor.class);
  private KafkaConsumer kafkaConsumer = null;
  private volatile boolean active = false;
  private volatile boolean hasRunning = false;
  private Map<String, String> consumerOffsetCache = new MapMaker().makeMap();

  protected OffsetCache(String bootstrapServers) {
    // consumer
    Properties consumerProps = new Properties();
    consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "MyKafkaManagerOffsetCache");
    consumerProps.put("exclude.internal.topics", "false");
    consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.ByteArrayDeserializer");
    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.ByteArrayDeserializer");
    consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
    long start = System.currentTimeMillis();
    kafkaConsumer = new KafkaConsumer(consumerProps);
    kafkaConsumer.subscribe(Arrays.asList("__consumer_offsets"));
    logger.info("init OffsetCache cost: " + (System.currentTimeMillis() - start));
  }

  protected String getOffset(String key) {
    if (key == null || key.length() <= 0) {
      return null;
    }
    return consumerOffsetCache.get(key);
  }

  protected Set<String> getKeys() {
    return consumerOffsetCache.keySet();
  }

  protected void start() throws InterruptedException {
    Cache.ownerCacheCountDownLatch.await();
    if (active) {
      throw new RuntimeException("This OffsetCache is already active.");
    }
    active = true;
    logger.info("OffsetCache started.");
    ConsumerRecords<byte[], byte[]> consumerRecords = null;
    boolean firstRound = true;
    while (active) {
      hasRunning = true;
      consumerRecords = kafkaConsumer.poll(100);
      if (firstRound) {
        Cache.offsetCacheCountDownLatch.countDown();
        firstRound = false;
      }
      Iterator<ConsumerRecord<byte[], byte[]>> iterator = consumerRecords.iterator();
      while (iterator.hasNext()) {
        ConsumerRecord<byte[], byte[]> consumerRecord = iterator.next();
        if (consumerRecord.key() == null || consumerRecord.value() == null) {
          continue;
        }
        try {
          Object formattedKey = GroupMetadataManager
              .readMessageKey(ByteBuffer.wrap(consumerRecord.key()));
          Object formattedValue = GroupMetadataManager
              .readOffsetMessageValue(ByteBuffer.wrap(consumerRecord.value()));
          if (formattedKey instanceof OffsetKey && formattedValue instanceof OffsetAndMetadata) {
            String groupTopicPartition = ((OffsetKey) formattedKey).toString();
            String offsetAndMetadata = ((OffsetAndMetadata) formattedValue).toString();
            String[] offsetAndMetadataItems = offsetAndMetadata.split("\\[|,|\\]");
            if (offsetAndMetadataItems.length != 7) {
              continue;
            }
            if (groupTopicPartition.startsWith("[MyKafkaManagerOffsetCache")) {
              continue;
            }
//            System.out.println(groupTopicPartition + " / " + offsetAndMetadataItems[2]);
            // consumergroup, topic, partition, offset
            consumerOffsetCache.put(groupTopicPartition, offsetAndMetadataItems[2]);
          }
        } catch (Exception e) {
          continue;
        }
      }
      kafkaConsumer.commitSync();
      hasRunning = false;
    }
  }

  protected void stop() {
    active = false;
    while (hasRunning) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        logger.error("", e);
      }
    }
    kafkaConsumer.close();
  }
}
