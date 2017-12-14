package com.jengine.data.mq.kafka;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class kafkaProducer {
    @Test
    public void test1() throws JsonProcessingException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.45.4.95:9092,10.45.4.96:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("a", 123);
        values.put("log_hostName", "bg302294");
        values.put("log_application", "yqjTest");
        values.put("log_hostIP", "10.45.16.104");
        values.put("log_service", "yqjTest");
        values.put("log_timestamp", "1511506830081");
        values.put("log_tag", "esTest:");
        ObjectMapper json = new ObjectMapper();
        String s1 = json.writeValueAsString(values);
        System.out.println(s1);
//        String s = "{\"a\":123,\"log_hostName\":\"bg302294\",\"log_application\":\"yqjTest\",\"log_hostIP\":\"10.45.16.104\",\"log_service\":\"yqjTest\",\"log_timestamp\":\"1511506830081\",\"log_tag\":\"esTest:\"}";


        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>("yqjlog-test1", Integer.toString(i), s1));
        }

        producer.close();
    }

    @Test
    public void test2() throws JsonProcessingException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.45.4.95:9092,10.45.4.96:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("a", 123);
        values.put("log_hostName", "bg302294");
        values.put("log_application", "yqjTest");
        values.put("log_hostIP", "10.45.16.104");
        values.put("log_service", "yqjTest");
        values.put("log_timestamp", "1511506830081");
        values.put("log_tag", "esTest:");
        ObjectMapper json = new ObjectMapper();
        String s1 = json.writeValueAsString(values);
        System.out.println(s1);
        ProducerRecord<String, String>  pr = null;
        for (int i = 0; i < 100; i++){
            pr = new ProducerRecord<String, String>("yqjlog-test1", Integer.toString(i), s1);
            producer.send(pr);

        }

        producer.close();
    }
}
