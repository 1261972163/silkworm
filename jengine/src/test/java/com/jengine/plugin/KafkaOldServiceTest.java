package com.jengine.plugin;

import com.jengine.plugin.kafka.KafkaOldService;
import com.jengine.plugin.kafka.KafkaOldServiceImpl;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.producer.KeyedMessage;

import java.util.Properties;

/**
 * @author bl07637
 * @date 4/26/2016
 * @description
 */
public class KafkaOldServiceTest {
    private KafkaOldService kafkaOldService = new KafkaOldServiceImpl();
    private String topic = "topic-test";

    private void initProducer() {
        Properties producerProps = new Properties();
        producerProps.put("zookeeper.connect", "silkworm-test-zookeeper:2181/kafka");
        producerProps.put("zk.connectiontimeout.ms", "1000000");
        producerProps.put("metadata.broker.list", "silkworm-test-kafka01:9092,silkworm-test-kafka02:9092,silkworm-test-kafka03:9092,silkworm-test-kafka04:9092");
        producerProps.put("serializer.class", "kafka.serializer.StringEncoder");
        producerProps.put("key.serializer.class", "kafka.serializer.StringEncoder");
        kafkaOldService.initProducer(producerProps);
    }

    private void initConsumer() {
        Properties consumerProps = new Properties();
        consumerProps.put("zookeeper.connect", "silkworm-test-zookeeper:2181/kafka");
        consumerProps.put("metadata.broker.list", "silkworm-test-kafka01:9092,silkworm-test-kafka02:9092,silkworm-test-kafka03:9092,silkworm-test-kafka04:9092");
        consumerProps.put("group.id", "test");
        consumerProps.put("serializer.class", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("key.serializer.class", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("auto.commit.enable", "true");
        kafkaOldService.initConsumer(consumerProps);
    }

    @org.junit.Test
    public void produce(){
        initProducer();
        try {
            int count=0;
            while(count<100){
                KeyedMessage<String, String> msg = new KeyedMessage<String, String>(topic, count+"");
                kafkaOldService.send(msg);
                count++;
                System.out.println("put " + Integer.toString(count));
            }
        } finally {
            kafkaOldService.closeProducer();
        }
        System.out.println("put to kafka. over.");
    }

    @org.junit.Test
    public void consume() throws InterruptedException {
        initConsumer();
        while (true) {
            KafkaStream<byte[], byte[]> stream = kafkaOldService.poll(topic);
            ConsumerIterator<byte[], byte[]> iterator =  stream.iterator();

            int i=0;
            while(iterator.hasNext()){
                String message = new String(iterator.next().message());
                System.out.println("consume " + message);
            }
            Thread.sleep(1000);
        }
    }
}
