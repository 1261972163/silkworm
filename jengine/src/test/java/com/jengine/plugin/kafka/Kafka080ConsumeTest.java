package com.jengine.plugin.kafka;

import com.jengine.feature.serialize.json.JsonUtil;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author bl07637
 * @date 5/26/2016
 * @description
 */
public class Kafka080ConsumeTest {
    private Kafka080ConsumeService kafka080ConsumeService = new Kafka080ConsumeService();
    private int pn = 1;
    private static long max = 1000*10000L;
    private static volatile boolean flag = true;
    private AtomicLong count = new AtomicLong(0);
    final CountDownLatch startGate = new CountDownLatch(1);
    final CountDownLatch endGate = new CountDownLatch(pn);

    private String topic = "topic1";
    private String groupId = "test1";

    public static void main(String[] args) {
        Kafka080ConsumeTest kafkaConsumerMain = new Kafka080ConsumeTest();
        kafkaConsumerMain.run();
    }

    public void run() {
        initConsumer080();
        Thread[] consumer = new Thread[pn];
        for (int i=0; i< pn; i++) {
            consumer[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        consume(topic);
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

    public void consume(String topic) throws InterruptedException {
        startGate.await();
        KafkaStream<byte[], byte[]> stream = kafka080ConsumeService.poll080(topic);
        ConsumerIterator<byte[], byte[]> iterator =  stream.iterator();
        int i = 1;
        while (hasNext(iterator)) {
            System.out.println("-----------------------------");
            MessageAndMetadata<byte[], byte[]> mam = iterator.next();
            if (mam.message() == null) {
                continue;
            }
            LogPackage logPackage = JsonUtil.formByteJson(LogPackage.class, mam.message());
            if (logPackage == null) {
                continue;
            }
            System.out.print("consume:");
            for (MonitorLog monitorLog : logPackage.getList()) {
                System.out.print("" + monitorLog.getAppId() + " ");
            }
            i++;
            System.out.println("");
        }
        System.out.println("----------------------------------------- consume over");
        Thread.sleep(10);
    }

    private boolean hasNext(ConsumerIterator<byte[], byte[]> iterator) {
        try {
            return iterator.hasNext();
        } catch (ConsumerTimeoutException e) {
            return false;
        }
    }

    private void initConsumer080() {
        Properties consumerProps = new Properties();
        consumerProps.put("zookeeper.connect", "10.8.73.187:2181/kafka");
        consumerProps.put("metadata.broker.list", "10.45.11.84:9092");
        consumerProps.put("group.id", groupId);
        consumerProps.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        consumerProps.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        consumerProps.put("consumer.timeout.ms", "1000");
        consumerProps.put("enable.auto.commit", "true");
        consumerProps.put("auto.offset.reset","smallest");
        consumerProps.put("auto.commit.interval.ms", "1000");
        kafka080ConsumeService.initConsumer080(consumerProps);
    }
}
