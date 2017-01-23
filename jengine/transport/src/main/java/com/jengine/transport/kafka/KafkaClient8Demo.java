package com.jengine.transport.kafka;

import com.jengine.serializer.json.JsonUtil;
import kafka.consumer.*;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.junit.Test;

import java.util.*;

/**
 * content
 *
 * @author nouuid
 * @date 1/22/2017
 * @since 0.1.0
 */
public class KafkaClient8Demo {

    private Kafka080ProduceService kafka080ProduceService;
    private Kafka080ConsumeService kafka080ConsumeService;

    @Test
    public void produceTest() {
        kafka080ProduceService = new Kafka080ProduceService();

        List<KeyedMessage<byte[], byte[]>> keyedMessageList = new ArrayList<KeyedMessage<byte[], byte[]>>();
        String topic = "localtest";
        for (int i = 0; i < 1000; i++) {
            Message message = new Message();
            message.setContent(UUID.randomUUID().toString());
            KeyedMessage<byte[], byte[]> keyedMessage = new KeyedMessage<byte[], byte[]>(topic, UUID.randomUUID().toString().getBytes(), JsonUtil.toByteJson(message));
            keyedMessageList.add(keyedMessage);
        }
        kafka080ProduceService.send(keyedMessageList);
    }

    @Test
    public void consumeTest() {
        String topic = "localtest";
        kafka080ConsumeService = new Kafka080ConsumeService();
        KafkaStream<byte[], byte[]> stream = kafka080ConsumeService.poll(topic);
        ConsumerIterator<byte[], byte[]> iterator =  stream.iterator();
        int i = 1;
        while (hasNext(iterator)) {
            MessageAndMetadata<byte[], byte[]> mam = iterator.next();
            if (mam.message() == null) {
                continue;
            }
            Message message = JsonUtil.formByteJson(Message.class, mam.message());
            if (message == null) {
                continue;
            }
            System.out.println(message.getContent());
        }
        kafka080ConsumeService.getConsumer080().shutdown();
    }

    private boolean hasNext(ConsumerIterator<byte[], byte[]> iterator) {
        try {
            return iterator.hasNext();
        } catch (ConsumerTimeoutException e) {
            return false;
        }
    }
}

class Kafka080ProduceService {
    private kafka.javaapi.producer.Producer<byte[], byte[]>  producer;

    public void Kafka080ProduceService() {
        producer = new kafka.javaapi.producer.Producer<byte[], byte[]>(new ProducerConfig(defaultProducerConfig()));
    }

    public void Kafka080ProduceService(Properties producerProps) {
        producer = new kafka.javaapi.producer.Producer<byte[], byte[]>(new ProducerConfig(producerProps));
    }

    public void send(List<KeyedMessage<byte[], byte[]>> keyedMessages) {
        producer.send(keyedMessages);
    }

    public Properties defaultProducerConfig() {
        Properties producerProps = new Properties();
        producerProps.put("zookeeper.connect", "10.8.73.187:2181/kafka");
        producerProps.put("zk.connectiontimeout.ms", "1000000");
        producerProps.put("metadata.broker.list", "10.45.11.84:9092");
        producerProps.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        producerProps.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        return producerProps;
    }
}


class Kafka080ConsumeService {

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

    public KafkaStream<byte[], byte[]> poll(String topic) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put(topic, 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer080.createMessageStreams(map);
        KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);
        return stream;
    }

    public Properties defaultConsumerConfig() {
        Properties consumerProps = new Properties();
        consumerProps.put("zookeeper.connect", "10.8.73.187:2181/kafka");
        consumerProps.put("metadata.broker.list", "10.45.11.84:9092");
        consumerProps.put("group.id", "test1");
        consumerProps.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        consumerProps.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        consumerProps.put("consumer.timeout.ms", "1000");
        consumerProps.put("enable.auto.commit", "true");
        consumerProps.put("auto.offset.reset","smallest");
        consumerProps.put("auto.commit.interval.ms", "1000");
        return consumerProps;
    }
}