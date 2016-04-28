package com.jengine.plugin;

import com.jengine.plugin.kafka.KafkaService;
import com.jengine.plugin.kafka.KafkaServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author bl07637
 * @date 4/26/2016
 * @description
 */
public class KafkaServiceTest {
    private KafkaService kafkaService = new KafkaServiceImpl();

    private void initProducer() {
        Properties producerProps = new Properties();
        producerProps.put("zookeeper.connect", "silkworm-test-zookeeper:2181/kafka");
        producerProps.put("zk.connectiontimeout.ms", "1000000");
        producerProps.put("metadata.broker.list", "silkworm-test-kafka01:9092,silkworm-test-kafka02:9092,silkworm-test-kafka03:9092,silkworm-test-kafka04:9092");
        producerProps.put("serializer.class", "kafka.serializer.StringEncoder");
        producerProps.put("key.serializer.class", "kafka.serializer.StringEncoder");
        kafkaService.initProducer(producerProps);
    }

    private void initConsumer() {
        Properties consumerProps = new Properties();
        consumerProps.put("zookeeper.connect", "10.8.73.187:2181/kafka");
        consumerProps.put("metadata.broker.list", "silkworm-test-kafka01:9092,silkworm-test-kafka02:9092,silkworm-test-kafka03:9092,silkworm-test-kafka04:9092");
        consumerProps.put("group.id", "test");
        consumerProps.put("serializer.class", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("key.serializer.class", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("auto.commit.enable", "true");
        kafkaService.initConsumer(consumerProps);
    }

    @org.junit.Test
    public void produce() throws InterruptedException {
        initProducer();
        for(int i = 0; i < 100; i++) {
            kafkaService.send(new ProducerRecord<String, String>("topic-test", Integer.toString(i), Integer.toString(i)));
            System.out.println("put " + Integer.toString(i));
        }

        System.out.println("put to kafka. over.");
    }

    @org.junit.Test
    public void consume() throws InterruptedException {
        initConsumer();
        int i = 0;
        while (true) {
            System.out.println("consume " + (i++));
            ConsumerRecords<String, String> records = kafkaService.poll();
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());
            }
            Thread.sleep(1000);
        }
    }

}
