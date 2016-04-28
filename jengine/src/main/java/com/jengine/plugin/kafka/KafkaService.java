package com.jengine.plugin.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

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
}
