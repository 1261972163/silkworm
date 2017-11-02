package com.jengine.data.mq.kafka.pc2;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

/**
 * @author nouuid
 * @date 5/27/2016
 * @description
 */
public class KafkaConsumerService {

  protected final Log logger = LogFactory.getLog(getClass());

  private KafkaConsumer<byte[], byte[]> consumer = null;
  private TopicPartition topicPartition = null;
  private ConsumerConfig consumerConfig = null;

  public KafkaConsumerService(ConsumerConfig consumerConfig) {
    consumer = new KafkaConsumer<byte[], byte[]>(consumerConfig.getConsumerProperties());
    if (consumerConfig.isAutoConsume()) {
      consumer.subscribe(Arrays.asList(consumerConfig.getTopic()));
    } else {
      List<TopicPartition> tps = new LinkedList<TopicPartition>();
      topicPartition = new TopicPartition(consumerConfig.getTopic(), consumerConfig.getPartition());
      tps.add(topicPartition);
      consumer.assign(tps);
    }
  }

  public ConsumerRecords<byte[], byte[]> poll() {
    int partition = consumerConfig.getPartition();
    int sleep = 0;
    boolean flag = true;
    int countNull = 1;
    ConsumerRecords<byte[], byte[]> consumerRecords = null;
    while (flag) {
      consumerRecords = consumer.poll(50);
      if (consumerRecords == null || consumerRecords.isEmpty()) {
        if (countNull >= 600) {
          logger.info("pull, paid=" + partition + ", pull 600 times (10min), always pull 0.");
          countNull = 0;
        }
        sleep = 100 * countNull; //最多睡眠10min
        if (sleep > 0) {
          try {
            Thread.sleep(sleep);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        countNull++;
        continue;
      }
      flag = false;
    }
    return consumerRecords;
  }

  public void commitSync(long recordOffset) {
    if (consumerConfig.isAutoConsume()) {
      consumer.commitSync();
    } else {
      consumer.commitSync(
          Collections.singletonMap(topicPartition, new OffsetAndMetadata(recordOffset + 1)));
    }
  }

  public void stop() {
    if (consumer != null) {
      consumer.close();
    }
    consumer = null;
  }
}
