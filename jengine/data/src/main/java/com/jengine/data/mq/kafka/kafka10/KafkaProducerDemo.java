package com.jengine.data.mq.kafka.kafka10;

import com.jengine.data.mq.kafka.Message;
import com.jengine.transport.serialize.json.JsonUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.After;
import org.junit.Before;

import java.util.Properties;
import java.util.UUID;

/**
 * content
 *
 * @author nouuid
 * @date 12/21/2016
 * @since 0.1.0
 */
public class KafkaProducerDemo {

    private Producer<byte[], byte[]> producer;

    @Before
    public void before() {
        producer = new KafkaProducer<byte[], byte[]>(getProducerProperties());
    }

    @After
    public void after() {
        producer.close();
    }

    private Properties getProducerProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "10.45.11.149:9092,10.45.11.150:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        properties.put("buffer.memory", 1024000); //1M
        properties.put("compression.type", "snappy");
        properties.put("retries", 3);
        properties.put("batch.size", 102400); //100k
        properties.put("max.request.size", 1024000); //1M
        properties.put("block.on.buffer.full", true);
        return properties;
    }


    @org.junit.Test
    public void produce() throws Exception {
        String topic = "localtest1";
        int i = 0;
        while (true) {
            Message message = new Message();
            message.setContent(i + "");
            ProducerRecord<byte[], byte[]> producerRecord = new ProducerRecord<byte[], byte[]>(topic, UUID.randomUUID().toString().getBytes(), JsonUtil.toByteJson(message));
            producer.send(producerRecord);
            Thread.sleep(10);
            System.out.println("put " + i++);
        }
    }
}
