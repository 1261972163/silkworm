package com.jengine.plugin.kafka;

import kafka.consumer.KafkaStream;
import kafka.producer.KeyedMessage;

import java.util.Properties;

/**
 * @author nouuid
 * @date 4/26/2016
 * @description
 */
public interface KafkaOldService {
    public void initProducer(Properties producerProps);
    public void initConsumer(Properties consumerProps);

    public void send(KeyedMessage<String, String> msg);
    public KafkaStream<byte[], byte[]> poll(String topic);

    public void closeProducer();
}
