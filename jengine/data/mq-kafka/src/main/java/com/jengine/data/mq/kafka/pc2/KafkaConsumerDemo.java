package com.jengine.data.mq.kafka.pc2;

import com.jengine.transport.serialize.json.JsonUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.Test;

/**
 * content
 *
 * @author nouuid
 * @date 12/21/2016
 * @since 0.1.0
 */
public class KafkaConsumerDemo {

  protected final Log logger = LogFactory.getLog(getClass());

  private KafkaConsumerService kafkaConsumerService;

  private Properties getConsumerProperties() {
    Properties properties = new Properties();
    properties.put("bootstrap.servers", "10.45.11.149:9092,10.45.11.150:9092");
    properties
        .put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
    properties
        .put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
    properties.put("group.id", "localtest1-1");
    properties.put("heartbeat.interval.ms", "20000");
    properties.put("max.partition.fetch.bytes", "10000");//3M
    properties.put("request.timeout.ms", "60000");
    properties.put("session.timeout.ms", "30000");
    properties.put("group.max.session.timeout.ms", "60000");
    properties.put("auto.offset.reset", "earliest");
    properties.put("enable.auto.commit", "false");
    return properties;
  }

  @Test
  public void test() {
    ConsumerConfig consumerConfig = new ConsumerConfig();
    consumerConfig.setTopic("localtest");
    consumerConfig.setPartition(1);
    consumerConfig.setConsumerProperties(getConsumerProperties());
    consumerConfig.setAutoConsume(false);
    kafkaConsumerService = new KafkaConsumerService(consumerConfig);

    consume(consumerConfig);
  }


  private void consume(ConsumerConfig consumerConfig) {
    int partition = consumerConfig.getPartition();
    int sleep = 0;
    int countSmall = 0;
    ConsumerRecords<byte[], byte[]> consumerRecords = null;
    while (true) {
      if (consumerRecords == null || consumerRecords.count() <= 0) {
        consumerRecords = kafkaConsumerService.poll();
      }
      if (consumerRecords == null || consumerRecords.count() <= 0) {
        continue;
      }
      if (consumerRecords.count() < 1000) {
        if (countSmall <= 10) { // 最大10s
          countSmall++;
          sleep = 1000 * countSmall;
        } else {
          countSmall = 0;
        }
      } else {
        sleep = 0;
        countSmall = 0;
      }
      logger.info(
          "pull, paid=" + partition + ", to_sleep=" + sleep + ", count=" + consumerRecords.count());
      if (sleep > 0) {
        try {
          Thread.sleep(sleep);
        } catch (InterruptedException e) {
          logger.error("", e);
        }
      }
      long recordOffset = 0;
      // 失败重试
      boolean flag = true;
      int maxFail = 3;
      while (flag && maxFail > 0) {
        try {
          recordOffset = process(consumerRecords);
          if (recordOffset < 0) {
            continue;
          }
        } catch (Exception e) {
          logger.error("", e);
          maxFail--;
          continue;
        }
        kafkaConsumerService.commitSync(recordOffset);
        consumerRecords = null;
        flag = false;
      }
    }
  }


  private long process(ConsumerRecords<byte[], byte[]> consumerRecords) {
    long lastRecordOffset = -1;
    if (consumerRecords == null) {
      return lastRecordOffset;
    }
    for (ConsumerRecord<byte[], byte[]> consumerRecord : consumerRecords) {
      if (consumerRecord == null || consumerRecord.value() == null) {
        logger.debug("kafka message null");
        continue;
      }
      byte[] value = consumerRecord.value();
      Map<String, String> message = JsonUtil.formByteJson(HashMap.class, value);
      if (message == null) {
        continue;
      }
      System.out.println(message);
      lastRecordOffset = consumerRecord.offset();
    }
    return lastRecordOffset;

  }


}
