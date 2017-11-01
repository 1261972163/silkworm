package com.jengine.data.mq.kafka.pc;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * @author nouuid
 * @date 4/26/2016
 * @description
 */
public class KafkaServiceTest {

  final CountDownLatch startGate = new CountDownLatch(1);
  private KafkaService kafkaService = new KafkaServiceImpl();
  private KafkaService kafkaService2 = new KafkaServiceImpl();

  private void initProducer() {
    Properties producerProps = new Properties();
    producerProps.put("bootstrap.servers", "10.45.11.149:9092,10.45.11.150:9092");
    producerProps.put("acks", "all");
    producerProps.put("retries", 0);
    producerProps.put("batch.size", 10);
    producerProps.put("linger.ms", 1);
    producerProps.put("buffer.memory", 33554432);
    producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    kafkaService.initProducer(producerProps);
  }

  private void initConsumer() {
    Properties consumerProps = new Properties();
    consumerProps.put("bootstrap.servers", "10.45.11.149:9092,10.45.11.150:9092");
    consumerProps.put("group.id", "test");
    consumerProps.put("auto.offset.reset", "latest");
    consumerProps.put("enable.auto.commit", "false");
    consumerProps.put("auto.commit.interval.ms", "1000");
    consumerProps.put("session.timeout.ms", "30000");
    consumerProps
        .put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    consumerProps
        .put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    kafkaService.initConsumer(consumerProps);
    kafkaService2.initConsumer(consumerProps);
  }

  @org.junit.Test
  public void produce() throws InterruptedException {
    initProducer();
    while (true) {
      for (int i = 0; i < 100; i++) {
        kafkaService.send(new ProducerRecord<String, String>("topic-test", Integer.toString(i),
            Integer.toString(i)));
        System.out.println("put " + Integer.toString(i));
      }
      Thread.sleep(1000);
    }

//        System.out.println("put to kafka. over.");
  }

  @org.junit.Test
  public void consume() throws InterruptedException {
    initConsumer();

    for (int cid = 1; cid <= 2; cid++) {
      final int cumserid = cid;
      System.out.println("cumserid" + cumserid);
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          System.out.println(cumserid + " consume ");
          try {
            startGate.await();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          while (true) {
            ConsumerRecords<String, String> records = null;
            if (cumserid == 1) {
              records = kafkaService.poll();
            } else {
              records = kafkaService2.poll();
            }
            for (ConsumerRecord<String, String> record : records) {
              System.out.printf(cumserid + " offset = %d, key = %s, value = %s", record.offset(),
                  record.key(), record.value());
              System.out.println();
              if (cumserid == 1) {
                kafkaService.getConsumer().commitSync();
              } else {
                kafkaService2.getConsumer().commitSync();
              }
            }
            System.out.println("--------------");
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      });
      thread.start();
    }
    startGate.countDown();

    Thread.sleep(30 * 60 * 1000);

  }

}
