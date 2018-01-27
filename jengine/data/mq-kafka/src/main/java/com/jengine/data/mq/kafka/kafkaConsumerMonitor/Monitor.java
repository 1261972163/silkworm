package com.jengine.data.mq.kafka.kafkaConsumerMonitor;

import com.jengine.data.mq.kafka.kafkaConsumerMonitor.cache.Cache;
import com.jengine.data.mq.kafka.kafkaConsumerMonitor.sink.InfluxdbSink;
import com.jengine.data.mq.kafka.kafkaConsumerMonitor.sink.PrintSink;
import com.jengine.data.mq.kafka.kafkaConsumerMonitor.sink.Sink;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by nouuid on 10/29/17.
 */
public class Monitor {
    private static final Logger logger = LoggerFactory.getLogger(Monitor.class);

    private String bootstrapServers;
    private int interval;
    private Fetcher fetcher;
    private Sink sink;

    private CountDownLatch mainCountDownLatch = new CountDownLatch(1);

    public static Cache cache = null;
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {
        String bootstrapServers = System.getProperty("kfkhosts", "127.0.0.1:9092");//10.45.4.95:9092,10.45.4.96:9092
        String intervalStr = System.getProperty("interval", "5000");
        String ifbhosts = System.getProperty("ifbhosts");//http://10.45.11.84:8086
        String ifbuser = System.getProperty("ifbuser", "root");
        String ifbpwd = System.getProperty("ifbpwd", "root");
        String ifbdb = System.getProperty("ifbdb", "kafka");//aTimeSeries
        int interval = 5000;
        try {
            interval = Integer.parseInt(intervalStr);
        } catch (Exception e) {
            logger.error("interval is not integer, use default value 5000(ms)", e);
        }
        Monitor monitor = new Monitor(bootstrapServers, interval, ifbhosts, ifbuser, ifbpwd, ifbdb);
        monitor.start();
    }

    public Monitor(String bootstrapServers, int interval, String ifbhosts, String ifbuser, String ifbpwd, String ifbdb) {
        this.bootstrapServers = bootstrapServers;
        this.interval = interval;
        cache = Cache.getInstance(bootstrapServers);
        if (StringUtils.isNotBlank(ifbhosts) && StringUtils.isNotBlank(ifbdb)) {
            sink = new InfluxdbSink(ifbhosts, ifbuser, ifbpwd, ifbdb);
        } else {
            sink = new PrintSink();
        }
        fetcher = new Fetcher(bootstrapServers, sink);
    }

    public void info() {
        logger.info("info=[bootstrapServers=" + bootstrapServers + ",interval=" + interval);
    }

    public void start() {
        logger.info("Monitor is started...");
        cache.start();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info("fetch at " + System.currentTimeMillis());
                fetcher.fetch();
            }
        }, 0, interval, TimeUnit.MILLISECONDS);
        try {
            mainCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Monitor is stoped.");
    }

    public void stop() {
        cache.stop();
        scheduledExecutorService.shutdownNow();
        mainCountDownLatch.countDown();
    }


}
