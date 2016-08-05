package com.jengine.plugin.kafka;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.List;
import java.util.Properties;

/**
 * @author nouuid
 * @date 5/27/2016
 * @description
 */
public class Kafka080ProduceService {
    private kafka.javaapi.producer.Producer<byte[], byte[]>  producer;

    public void initProducer(Properties producerProps) {
        producer = new kafka.javaapi.producer.Producer<byte[], byte[]>(new ProducerConfig(producerProps));
    }

    public void send(List<KeyedMessage<byte[], byte[]>> keyedMessages) {
        producer.send(keyedMessages);
    }

}
