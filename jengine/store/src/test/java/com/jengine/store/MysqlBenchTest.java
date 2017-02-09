package com.jengine.store;


import com.jengine.store.db.mysql.MysqlBench;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class MysqlBenchTest {
    MysqlBench mysqlBench;

    @org.junit.Before
    public void before() {
        String url = "jdbc:mysql:loadbalance://10.45.11.84:3306/test?loadBalanceConnectionGroup=first&loadBalanceEnableJMX=true&useUnicode=true&characterEncoding=UTF8";
        mysqlBench = new MysqlBench();
        mysqlBench.setUrl(url);
        mysqlBench.setUsername("root");
        mysqlBench.setPwd("MyNewPass4!");
        mysqlBench.setTableSize(10000);
        mysqlBench.setThreadNum(100);
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
