package com.nouuid.mysqlbench;

import com.nouuid.mysqlbench.jta.BenchConfig;
import com.nouuid.mysqlbench.jta.MysqlJtaBench;

/**
 * @author nouuid
 * @date 7/14/2016
 * @description
 */
public class MysqlJtaBenchTest {
    MysqlJtaBench mysqlJtaBench;

    @org.junit.Before
    public void before() {
        String server = "10.45.11.84:3306";
        String server2 = "10.45.11.85:3306";
        BenchConfig benchConfig = new BenchConfig();
        benchConfig.setServer(server);
        benchConfig.setUsername("root");
        benchConfig.setPwd("MyNewPass4!");
        benchConfig.setServer2(server2);
        benchConfig.setUsername2("root");
        benchConfig.setPwd2("MyNewPass4!");
        benchConfig.setTableSize(10000);
        benchConfig.setThreadNum(10);
        benchConfig.setColNum(2);
        benchConfig.setDuration(60 * 1000);
        benchConfig.setType("crossSelect");
        mysqlJtaBench = new MysqlJtaBench(benchConfig);
    }

    @org.junit.Test
    public void prepare() throws Exception {
        mysqlJtaBench.prepare();
    }

    @org.junit.Test
    public void run() throws Exception {
        mysqlJtaBench.run();
    }

    @org.junit.Test
    public void cleanup() throws Exception {
        mysqlJtaBench.cleanup();
    }
}
