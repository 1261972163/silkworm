package com.jengine.data.nosql.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by nouuid on 10/30/17.
 */
public class InfluxdbDemo {


  @Test
  public void test() {
    String url = "http://10.45.11.84:8086";
    String user = "root";
    String pwd = "root";
    InfluxDB influxDB = InfluxDBFactory.connect(url, user, pwd);
    String dbName = "aTimeSeries";
    influxDB.createDatabase(dbName);
    String rpName = "aRetentionPolicy";

    Query query = new Query("create retention policy \"" + rpName
        + "\" on \"" + dbName + "\" duration 30d30m replication 1",
        dbName);
    influxDB.query(query);


    influxDB.setRetentionPolicy(rpName);

//        influxDB.createRetentionPolicy(rpName, dbName, "30d", "30m", 2, true);
    BatchPoints batchPoints = BatchPoints
        .database(dbName)
        .tag("async", "true")
        .retentionPolicy(rpName)
        .consistency(InfluxDB.ConsistencyLevel.ALL)
        .build();
    Point point1 = Point.measurement("cpu")
        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
        .addField("idle", 90L)
        .addField("user", 9L)
        .addField("system", 1L)
        .build();
    Point point2 = Point.measurement("disk")
        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
        .addField("used", 80L)
        .addField("free", 1L)
        .build();
    batchPoints.point(point1);
    batchPoints.point(point2);
    influxDB.write(batchPoints);
  }

  @Test
  public void read() {
    String url = "http://10.45.11.86:8086";
    String user = "root";
    String pwd = "root";
    InfluxDB influxDB = InfluxDBFactory.connect(url, user, pwd);
//    String command = "SELECT * FROM virtual_memory_bytes WHERE time > now() - 60s and time < now()";
//    String database = "prometheus";
    String command = "SELECT * FROM elasticsearch_indices WHERE time > now() - 5s and time < now()";
    String database = "elasticsearch";
    Query query = new Query(command, database);
    QueryResult queryResult = influxDB.query(query);
    List<QueryResult.Result> results = queryResult.getResults();
    List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
    for (QueryResult.Result result : results) {
      List<QueryResult.Series> seriesList = result.getSeries();
      for (QueryResult.Series series : seriesList) {
        for (List<Object> valueList : series.getValues()) {
          Map<String, Object> record = new HashMap<String, Object>();
          for (int i = 0; i < valueList.size(); i++) {
            record.put(series.getColumns().get(i), valueList.get(i));
          }
          datas.add(record);
        }
      }
    }
    System.out.println("---");
  }
}
