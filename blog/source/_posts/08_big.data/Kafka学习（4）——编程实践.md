Kafka学习（4）——编程实践
========================

# 1. 基于0.8的生产者和消费者

maven依赖：

    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka-clients</artifactId>
        <version>0.9.0.1</version>
    </dependency>
    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka_2.10</artifactId>
        <version>0.9.0.1</version>
    </dependency>


## 1.1 消息生产

    package com.best.kafka.test;
    
    import com.best.xingng.cxf.databinding.json.util.JsonUtil;
    import kafka.producer.KeyedMessage;
    import kafka.producer.ProducerConfig;
    
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Properties;
    import java.util.UUID;
    
    
    public class LogProcessProducer {
    
        private static String KAFKA_ZOOKEEPER_CONNECT = "127.0.0.1:2181";
        private static String KAFKA_METADATA_BROKER_LIST = "127.0.0.1:9092";
        private static String KAFKA_ZK_CONNECTION_TIMEOUT_MS = "1000000";
        private static String TOPIC = "test1";
    
        public void test() {
            //初始化producer
            Properties producerProps = new Properties();
            producerProps.put("zookeeper.connect", KAFKA_ZOOKEEPER_CONNECT);
            producerProps.put("zk.connectiontimeout.ms", KAFKA_ZK_CONNECTION_TIMEOUT_MS);
            producerProps.put("metadata.broker.list", KAFKA_METADATA_BROKER_LIST);
            producerProps.put("key.serializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
            producerProps.put("value.serializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
            kafka.javaapi.producer.Producer<byte[], byte[]> producer = new kafka.javaapi.producer.Producer<byte[], byte[]>(new ProducerConfig(producerProps));
    
            //封装数据
            List<KeyedMessage<byte[], byte[]>> keyedMessages = new ArrayList<KeyedMessage<byte[], byte[]>>();
            for (int i=1; i<=100; i++) {
                String s = "test"; //此处数据类型为String
                KeyedMessage<byte[], byte[]> keyedMessage = new KeyedMessage<byte[], byte[]>(TOPIC, UUID.randomUUID().toString().getBytes(), SerializerUtil.serializer(s));
                keyedMessages.add(keyedMessage);
            }
    
            //批量发送
            producer.send(keyedMessages);
        }
    }
    
    SerializerUtil一个自定义的序列化帮助类，实现对象的序列化和反序列化。

## 1.2 消息消费

一方面，kafka认为0.9.*版本的consumer还不够完善，所以推荐使用0.9.*之前的consumer。

另一方面，0.9.*版本不再在zookeeper上维护consumer的offset，而是维护在broker上。

这对旧的kafka-manager来说影响较大，当前最新的kafka-manager-1.3.0.8已经支持broker上维护的offset，我们还未升级。

    package com.best.kafka.test;
    
    import kafka.consumer.Consumer;
    import kafka.consumer.ConsumerConfig;
    import kafka.consumer.ConsumerIterator;
    import kafka.consumer.KafkaStream;
    import kafka.message.MessageAndMetadata;
    
    import java.util.*;
    
    
    public class LogProcessConsumer {
    
        private static String KAFKA_ZOOKEEPER_CONNECT = "127.0.0.1:2181";
        private static String KAFKA_METADATA_BROKER_LIST = "127.0.0.1:9092";
        private static String KAFKA_FETCH_MAX_SIZE = (5*1024*1024) + "";
        private static String TOPIC = "test1";
        private static int PARTITION_NUM = 1;
    
        public void test() {
            //初始化consumer
            Properties consumerProps = new Properties();
            consumerProps.put("zookeeper.connect", KAFKA_ZOOKEEPER_CONNECT);
            consumerProps.put("metadata.broker.list", KAFKA_METADATA_BROKER_LIST);
            consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
            consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
            consumerProps.put("auto.commit.enable", "false");
            consumerProps.put("auto.offset.reset", "smallest");
            consumerProps.put("fetch.message.max.bytes", KAFKA_FETCH_MAX_SIZE);
            consumerProps.put("group.id", "test");
            kafka.javaapi.consumer.ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(consumerProps));
    
            //获取KafkaStream
            int num = 10; // partition数目
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            map.put(TOPIC, PARTITION_NUM);
            Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumerConnector.createMessageStreams(map);
            List<KafkaStream<byte[], byte[]>> kafkaStreamList = streamMap.get(TOPIC);
    
            //获取KafkaStream的iterator
            List<ConsumerIterator<byte[], byte[]>> consumerIterators = new ArrayList<ConsumerIterator<byte[], byte[]>>();
            for (final KafkaStream<byte[], byte[]> stream : kafkaStreamList) {
                ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
                consumerIterators.add(iterator);
            }
    
            // 取数据
            for (final ConsumerIterator<byte[], byte[]> iterator : consumerIterators) {
                while (iterator.hasNext()) {
                    MessageAndMetadata<byte[], byte[]> mam = iterator.next();
                    if (mam.message() == null) {
                        continue;
                    }
                    String s = (String) SerializerUtil.deserializer(mam.message());
                    System.out.println(s);
                    consumerConnector.commitOffsets();
                }
            }
        }
    }

* KafkaStream。KafkaStream是kafka的consumer，KafkaStream的数量应和partition的数量保持一致，一个partition对应一个KafkaStream，超过partition数量的KafkaStream其实一直阻塞着，并不会其作用。所以，要增加KafkaStream就必须相应增加partition。
* offset。offset是consumer消费位置的标志，提交offset表明消息被消费。自动提交方式，offset是在iterator.next()之后就会进行。手动提交方式，可以定制消息被后续处理流程处理后再提交，从而确保消息发送的exactly once。如上例，设置auto.commit.enable为false，在print之后手动提交consumerConnector.commitOffsets()，可以确保print每一条消息。
* 消费组。group.id决定了consumer所属的消费组，同一个消费组的consumer共同维护一个offset。
* 初始消费位置。auto.offset.reset决定了一个新的consumer（不同group）消费的位置，默认为largest，表示从最新的消息开始消费；smallest表示从最久的消息开始消费。
* 阻塞消费。默认情况下，未设置consumer.timeout.ms参数，ConsumerIterator.hasNext()是阻塞式的，有数据时，ConsumerIterator.hasNext()返回true，无数据时，程序卡在ConsumerIterator.hasNext()，直到有新的数据进来。如果设置consumer.timeout.ms，则ConsumerIterator.hasNext()是限时消费的，有数据的时候，ConsumerIterator.hasNext()返回true，无数据时，ConsumerIterator.hasNext()等待consumer.timeout.ms时间，如果仍无数据，则抛出ConsumerTimeoutException错误。
* 消息大小。在consumer端关注的是获取的单条消息大小，由fetch.message.max.bytes决定。取出的消息大小超过fetch.message.max.bytes就会报MessageSizeTooLargeException。取出的消息大小是由写入的消息大小决定的，topic的max.message.bytes（默认情况下使用Broker的message.max.bytes）是限制producer对该topic发送的消息大小。所以要求fetch.message.max.bytes大于topic的max.message.bytes。

# 2. 基于0.9的生产者和消费者


使用0.9客户端对kafka进行消息读写。

0.8.2对Producer进行了较大改变，发送更快。

0.9.0对Consumer进行了较大的改变，去除了0.8上高低版本consumer的区别，使用更加方便。同时offset不再维护到zk上，维护到了broker上，可以降低对zk的依赖。但目前只是beta版本。


maven依赖：

    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka-clients</artifactId>
        <version>0.9.0.1</version>
    </dependency>

API具体文档见[Producer API](http://kafka.apache.org/090/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html)和[Consumer API](http://kafka.apache.org/090/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html)

参数配置见[Producer Config](http://kafka.apache.org/090/documentation.html#producerconfigs)和[Consumer Config](http://kafka.apache.org/090/documentation.html#newconsumerconfigs)

## 2.1 消息生产

客户端加snappy压缩，需要确保snappy-java版本为1.1.1.7

## 2.2 消息消费

kafka会管理如何分配partition的消费。0.9的KafkaConsumer是一个批量消费。

（1）当某个consumer消费了数据，但是在heartbeat.interval.ms时间内没有commit，它就认为consumer挂了。这个时候就要reblance了。
（2）consumer每次poll时，取出max.partition.fetch.bytes大小的数据。

下面是一个简单的producer-consumser单元测试：

JsonUtil一个自定义的序列化帮助类，实现对象的序列化和反序列化。

    package com.best.kafka.test;
    
    import best.kafka.test.serialize.json.JsonUtil;
    import junit.framework.Assert;
    import org.apache.commons.logging.Log;
    import org.apache.commons.logging.LogFactory;
    import org.apache.kafka.clients.consumer.ConsumerRecord;
    import org.apache.kafka.clients.consumer.ConsumerRecords;
    import org.apache.kafka.clients.consumer.KafkaConsumer;
    import org.apache.kafka.clients.producer.ProducerRecord;
    
    import java.util.Iterator;
    import java.util.Properties;
    import java.util.UUID;
    import java.util.concurrent.CopyOnWriteArraySet;
    import java.util.concurrent.CountDownLatch;
    import java.util.concurrent.atomic.AtomicLong;
    
    /**
     * Kafka090ClientTest
     *
     * @author nouuid
     * @date 1/9/2016
     * @since
     */
    public class Kafka090ClientTest {
        protected final Log logger = LogFactory.getLog(getClass());
    
        private Kafka090ProducerService kafka090ProducerService = null;
        private Kafka090ConsumeService kafka090ConsumeService = null;
    
        private volatile boolean flag = true;
        private String topic = "localtest";
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
            properties.put("group.id", "localtestgroup");
            properties.put("heartbeat.interval.ms", "20000");
    //        properties.put("max.partition.fetch.bytes", "3145728");//3M
            properties.put("max.partition.fetch.bytes", "1000");//3M
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
            while(i<1000){
                Message message = new Message();
                message.setContent(i + "");
                ProducerRecord<byte[], byte[]> producerRecord = new ProducerRecord<byte[], byte[]>(topic, UUID.randomUUID().toString().getBytes(), JsonUtil.toByteJson(message));
                kafka090ProducerService.send(producerRecord);
                System.out.println("put " + i++);
            }
            Thread.sleep(10*1000);
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


**消息体**

    package com.best.kafka.test;
    
    /**
     * Message
     *
     * @author nouuid
     * @date 9/1/2016
     * @since
     */
    public class Message {
        private String content;
    
        public String getContent() {
            return content;
        }
    
        public void setContent(String content) {
            this.content = content;
        }
    }

**生产者producer**

    package com.best.kafka.test;
    
    import org.apache.commons.logging.Log;
    import org.apache.commons.logging.LogFactory;
    import org.apache.kafka.clients.producer.KafkaProducer;
    import org.apache.kafka.clients.producer.Producer;
    import org.apache.kafka.clients.producer.ProducerRecord;
    
    import java.util.Properties;
    
    /**
     * Kafka090ProducerService
     *
     * @author nouuid
     * @date 9/1/2016
     * @since
     */
    public class Kafka090ProducerService {
        protected final Log logger = LogFactory.getLog(getClass());
    
        private Producer<byte[], byte[]> producer;
    
        public Kafka090ProducerService(Properties properties) throws Exception {
            if (properties==null && properties.get("bootstrap.servers")==null
                    && properties.get("key.serializer")==null
                    && properties.get("value.serializer")==null) {
                throw new Exception("lack of properties");
            } else {
                producer = new KafkaProducer<byte[], byte[]>(properties);
            }
        }
    
        public void close() throws Exception {
            try {
                producer.close();
            } catch (Exception e) {
                logger.error("Close kafka producer or consumer Error!", e);
            }
            logger.info(" kafka has been shut down!");
        }
    
        public void send(ProducerRecord<byte[], byte[]> producerRecord) {
            if (producerRecord!=null) {
                producer.send(producerRecord);
            }
        }
    }

**消费者consumer**

    package com.best.kafka.test;
    
    import org.apache.commons.lang.StringUtils;
    import org.apache.commons.logging.Log;
    import org.apache.commons.logging.LogFactory;
    import org.apache.kafka.clients.consumer.KafkaConsumer;
    
    import java.util.Arrays;
    import java.util.Properties;
    import java.util.concurrent.CopyOnWriteArraySet;
    
    /**
     * Kafka090ConsumeService
     *
     * @author nouuid
     * @date 9/1/2016
     * @since
     */
    public class Kafka090ConsumeService {
        protected final Log logger = LogFactory.getLog(getClass());
    
        private CopyOnWriteArraySet<KafkaConsumer<byte[], byte[]>> consumers = new CopyOnWriteArraySet<KafkaConsumer<byte[], byte[]>>();
    
        public Kafka090ConsumeService(Properties properties, String topic, int consumerNum) throws Exception {
            if (properties==null && properties.get("bootstrap.servers")==null
                    && properties.get("key.serializer")==null
                    && properties.get("value.serializer")==null
                    && consumerNum<=0
                    && StringUtils.isNotBlank(topic)) {
                throw new Exception("lack of properties");
            } else {
                for (int i=0; i< consumerNum; i++) {
                    KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<byte[], byte[]>(properties);
                    consumer.subscribe(Arrays.asList(topic));
                    consumers.add(consumer);
                }
            }
        }
    
        public void close() throws Exception {
            try {
                if (consumers!=null && consumers.size()>0) {
                    for (KafkaConsumer<byte[], byte[]> consumer : consumers) {
                        if (consumer!=null) {
                            synchronized (consumer) {
                                if (consumer!=null) {
                                    consumer.close();
                                    consumer = null;
                                }
                            }
                        }
    
                    }
                }
            } catch (Exception e) {
                logger.error("Close kafka producer or consumer Error!", e);
            }
            logger.info(" kafka has been shut down!");
        }
    
        public CopyOnWriteArraySet<KafkaConsumer<byte[], byte[]>> getConsumers() {
            return consumers;
        }
    }








