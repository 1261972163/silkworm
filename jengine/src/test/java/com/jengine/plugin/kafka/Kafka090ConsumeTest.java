package com.jengine.plugin.kafka;

import com.jengine.feature.serialize.json.JsonUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author bl07637
 * @date 5/26/2016
 * @description
 */
public class Kafka090ConsumeTest {
    private Kafka090ConsumeService kafka090ConsumeService = new Kafka090ConsumeService();
    private int pn = 1;
    private static long max = 1000*10000L;
    private static volatile boolean flag = true;
    private AtomicLong count = new AtomicLong(0);
    final CountDownLatch startGate = new CountDownLatch(1);
    final CountDownLatch endGate = new CountDownLatch(pn);

    private String topic = "topic1";
    private String groupId = "test1";

    public static void main(String[] args) {
        Kafka090ConsumeTest kafkaConsumerMain = new Kafka090ConsumeTest();
        kafkaConsumerMain.run();
    }

    public void run() {
        initConsumer090();
        Thread[] consumer = new Thread[pn];
        for (int i=0; i< pn; i++) {
            consumer[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        consume();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        endGate.countDown();
                    }
                }
            });
            consumer[i].start();
        }

        try {
            long start = System.currentTimeMillis();
            startGate.countDown();
            endGate.await();
            long end = System.currentTimeMillis();
            long cost = end - start;
            System.out.println("num:" + count.longValue() + ", cost:" + cost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end---------------------------------------");
    }

    public void consume() throws InterruptedException {
        startGate.await();
        while (true) {
            ConsumerRecords<byte[], byte[]> consumerRecords = kafka090ConsumeService.poll090(1);
            for (ConsumerRecord<byte[], byte[]> record : consumerRecords) {
                LogPackage logPackage = JsonUtil.formByteJson(LogPackage.class, record.value());
                System.out.printf("offset = %d, key = %s", record.offset(), record.key());
                if (logPackage == null) {
                    continue;
                }
                System.out.print("consume:");
                for (MonitorLog monitorLog : logPackage.getList()) {
                    System.out.print("" + monitorLog.getAppId() + " ");
                }
                System.out.println("");
            }
            System.out.println("----------------------------------------- consume over");
            Thread.sleep(1000);
        }
    }

    private void initConsumer090() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "10.8.73.187:2181/kafka");
        props.put("bootstrap.servers", "10.45.11.84:9092");
        props.put("group.id", groupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.offset.reset","earliest");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put("topic", topic);
        kafka090ConsumeService.initConsumer090(props, topic);
    }
}
