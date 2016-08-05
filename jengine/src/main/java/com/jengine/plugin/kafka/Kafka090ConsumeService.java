package com.jengine.plugin.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author nouuid
 * @date 5/27/2016
 * @description
 */
public class Kafka090ConsumeService {
    private KafkaConsumer<byte[], byte[]> consumer090;

    public void initConsumer090(Properties consumerProps, String topic) {
        consumer090 = new KafkaConsumer<byte[], byte[]>(consumerProps);
        consumer090.subscribe(Arrays.asList(topic));
    }

    public ConsumerRecords<byte[], byte[]> poll090(int num) {
        ConsumerRecords<byte[], byte[]> records = consumer090.poll(num);
        return records;
    }
}
