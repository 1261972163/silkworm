package com.jengine.store.mq.kafka;

import com.jengine.serializer.json.JsonUtil;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author bl07637
 * @date 1/9/2016
 * @description
 */
public class Kafka090ClientTest {
    protected final Log logger = LogFactory.getLog(getClass());

    private Kafka090ProducerService kafka090ProducerService = null;
    private Kafka090ConsumeService kafka090ConsumeService = null;

    private volatile boolean flag = true;
    private String topic = "localtest";
//    private String topic = "xingcore";
    private static int consumerNum = 1;

    final CountDownLatch startGate = new CountDownLatch(1);
    final CountDownLatch endGate = new CountDownLatch(consumerNum);
    AtomicLong counter = new AtomicLong();

    private Properties getProducerProperties() {
        Properties properties = new Properties();
//        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("bootstrap.servers", "10.45.11.149:9092,10.45.11.150:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        properties.put("buffer.memory", 1024000); //1M
        properties.put("compression.type", "snappy");
        properties.put("retries", 3);
        properties.put("batch.size", 102400); //100k
//        properties.put("client.id", );
//        properties.put("max.block.ms", 60000); //60s
        properties.put("max.request.size", 1024000); //1M
//        properties.put("receive.buffer.bytes", );
//        properties.put("request.timeout.ms", );
        properties.put("block.on.buffer.full", true);
        return properties;
    }

