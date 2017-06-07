package com.jengine.data.mq.kafka.kafkaMonitor;

import com.google.common.collect.MapMaker;
import kafka.common.OffsetAndMetadata;
import kafka.coordinator.GroupMetadataManager;
import kafka.coordinator.OffsetKey;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * content
 *
 * @author bl07637
 * @date 4/20/2017
 * @since 0.1.0
 */
public class KafkaMonitorOffsetCache {
    private static final Logger              logger        = LoggerFactory.getLogger(KafkaMonitor.class);
    private              KafkaConsumer       kafkaConsumer = null;
    private              Map<String, String> offsetCahe    = new MapMaker().makeMap();
    private volatile     boolean             active        = false;
    private volatile     boolean             hasRunning    = false;

    private static KafkaMonitorOffsetCache kafkaMonitorOffsetCache = null;

    private KafkaMonitorOffsetCache(String bootstrapServers) {
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "MyKafkaManagerOffsetCache");
        consumerProps.put("exclude.internal.topics", "false");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        long start = System.currentTimeMillis();
        kafkaConsumer = new KafkaConsumer(consumerProps);
        kafkaConsumer.subscribe(Arrays.asList("__consumer_offsets"));
        logger.info("init KafkaMonitorOffsetCache cost: " + (System.currentTimeMillis() - start));
    }

    public static KafkaMonitorOffsetCache getInstance(String bootstrapServers) {
        if (kafkaMonitorOffsetCache==null) {
            synchronized (KafkaMonitorOffsetCache.class) {
                if (kafkaMonitorOffsetCache==null) {
                    kafkaMonitorOffsetCache = new KafkaMonitorOffsetCache(bootstrapServers);
                }
            }
        }
        return kafkaMonitorOffsetCache;
    }

    public Map<String, String> getOffsetCahe() {
        return offsetCahe;
    }

    public void start() {
        if (active) {
            throw new RuntimeException("This KafkaMonitorOffsetCache is already active.");
        }
        active = true;
        logger.info("KafkaMonitorOffsetCache started.");
        ConsumerRecords<byte[], byte[]> consumerRecords = null;
        while (active) {
            hasRunning = true;
            consumerRecords = kafkaConsumer.poll(100);
            Iterator<ConsumerRecord<byte[], byte[]>> iterator = consumerRecords.iterator();
            while (iterator.hasNext()) {
                ConsumerRecord<byte[], byte[]> consumerRecord = iterator.next();
                if (consumerRecord.key()==null || consumerRecord.value()==null) {
                    continue;
                }
                try {
                    Object formattedKey = GroupMetadataManager.readMessageKey(ByteBuffer.wrap(consumerRecord.key()));
                    Object formattedValue = GroupMetadataManager.readOffsetMessageValue(ByteBuffer.wrap(consumerRecord.value()));
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
                        System.out.println(groupTopicPartition + " / " + offsetAndMetadataItems[2]);
                        offsetCahe.put(groupTopicPartition, offsetAndMetadataItems[2]);
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            kafkaConsumer.commitSync();
            hasRunning = false;
        }
    }

    public void stop() {
        active = false;
        while (hasRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
        kafkaConsumer.close();
    }
}
