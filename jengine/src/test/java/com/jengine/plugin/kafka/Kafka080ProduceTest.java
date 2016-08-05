package com.jengine.plugin.kafka;

import com.jengine.feature.serialize.json.JsonUtil;
import kafka.producer.KeyedMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author nouuid
 * @date 5/26/2016
 * @description
 */
public class Kafka080ProduceTest {
    private Kafka080ProduceService kafka080ProduceService = new Kafka080ProduceService();
    private static long max = 102L;
    private int pn = 1;
    private AtomicLong count = new AtomicLong(0);
    private static volatile boolean flag = true;
    final CountDownLatch startGate = new CountDownLatch(1);
    final CountDownLatch endGate = new CountDownLatch(pn);

    private String topic = "topic3";

    public static void main(String[] args) {
        Kafka080ProduceTest kafkaProduceTest = new Kafka080ProduceTest();
        kafkaProduceTest.run();
    }

    public void run() {
        initProducer();
        Thread[] producer = new Thread[pn];
        for (int i=0; i< pn; i++) {
            producer[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        produce();
                    } finally {
                        endGate.countDown();
                    }
                }
            });
            producer[i].start();
        }

        try {
            startGate.countDown();
            endGate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------------------- waiting");
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------------------- over");
    }

    public void produce() {
        try {
            startGate.await();
            List<MonitorLog> monitorLogs = bulkBuild();
            List<KeyedMessage<byte[], byte[]>> keyedMessageList = slice(monitorLogs, topic);
            kafka080ProduceService.send(keyedMessageList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<MonitorLog> bulkBuild() {
        List<MonitorLog> monitorLogs = new ArrayList<MonitorLog>();
        int i = 1;
        while(i <= max) {
            MonitorLog monitorLog = new MonitorLog();
            monitorLog.setAppId("m" + i);
            monitorLog.setType(1);
            monitorLogs.add(monitorLog);
            i++;
        }
        return monitorLogs;
    }

    private List<KeyedMessage<byte[], byte[]>> slice(final List<MonitorLog> list, String topic) {
        int pieces = list.size() / 10;
        List<KeyedMessage<byte[], byte[]>> res = new ArrayList<KeyedMessage<byte[], byte[]>>();

        for (int i = 0; i < pieces; i++) {
            LogPackage logPackage = new LogPackage(list.subList(i * 10, (i + 1) * 10));
            KeyedMessage<byte[], byte[]> keyedMessage = new KeyedMessage<byte[], byte[]>(topic, UUID.randomUUID().toString().getBytes(), JsonUtil.toByteJson(logPackage));
            res.add(keyedMessage);
        }
        LogPackage logPackage = new LogPackage(list.subList(pieces * 10, list.size()));
        KeyedMessage<byte[], byte[]> keyedMessage = new KeyedMessage<byte[], byte[]>(topic, UUID.randomUUID().toString().getBytes(), JsonUtil.toByteJson(logPackage));
        res.add(keyedMessage);
        return res;
    }

    private void initProducer() {
        Properties producerProps = new Properties();
        producerProps.put("zookeeper.connect", "10.8.73.187:2181/kafka");
        producerProps.put("zk.connectiontimeout.ms", "1000000");
        producerProps.put("metadata.broker.list", "10.45.11.84:9092");
        producerProps.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        producerProps.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        kafka080ProduceService.initProducer(producerProps);
    }


}
