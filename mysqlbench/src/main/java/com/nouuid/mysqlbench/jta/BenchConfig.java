package com.nouuid.mysqlbench.jta;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class BenchConfig {
    String server = "127.0.0.1:3306";
    String username = "root";
    String pwd = "";

    String server2 = "127.0.0.1:3306";
    String username2 = "root";
    String pwd2 = "";

    String database = "test";
    int tableNum = 10;
    int colNum = 1;
    int tableSize = 10 * 10000;
    String type = "select";
    long duration = 60 * 1000;
    long reportDuration = 10 * 1000;
    int threadNum = 1;

    public String getUrl() {
        StringBuilder url = new StringBuilder();
        url.append("jdbc:mysql://");
        url.append(server);
        url.append("/").append(database);
        url.append("?useUnicode=true&characterEncoding=UTF8");
        return url.toString();
    }

    public String getUrl2() {
        StringBuilder url = new StringBuilder();
        url.append("jdbc:mysql://");
        url.append(server2);
        url.append("/").append(database);
        url.append("?useUnicode=true&characterEncoding=UTF8");
        return url.toString();
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
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

    public String getServer2() {
        return server2;
    }

    public void setServer2(String server2) {
        this.server2 = server2;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getPwd2() {
        return pwd2;
    }

    public void setPwd2(String pwd2) {
        this.pwd2 = pwd2;
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

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }
}
