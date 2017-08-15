package com.jengine.data.mq.kafka.simple;

import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author nouuid
 * @date 4/26/2016
 * @description
 */
public interface KafkaService {

  public void initProducer(Properties producerProps);

  public void initConsumer(Properties consumerProps);

  public void send(ProducerRecord producerRecord);

  public ConsumerRecords<String, String> poll();

  public void closeProducer();

  public KafkaConsumer<String, String> getConsumer();
}
