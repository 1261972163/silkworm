package com.jengine.data.mq.kafka.kafkaConsumerMonitor.sink;

import com.jengine.data.mq.kafka.kafkaConsumerMonitor.Metric;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by weiyang on 10/30/17.
 */
public class InfluxdbSink implements Sink {
    private InfluxDB influxDB;
    private AtomicInteger batchPointCount = new AtomicInteger(0);
    private String dbName;

    private ReentrantLock batchLock = new ReentrantLock();

    public InfluxdbSink() {
        String url = "http://10.45.11.84:8086";
        String user = "root";
        String pwd = "root";
        influxDB = InfluxDBFactory.connect(url, user, pwd);
        dbName = "aTimeSeries";
        influxDB.createDatabase(dbName);
    }

    @Override
    public void save(Metric metric) {
        Point point = toPoint(metric);
        influxDB.write(point);
    }

    @Override
    public void save(List<Metric> metrics) {
        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
//                .retentionPolicy(rpName)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        for (Metric metric : metrics) {
            batchPoints.point(toPoint(metric));
        }
        influxDB.write(batchPoints);
    }

    private Point toPoint(Metric metric) {
        Point point = Point.measurement("KafkaConsumerMonitor")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("group", metric.getGroup())
                .tag("topic", metric.getTopic())
                .tag("partition", metric.getPartition()+"")
                .tag("owner", metric.getOwner())
                .addField("log", metric.getLogSize())
                .addField("offset", metric.getConsumerOffset())
                .addField("lag", metric.getLag())
                .build();
        return point;
    }

}
