package com.jengine.data.mq.kafka.kafkaConsumerMonitor;

import com.jengine.data.mq.kafka.kafkaConsumerMonitor.sink.PrintSink;
import com.jengine.data.mq.kafka.kafkaConsumerMonitor.sink.Sink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by weiyang on 10/29/17.
 */
public class Monitor {
    private static final Logger logger = LoggerFactory.getLogger(Monitor.class);

    private String bootstrapServers;
    private OffsetCache kafkaMonitorOffsetCache;
    private Fetcher consumerMetric;

    private CountDownLatch mainCountDownLatch = new CountDownLatch(1);


    public static void main(String[] args) {
        String bootstrapServers = "10.45.4.95:9092,10.45.4.96:9092";
        Monitor monitor = new Monitor(bootstrapServers);
        monitor.start();
    }

    public Monitor(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
        kafkaMonitorOffsetCache = OffsetCache.getInstance(bootstrapServers);
        Sink sink = new PrintSink();
        consumerMetric = new Fetcher(bootstrapServers, kafkaMonitorOffsetCache, sink);
    }


    public void start() {
//        CountDownLatch consumerMetricDownLatch = new CountDownLatch(1);
        CountDownLatch cacheCountDownLatch = new CountDownLatch(1);
        Thread consumerMetricThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cacheCountDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                for (int i=1; i<=10; i++) {
//                    System.out.println("roud-" + i + "==========================");
//                    consumerMetric.fetch();
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                consumerMetric.fetch();
            }
        });
        Thread consumeOffsetThread = new Thread(new Runnable() {
            @Override
            public void run() {
                kafkaMonitorOffsetCache.start(cacheCountDownLatch);
            }
        });
        consumerMetricThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumeOffsetThread.start();
        logger.info("Monitor is started...");
        try {
            mainCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        kafkaMonitorOffsetCache.stop();
        mainCountDownLatch.countDown();
    }


}
