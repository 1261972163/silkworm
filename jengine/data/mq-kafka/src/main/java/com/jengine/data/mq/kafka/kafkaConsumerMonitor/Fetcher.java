package com.jengine.data.mq.kafka.kafkaConsumerMonitor;

import com.jengine.common.utils.StringUtils;
import com.jengine.data.mq.kafka.kafkaConsumerMonitor.sink.Sink;
import kafka.admin.AdminClient;
import kafka.coordinator.GroupOverview;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import scala.collection.JavaConversions;
import scala.collection.immutable.List;

import java.util.Map;
import java.util.Properties;

/**
 * Created by weiyang on 10/29/17.
 */
public class Fetcher {
    private String bootstrapServers;
    private KafkaConsumer kafkaMetricConsumer;
    private OffsetCache kafkaMonitorOffsetCache;
    private Sink sink;

    public Fetcher(String bootstrapServers, OffsetCache kafkaMonitorOffsetCache, Sink sink) {
        this.bootstrapServers = bootstrapServers;
        this.kafkaMonitorOffsetCache = kafkaMonitorOffsetCache;
        this.sink = sink;
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaMetricConsumer = new KafkaConsumer(consumerProps);
    }

    public void fetch() {
        generateConsumerMetric();
    }

    private void generateConsumerMetric() {
        AdminClient client = AdminClient.createSimplePlaintext(bootstrapServers);
        Map<Node,List<GroupOverview>> map = JavaConversions.asJavaMap(client.listAllConsumerGroups());
        for (List<kafka.coordinator.GroupOverview> groupOverviewList : map.values()) {
            for (GroupOverview groupOverview : JavaConversions.asJavaList(groupOverviewList)) {
                System.out.println("-----" + groupOverview.groupId());
                List<AdminClient.ConsumerSummary> consumerSummaryList = client.describeConsumerGroup(groupOverview.groupId());
                for (AdminClient.ConsumerSummary consumerSummary : JavaConversions.asJavaList(consumerSummaryList)) {
                    List<TopicPartition>  topicPartitionList = consumerSummary.assignment();
                    kafkaMetricConsumer.assign(JavaConversions.asJavaList(topicPartitionList));
                    kafkaMetricConsumer.seekToEnd(JavaConversions.asJavaList(topicPartitionList));
                    for (TopicPartition topicPartition : JavaConversions.asJavaList(topicPartitionList)) {
                        Metric metric = metric(topicPartition, groupOverview.groupId(), consumerSummary.memberId());
//                        sink.save(metric);
                    }
                }
            }
        }
    }

    private Metric metric(TopicPartition topicPartition, String group, String consumerId) {
        String topic = topicPartition.topic();
        int partition = topicPartition.partition();
        String key = "[" + group + "," + topic + "," + partition + "]";
        long logSize = kafkaMetricConsumer.position(topicPartition);
        String consumerOffsetStr = kafkaMonitorOffsetCache.getOffsetCahe().get(key);
        long offset = StringUtils.isBlank(consumerOffsetStr) ? 0l : Long.parseLong(consumerOffsetStr);
        long lagSize = logSize - offset;
        String owner = consumerId;
        Metric metric = buildMetric(group, topic, partition, logSize, offset, lagSize, owner);
        return metric;
    }

    private Metric buildMetric(String group, String topic, int partition, long logSize, long consumerOffset,
                               long lag, String owner) {
        Metric metric = new Metric();
        metric.setGroup(group);
        metric.setTopic(topic);
        metric.setPartition(partition);
        metric.setLogSize(logSize);
        metric.setConsumerOffset(consumerOffset);
        metric.setLag(lag);
        metric.setOwner(owner);
        return metric;
    }
}
