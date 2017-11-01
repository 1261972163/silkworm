/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.data.mq.kafka.monitor;

import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

import kafka.admin.AdminClient;
import kafka.coordinator.GroupOverview;
import scala.collection.JavaConversions;
import scala.collection.immutable.List;

/**
 * @author bl07637
 */
public class AdminDemo {
  public static void main(String[] args) {
    String bootstrapServers = "10.45.4.95:9092,10.45.4.96:9092";
//    Properties consumerProps = new Properties();
//    consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//    consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
//    consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//    consumerProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
//    consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//    KafkaConsumer kafkaMetricConsumer = new KafkaConsumer(consumerProps);
//    Map<String, Metric> map = kafkaMetricConsumer.metrics();
//    for (String key : map.keySet()) {
//      System.out.println("key=" + key + ", " + map.get(key));
//    }

    AdminClient client = AdminClient.createSimplePlaintext(bootstrapServers);
    Map<Node,List<GroupOverview>> map = JavaConversions.asJavaMap(client.listAllConsumerGroups());
    for (List<kafka.coordinator.GroupOverview> groupOverviewList : map.values()) {
      for (GroupOverview groupOverview : JavaConversions.asJavaList(groupOverviewList)) {
//        System.out.println("-----" + groupOverview.groupId());
        List<AdminClient.ConsumerSummary> consumerSummaryList = client.describeConsumerGroup(groupOverview.groupId());
        for (AdminClient.ConsumerSummary consumerSummary : JavaConversions.asJavaList(consumerSummaryList)) {
          List<TopicPartition>  topicPartitionList = consumerSummary.assignment();
          for (TopicPartition topicPartition : JavaConversions.asJavaList(topicPartitionList)) {
            System.out.println(topicPartition + ", " + groupOverview.groupId() + "," + consumerSummary.memberId() + ":" + consumerSummary.clientHost());
          }
        }
      }
    }
  }
}
