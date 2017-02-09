package com.jengine.store.mq.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * content
 *
 * @author bl07637
 * @date 12/21/2016
 * @since 0.1.0
 */
public class Kafka090ConsumerDemo {
    private KafkaConsumer<byte[], byte[]> consumer;

    @Before
    public void before() {
        consumer = new KafkaConsumer<byte[], byte[]>(getConsumerProperties());
    }

    private Properties getConsumerProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "10.45.11.149:9092,10.45.11.150:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
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

    /**
     * 指定topic和partition进行消费
     * @throws InterruptedException
     */
    @Test
    public void consume1() throws InterruptedException {
        List<TopicPartition> tps = new LinkedList<TopicPartition>();
        int num = 10;
        for (int i=0; i<num; i++) {
            tps.add(new TopicPartition("localtest1", i));
        }
//        tps.add(new TopicPartition("localtest1", 0));
        consumer.assign(tps);

        int i = 0;
        while (true) {
            i++;
            ConsumerRecords<byte[], byte[]> consumerRecords = consumer.poll(100);
            for (TopicPartition topicPartition : consumerRecords.partitions()) {
                for (ConsumerRecord<byte[], byte[]> record : consumerRecords.records(topicPartition)) {
                    if (record.value() == null) {
                        continue;
                    }
                    System.out.println("--------" + record.topic() + ", " + record.partition());
                    consumer.commitSync(Collections.singletonMap(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1)));
                }
            }
            System.out.println("loop-" + i);
            Thread.sleep(1000);
        }
    }

    /**
     * 自动分配partition消费
     * @throws InterruptedException
     */
    @Test
    public void consume2() throws InterruptedException {
        consumer.subscribe(Arrays.asList("localtest1"));
        int i = 0;
        while (true) {
            i++;
            ConsumerRecords<byte[], byte[]> consumerRecords = consumer.poll(100);
            for (TopicPartition topicPartition : consumerRecords.partitions()) {
                for (ConsumerRecord<byte[], byte[]> record : consumerRecords.records(topicPartition)) {
                    if (record.value() == null) {
                        continue;
                    }
                    System.out.println("--------" + record.topic() + ", " + record.partition());
                    consumer.commitSync(Collections.singletonMap(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1)));
                }
            }
            System.out.println("loop-" + i);
            Thread.sleep(1000);
        }
    }

}
