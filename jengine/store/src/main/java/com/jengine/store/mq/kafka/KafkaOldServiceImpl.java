package com.jengine.store.mq.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author nouuid
 * @date 4/26/2016
 * @description
 */
public class KafkaOldServiceImpl implements KafkaOldService {

    private kafka.javaapi.producer.Producer<String, String>  producer;
    private kafka.javaapi.consumer.ConsumerConnector consumer;

    @Override
    public void initProducer(Properties producerProps) {
        producer = new kafka.javaapi.producer.Producer<String, String>(new ProducerConfig(producerProps));
    }

    @Override
    public void initConsumer(Properties consumerProps) {
        consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(consumerProps));
    }

    @Override
    public void send(KeyedMessage<String, String> keyedMessage) {
        producer.send(keyedMessage);
    }

    @Override
    public void send(List<KeyedMessage<String, String>> keyedMessages) {
        producer.send(keyedMessages);
    }

    @Override
    public void closeProducer() {
        if (producer!=null) {
            producer.close();
        }
    }

    @Override
    public KafkaStream<byte[], byte[]> poll(String topic) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put(topic, 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(map);
        KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);
        return stream;
    }
}

