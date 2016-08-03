package com.jengine.plugin.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author bl07637
 * @date 5/27/2016
 * @description
 */
public class Kafka080ConsumeService {
    public ConsumerConnector getConsumer080() {
        return consumer080;
    }

    public void setConsumer080(ConsumerConnector consumer080) {
        this.consumer080 = consumer080;
    }

    private kafka.javaapi.consumer.ConsumerConnector consumer080;

    public void initConsumer080(Properties consumerProps) {
        consumer080 = Consumer.createJavaConsumerConnector(new ConsumerConfig(consumerProps));
    }

    public KafkaStream<byte[], byte[]> poll080(String topic) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put(topic, 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer080.createMessageStreams(map);
        KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);
        return stream;
    }
}
