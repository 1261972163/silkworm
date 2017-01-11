package com.nouuid.mysqlbench;


import com.nouuid.mysqlbench.cross.BenchConfig;
import com.nouuid.mysqlbench.cross.MysqlCrossBench;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class MysqlCrossBenchTest {
    MysqlCrossBench mysqlCrossBench;

    @org.junit.Before
    public void before() {
        String server = "10.45.11.84:3306";
        BenchConfig benchConfig = new BenchConfig();
        benchConfig.setServer(server);
        benchConfig.setUsername("root");
        benchConfig.setPwd("MyNewPass4!");
        benchConfig.setTableSize(10000);
        benchConfig.setThreadNum(200);
        benchConfig.setColNum(2);
        mysqlCrossBench = new MysqlCrossBench(benchConfig);
    }

    @org.junit.Test
    public void prepare() throws Exception {
        mysqlCrossBench.prepare();
    }

    @org.junit.Test
    public void run() throws Exception {
        mysqlCrossBench.run();
    }

    @org.junit.Test
    public void cleanup() throws Exception {
        mysqlCrossBench.cleanup();
    }
}
