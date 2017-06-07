package com.jengine.data.mq.kafka.kafkaMonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * content
 *
 * @author bl07637
 * @date 4/19/2017
 * @since 0.1.0
 */
public class KafkaMonitorDemo {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMonitorDemo.class);

    @org.junit.Test
    public void test1() throws InterruptedException {
        String bootstrapServers = "10.45.11.149:9092,10.45.11.150:9092";
        final KafkaMonitorOffsetCache kafkaMonitorOffsetCache = KafkaMonitorOffsetCache.getInstance(bootstrapServers);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                kafkaMonitorOffsetCache.start();
            }
        });
        thread.start();
        Thread.sleep(1000*30);
        kafkaMonitorOffsetCache.stop();
        System.out.println("=====================");
        for (String key : kafkaMonitorOffsetCache.getOffsetCahe().keySet()) {
            System.out.println(key + " -> " + kafkaMonitorOffsetCache.getOffsetCahe().get(key));
        }

        System.out.println("--------- end");
    }

    @org.junit.Test
    public void test2() throws InterruptedException {
        String bootstrapServers = "10.45.11.149:9092,10.45.11.150:9092";
        String group = "test";
        String topic = "xingngLogTest2";
        KafkaMonitor kafkaMonitor2 = new KafkaMonitor(bootstrapServers, group);
        logger.info("------------------ in sleeping.");
        Thread.sleep(1000 * 60);
        logger.info("------------------ out sleeping.");
        int[] partitions = {0, 1, 2, 3, 4, 5};
        List<OffsetInfo> offsetInfos = kafkaMonitor2.monitor(topic, partitions);
        kafkaMonitor2.close();
        printOffsetInfos(offsetInfos);
        System.out.println("=======================================");
    }

    public static void printOffsetInfo(OffsetInfo offsetInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append(offsetInfo.getTopic());
        sb.append("\t");
        sb.append(offsetInfo.getPartition());
        sb.append("\t\t\t");
        sb.append(offsetInfo.getLogSize());
        sb.append("\t\t");
        sb.append(offsetInfo.getConsumerOffset());
        sb.append("\t\t\t\t");
        sb.append(offsetInfo.getLag());
        sb.append("\t");
        sb.append(offsetInfo.getOwner());
        System.out.println(sb.toString());
    }

    public static void printOffsetInfos(List<OffsetInfo> offsetInfos) {
        System.out.println("topic\t\tpartition\tlogSize\tconsumerOffset\tlag\towner");
        for (OffsetInfo monitorInfo : offsetInfos) {
            StringBuilder sb = new StringBuilder();
            sb.append(monitorInfo.getTopic());
            sb.append("\t");
            sb.append(monitorInfo.getPartition());
            sb.append("\t\t\t");
            sb.append(monitorInfo.getLogSize());
            sb.append("\t\t");
            sb.append(monitorInfo.getConsumerOffset());
            sb.append("\t\t\t\t");
            sb.append(monitorInfo.getLag());
            sb.append("\t");
            sb.append(monitorInfo.getOwner());
            System.out.println(sb.toString());
        }
    }

}
