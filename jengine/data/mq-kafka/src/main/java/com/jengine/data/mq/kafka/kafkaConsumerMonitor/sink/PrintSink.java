package com.jengine.data.mq.kafka.kafkaConsumerMonitor.sink;

import com.jengine.data.mq.kafka.kafkaConsumerMonitor.Metric;

import java.util.List;

/**
 * Created by weiyang on 10/30/17.
 */
public class PrintSink implements Sink {
    @Override
    public void save(Metric metric) {
        StringBuilder sb = new StringBuilder();
        sb.append(metric.getGroup()).append("\t");
        sb.append(metric.getTopic()).append("\t");
        sb.append(metric.getPartition()).append("\t\t\t");
        sb.append(metric.getLogSize()).append("\t\t");
        sb.append(metric.getConsumerOffset()).append("\t\t\t\t");
        sb.append(metric.getLag()).append("\t");
        sb.append(metric.getOwner());
        System.out.println(sb.toString());
    }

    @Override
    public void save(List<Metric> metrics) {
        System.out.println("group\t\ttopic\t\tpartition\tlogSize\tconsumerOffset\tlag\towner");
        for (Metric metric : metrics) {
            save(metric);
        }
    }

}
