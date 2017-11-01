package com.jengine.data.mq.kafka.kafkaConsumerMonitor.sink;

import com.jengine.data.mq.kafka.kafkaConsumerMonitor.Metric;

import java.util.List;

/**
 * Created by weiyang on 10/30/17.
 */
public interface Sink {

    void save(Metric metric);
    void save(List<Metric> metrics);
}