    private Properties getConsumerProperties() {
        Properties properties = new Properties();
//        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("bootstrap.servers", "10.45.11.149:9092,10.45.11.150:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
//        properties.put("fetch.min.bytes", );
        properties.put("group.id", "localtest1");
        properties.put("heartbeat.interval.ms", "20000");
//        properties.put("max.partition.fetch.bytes", "3145728");//3M
        properties.put("max.partition.fetch.bytes", "10000");//3M
        properties.put("request.timeout.ms", "60000");
        properties.put("session.timeout.ms", "30000");
        properties.put("group.max.session.timeout.ms", "60000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("enable.auto.commit", "false");
//        properties.put("max.poll.records", "1"); //0.10.0版本可用
//        properties.put("partition.assignment.strategy", );
//        properties.put("receive.buffer.bytes", );
//        properties.put("request.timeout.ms", );
//        properties.put("send.buffer.bytes", );
//        properties.put("client.id", );
//        properties.put("fetch.max.wait.ms", );
        return properties;
    }


    @org.junit.Before
    public void before() throws Exception {
        kafka090ProducerService = new Kafka090ProducerService(getProducerProperties());
        kafka090ConsumeService = new Kafka090ConsumeService(getConsumerProperties(), topic, consumerNum);
    }

    @org.junit.After
    public void after() throws Exception {
        flag = false;
        kafka090ProducerService.close();
        kafka090ConsumeService.close();
    }

    @org.junit.Test
    public void produce() throws InterruptedException {
        int i = 0;
//        while(i<1000){
        while(true){
            Message message = new Message();
            message.setContent(i + "");
            ProducerRecord<byte[], byte[]> producerRecord = new ProducerRecord<byte[], byte[]>(topic, UUID.randomUUID().toString().getBytes(), JsonUtil.toByteJson(message));
            kafka090ProducerService.send(producerRecord);
            Thread.sleep(10);
            System.out.println("put " + i++);
        }
//        Thread.sleep(10*1000);
    }

    @org.junit.Test
    public void consume() throws InterruptedException {
        CopyOnWriteArraySet<KafkaConsumer<byte[], byte[]>> consumers = kafka090ConsumeService.getConsumers();
        KafkaConsumer<byte[], byte[]> consumer = consumers.iterator().next();
        int i = 0;
        while (flag) {
            ConsumerRecords<byte[], byte[]> consumerRecords = consumer.poll(100);
            for (ConsumerRecord<byte[], byte[]> record : consumerRecords) {
                if (record.value() == null) {
                    continue;
                }
                Message message = JsonUtil.formByteJson(Message.class, record.value());
                System.out.println("consume:" + message.getContent() + "..");
            }
            if (consumerRecords.count()>0) {
                consumer.commitSync();
            }
            System.out.println("loop-" + i);
            Thread.sleep(1000);
        }
    }

    /**
     * 分区提交
     * @throws InterruptedException
     */
    @org.junit.Test
    public void consume2() throws InterruptedException {
        CopyOnWriteArraySet<KafkaConsumer<byte[], byte[]>> consumers = kafka090ConsumeService.getConsumers();
        KafkaConsumer<byte[], byte[]> consumer = consumers.iterator().next();
        int i = 0;
        while (flag) {
            ConsumerRecords<byte[], byte[]> consumerRecords = consumer.poll(100);
            for (TopicPartition topicPartition : consumerRecords.partitions()) {
                for (ConsumerRecord<byte[], byte[]> record : consumerRecords.records(topicPartition)) {
                    if (record.value() == null) {
                        continue;
                    }
                    Message message = JsonUtil.formByteJson(Message.class, record.value());
                    System.out.println("consume:" + message.getContent() + "..");
//                    Collections.frequency(new HashMap<TopicPartition, OffsetAndMetadata>());
                    consumer.commitSync(Collections.singletonMap(new TopicPartition(topic,record.partition()), new OffsetAndMetadata(record.offset()+1)));
                }
            }
            consumer.commitSync();
            System.out.println("loop-" + i);
            Thread.sleep(1000);
        }
    }

    @org.junit.Test
    public void test() throws InterruptedException {
        while (counter.longValue() < 1000) {
            Message message = new Message();
            message.setContent(counter.longValue() + "");
            ProducerRecord<byte[], byte[]> producerRecord = new ProducerRecord<byte[], byte[]>(topic, UUID.randomUUID().toString().getBytes(), JsonUtil.toByteJson(message));
            kafka090ProducerService.send(producerRecord);
            System.out.println("put " + counter.longValue());
            counter.incrementAndGet();
        }

        Thread.sleep(10 * 1000);

        // consume
        CopyOnWriteArraySet<KafkaConsumer<byte[], byte[]>> consumers = kafka090ConsumeService.getConsumers();
        Iterator<KafkaConsumer<byte[], byte[]>> iterator = consumers.iterator();
        while (iterator.hasNext()) {
            KafkaConsumer<byte[], byte[]> consumer = iterator.next();
            Thread consumeThread = new Thread(new ConsumeTask(consumer));
            consumeThread.start();
        }
        startGate.countDown();
        endGate.await();
        Assert.assertEquals(0l, counter.longValue());
    }

    class ConsumeTask implements Runnable {
        private KafkaConsumer<byte[], byte[]> consumer;

        public ConsumeTask(KafkaConsumer<byte[], byte[]> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void run() {
            try {
                startGate.await();
                logger.info("Kafka " + Thread.currentThread().getName() + " running...");
                try {
                    int i=0;
                    while (i<50) {
                        ConsumerRecords<byte[], byte[]> consumerRecords = consumer.poll(100);
                        for (ConsumerRecord<byte[], byte[]> record : consumerRecords) {
                            if (record.value() == null) {
                                continue;
                            }
                            Message message = JsonUtil.formByteJson(Message.class, record.value());
                            counter.decrementAndGet();
                            System.out.println(Thread.currentThread().getName() + " consume:" + message.getContent() + "..");
                        }
                        if (consumerRecords.count() > 0) {
                            consumer.commitSync();
                        }
                        System.out.println("loop-" + i);
                        Thread.sleep(1000);
                        i++;
                    }
                } catch (Exception e) {
                    logger.error("", e);
                }
                Thread.sleep(10 * 1000);
                endGate.countDown();
                logger.info("Kafka " + Thread.currentThread().getName() + " finished.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
