package com.jengine.data.mq.kafka.kafkaConsumerMonitor.sink;

import com.jengine.common.javacommon.utils.StringUtils;
import com.jengine.data.mq.kafka.kafkaConsumerMonitor.Metric;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by nouuid on 10/30/17.
 */
public class InfluxdbSink implements Sink {
  private InfluxDB influxDB;
  private String dbName;

  public InfluxdbSink(String host, String user, String pwd, String db) {
    influxDB = InfluxDBFactory.connect(host, user, pwd);
    this.dbName = db;
    if (!influxDB.databaseExists(db)) {
      influxDB.createDatabase(db);
    }
  }

  public static void main(String[] args) {
    String url = "http://10.45.11.84:8086";
    String user = "root";
    String pwd = "root";
    String db = "test1";
    InfluxdbSink influxdbSink = new InfluxdbSink(url, user, pwd, db);
    Metric metric = new Metric();
    metric.setGroup("1");
    metric.setTopic("2");
    metric.setPartition(0);
    metric.setLogSize(100);
    metric.setConsumerOffset(10);
    metric.setLag(90);
//    metric.setOwner("3");
    influxdbSink.save(metric);
    System.out.printf("----");
  }

  @Override
  public void save(Metric metric) {
    BatchPoints batchPoints = BatchPoints
            .database(dbName)
//                .retentionPolicy(rpName)
            .consistency(InfluxDB.ConsistencyLevel.ALL)
            .build();
    Point point = toPoint(metric);
    batchPoints.point(point);
    influxDB.write(batchPoints);
  }

  @Override
  public void save(List<Metric> metrics) {
    BatchPoints batchPoints = BatchPoints
        .database(dbName)
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
        .tag("partition", metric.getPartition() + "")
        .tag("owner", StringUtils.isBlank(metric.getOwner()) ? "NULL" : metric.getOwner())
        .addField("log", metric.getLogSize())
        .addField("offset", metric.getConsumerOffset())
        .addField("lag", metric.getLag())
        .build();
    return point;
  }

}
