package com.nouuid.mysqlbench;

import com.nouuid.mysqlbench.local.BenchConfig;
import com.nouuid.mysqlbench.local.MysqlBench;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class MysqlBenchTest {
    MysqlBench mysqlBench;

    @org.junit.Before
    public void before() {
        String server = "10.45.11.85:3306";
        BenchConfig benchConfig = new BenchConfig();
        benchConfig.setServer(server);
        benchConfig.setUsername("root");
        benchConfig.setPwd("MyNewPass4!");
        benchConfig.setTableRowSize(50000);
        benchConfig.setThreadNum(200);
        benchConfig.setColNum(18);
        benchConfig.setTableNum(1);
        benchConfig.setTableName("testsync");
        benchConfig.setDuration(5*60*1000);
        mysqlBench = new MysqlBench(benchConfig);
    }

    @org.junit.Test
    public void prepare() throws Exception {
        mysqlBench.prepare();
    }

    @org.junit.Test
    public void run() throws Exception {
        mysqlBench.run();
    }

    @org.junit.Test
    public void cleanup() throws Exception {
        mysqlBench.cleanup();
    }
}
