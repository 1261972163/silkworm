package com.jengine.data.mq.kafka.monitor;

import kafka.admin.AdminClient;
import kafka.common.OffsetAndMetadata;
import kafka.coordinator.GroupMetadataManager;
import kafka.coordinator.OffsetKey;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import scala.collection.JavaConversions;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.*;
import org.apache.kafka.common.Node;
import scala.collection.immutable.List;

/**
 * Created by nouuid on 10/28/17.
 */
public class KafkaTestDemo {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTestDemo.class);

    private boolean active = true;
    private KafkaConsumer kafkaConsumer = null;

    private KafkaTestDemo(String bootstrapServers) {
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "MyKafkaManagerOffsetCache");
        consumerProps.put("exclude.internal.topics", "false");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        long start = System.currentTimeMillis();
        kafkaConsumer = new KafkaConsumer(consumerProps);
        kafkaConsumer.subscribe(Arrays.asList("__consumer_offsets"));
        logger.info("init OffsetCache cost: " + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        String bootstrapServers = "10.45.4.95:9092,10.45.4.96:9092";
        KafkaTestDemo kafkaConsumerMetricToInfluxdb = new KafkaTestDemo(bootstrapServers);
//        kafkaConsumerMetricToInfluxdb.getMetric();
//        kafkaConsumerMetricToInfluxdb.test2(bootstrapServers);
        kafkaConsumerMetricToInfluxdb.test3(bootstrapServers);
    }

    private void test3(String bootstrapServers) {
        KafkaConsumer kafkaConsumer = initKafkaConsumer(bootstrapServers);
        Map<String, java.util.List<PartitionInfo>> x1 = kafkaConsumer.listTopics();
        for (String x2 : x1.keySet()) {
            for (PartitionInfo x3 : x1.get(x2)) {
                TopicPartition topicPartition = new TopicPartition(x2, x3.partition());
                kafkaConsumer.assign(Arrays.asList(new TopicPartition[]{topicPartition}));
                kafkaConsumer.seekToEnd(Arrays.asList(new TopicPartition[]{topicPartition}));
                long logSize = kafkaConsumer.position(topicPartition);
                System.out.printf("");
            }
        }
    }

    public void test2(String bootstrapServers) {
        AdminClient client = AdminClient.createSimplePlaintext(bootstrapServers);
        Map<Node,List<kafka.coordinator.GroupOverview>> map =
                JavaConversions.asJavaMap(client.listAllConsumerGroups());
        for (List<kafka.coordinator.GroupOverview> x1 : map.values()) {
            for (kafka.coordinator.GroupOverview x2 : JavaConversions.asJavaList(x1)) {
                scala.collection.immutable.List<kafka.admin.AdminClient.ConsumerSummary> x3=
                        client.describeConsumerGroup(x2.groupId());
                for (kafka.admin.AdminClient.ConsumerSummary x4 : JavaConversions.asJavaList(x3)) {
                    scala.collection.immutable.List<org.apache.kafka.common.TopicPartition>  x5 =
                            x4.assignment();
                    for (TopicPartition x6 : JavaConversions.asJavaList(x5)) {
                        long x7 = getLogSize(bootstrapServers, x6.topic(), x6.partition());
                        System.out.printf("");

                    }
                }
                System.out.printf("");
            }
        }
        System.out.printf("...");
    }



    public void getMetric() {
        ConsumerRecords<byte[], byte[]> consumerRecords = null;
        while (active) {
            consumerRecords = kafkaConsumer.poll(100);
            Iterator<ConsumerRecord<byte[], byte[]>> iterator = consumerRecords.iterator();
            while (iterator.hasNext()) {
                ConsumerRecord<byte[], byte[]> consumerRecord = iterator.next();
                if (consumerRecord.key() == null || consumerRecord.value() == null) {
                    continue;
                }
                try {
                    Object formattedKey = GroupMetadataManager
                            .readMessageKey(ByteBuffer.wrap(consumerRecord.key()));
                    Object formattedValue = GroupMetadataManager
                            .readOffsetMessageValue(ByteBuffer.wrap(consumerRecord.value()));
                    if (formattedKey instanceof OffsetKey && formattedValue instanceof OffsetAndMetadata) {
                        String groupTopicPartition = ((OffsetKey) formattedKey).toString();
                        String offsetAndMetadata = ((OffsetAndMetadata) formattedValue).toString();
                        String[] offsetAndMetadataItems = offsetAndMetadata.split("\\[|,|\\]");
                        if (offsetAndMetadataItems.length != 7) {
                            continue;
                        }
                        if (groupTopicPartition.startsWith("[MyKafkaManagerOffsetCache")) {
                            continue;
                        }
                        String[] groupTopicPartitionSplits = groupTopicPartition.substring(1, groupTopicPartition.length()-1)
                                .split(",");;
                        String cunsumerGroup = groupTopicPartitionSplits[0];
                        String topic = groupTopicPartitionSplits[0];
                        String partition = groupTopicPartitionSplits[0];
                        String offset = offsetAndMetadataItems[2];
//                        long logSize = getLogSize(kafkaConsumer, topic, Integer.parseInt(partition));
//                        long lag = logSize - Long.parseLong(offset);

                        // consumergroup, topic, partition, offset
//                        System.out.println(groupTopicPartition + " / " + offsetAndMetadataItems[2]);
                        System.out.println(groupTopicPartition + " / " + offsetAndMetadata);
//
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            kafkaConsumer.commitSync();
        }
    }

    private long getLogSize(String bootstrapServers, String topic, int partition) {
        KafkaConsumer kafkaConsumer = initKafkaConsumer(bootstrapServers);


        TopicPartition topicPartition = new TopicPartition(topic, partition);
        kafkaConsumer.assign(Arrays.asList(new TopicPartition[]{topicPartition}));
        kafkaConsumer.seekToEnd(Arrays.asList(new TopicPartition[]{topicPartition}));
        long logSize = kafkaConsumer.position(topicPartition);
        return logSize;
    }

    private KafkaConsumer initKafkaConsumer(String bootstrapServers) {
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerProps);
        return kafkaConsumer;
    }
}
