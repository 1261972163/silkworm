package com.jengine.data.mq.kafka;

import java.util.Properties;

/**
 * content
 *
 * @author nouuid
 * @date 6/5/2017
 * @since 0.1.0
 */
public class ConsumerConfig {

  private Properties consumerProperties = null;
  private String topic = null;
  private int partition = -1;
  private boolean autoConsume = true;

  public Properties getConsumerProperties() {
    return consumerProperties;
  }

  public void setConsumerProperties(Properties consumerProperties) {
    this.consumerProperties = consumerProperties;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public int getPartition() {
    return partition;
  }

  public void setPartition(int partition) {
    this.partition = partition;
  }

  public boolean isAutoConsume() {
    return autoConsume;
  }

  public void setAutoConsume(boolean autoConsume) {
    this.autoConsume = autoConsume;
  }
}
