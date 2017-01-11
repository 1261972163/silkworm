package com.nouuid.mysqlbench.local;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class BenchConfig {
    String server = "127.0.0.1:3306";
    String username = "root";
    String pwd = "";

    String database = "test";
    String tableName = "test";
    int tableNum = 10;
    int colNum = 1;
    long tableRowSize = 10 * 10000;

    String insertMode = "uuid";

    int threadNum = 1;
    String testMode = "nontrx";
    String nontrxMode = "select";
    String selectMode = "id";

    long duration = 60 * 1000;
    long reportDuration = 10 * 1000;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUrl() {
        StringBuilder url = new StringBuilder();
        url.append("jdbc:mysql:loadbalance://");
        url.append(server);
        url.append("/").append(database);
        url.append("?loadBalanceConnectionGroup=first&loadBalanceEnableJMX=true&useUnicode=true&characterEncoding=UTF8");
        return url.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public long getTableRowSize() {
        return tableRowSize;
    }

    public void setTableRowSize(long tableRowSize) {
        this.tableRowSize = tableRowSize;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getReportDuration() {
        return reportDuration;
    }

    public void setReportDuration(long reportDuration) {
        this.reportDuration = reportDuration;
    }

    public String getInsertMode() {
        return insertMode;
    }

    public void setInsertMode(String insertMode) {
        this.insertMode = insertMode;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTestMode() {
        return testMode;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }

    public String getNontrxMode() {
        return nontrxMode;
    }

    public void setNontrxMode(String nontrxMode) {
        this.nontrxMode = nontrxMode;
    }

    public String getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(String selectMode) {
        this.selectMode = selectMode;
    }
}
