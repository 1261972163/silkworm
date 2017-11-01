package com.jengine.data.mq.kafka.pc;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author nouuid
 * @date 4/26/2016
 * @description
 */
public class KafkaServiceImpl implements KafkaService {

  private Producer<String, String> producer;

  private KafkaConsumer<String, String> consumer;

  @Override
  public void initProducer(Properties producerProps) {
//        producerProps.put("bootstrap.servers", "xingng-test-kafka01.800best.com:9092,xingng-test-kafka02.800best.com:9092,xingng-test-kafka03.800best.com:9092,xingng-test-kafka04.800best.com:9092");
//        producerProps.put("acks", "all");
//        producerProps.put("retries", 0);
//        producerProps.put("batch.size", 10);
//        producerProps.put("linger.ms", 1);
//        producerProps.put("buffer.memory", 33554432);
//        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    producer = new KafkaProducer<String, String>(producerProps);
  }

  @Override
  public void initConsumer(Properties consumerProps) {
//        consumerProps.put("bootstrap.servers", "xingng-test-kafka01.800best.com:9092,xingng-test-kafka02.800best.com:9092,xingng-test-kafka03.800best.com:9092,xingng-test-kafka04.800best.com:9092");
//        consumerProps.put("group.id", "test");
//        consumerProps.put("auto.offset.reset", "earliest");
//        consumerProps.put("enable.auto.commit", "true");
//        consumerProps.put("auto.commit.interval.ms", "1000");
//        consumerProps.put("session.timeout.ms", "30000");
//        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    consumer = new KafkaConsumer<String, String>(consumerProps);
    consumer.subscribe(Arrays.asList("topic-test"));
  }

  @Override
  public void send(ProducerRecord producerRecord) {
    producer.send(producerRecord);
  }

  @Override
  public ConsumerRecords<String, String> poll() {
    return consumer.poll(100);
  }

  @Override
  public void closeProducer() {
    if (producer != null) {
      producer.close();
    }
  }

  public KafkaConsumer<String, String> getConsumer() {
    return consumer;
  }
}

