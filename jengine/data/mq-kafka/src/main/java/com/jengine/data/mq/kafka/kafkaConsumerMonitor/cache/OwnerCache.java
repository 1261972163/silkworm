/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.data.mq.kafka.kafkaConsumerMonitor.cache;

import com.google.common.collect.MapMaker;

import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import kafka.admin.AdminClient;
import kafka.coordinator.GroupOverview;
import scala.collection.JavaConversions;
import scala.collection.immutable.List;

/**
 * @author bl07637
 */
class OwnerCache {
  private AdminClient client;
  private Map<String, String> partitionOwnerMap = new MapMaker().makeMap();
  private Lock partitionOwnerMapLock = new ReentrantLock();
  private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
  private int refreshInteval = 3000;
  private boolean first = true;

  protected OwnerCache(String bootstrapServers, int refreshInteval) {
    this.refreshInteval = refreshInteval;
    client = AdminClient.createSimplePlaintext(bootstrapServers);
  }

  protected void start() {
    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        refreshPartitionOwnerMap();
      }
    }, 0, refreshInteval, TimeUnit.MILLISECONDS);
  }

  private void refreshPartitionOwnerMap() {
    try {
      partitionOwnerMapLock.lock();
      partitionOwnerMap.clear();
      Map<Node, List<GroupOverview>> map = JavaConversions.asJavaMap(client.listAllConsumerGroups());
      for (List<kafka.coordinator.GroupOverview> groupOverviewList : map.values()) {
        for (GroupOverview groupOverview : JavaConversions.asJavaList(groupOverviewList)) {
          List<AdminClient.ConsumerSummary> consumerSummaryList = client.describeConsumerGroup(groupOverview.groupId());
          for (AdminClient.ConsumerSummary consumerSummary : JavaConversions.asJavaList(consumerSummaryList)) {
            List<TopicPartition> topicPartitionList = consumerSummary.assignment();
            for (TopicPartition topicPartition : JavaConversions.asJavaList(topicPartitionList)) {
              String key = CacheUtils.buildKey(groupOverview.groupId(), topicPartition.topic(), topicPartition.partition() + "");
              String owner = consumerSummary.memberId() + ":" + consumerSummary.clientHost();
              partitionOwnerMap.put(key, owner);
            }
          }
        }
      }
    } finally {
      partitionOwnerMapLock.unlock();
    }
    if(first) {
      Cache.ownerCacheCountDownLatch.countDown();
      first = false;
    }
  }

  protected void stop() {
    scheduledExecutorService.shutdownNow();
    if (client != null) {
      client.close();
    }
  }

  protected String getConumserOwner(String key) {
    try {
      partitionOwnerMapLock.lock();
      return partitionOwnerMap.get(key);
    } finally {
      partitionOwnerMapLock.unlock();
    }
  }
}
