package com.jengine.plugin.mongo;

/**
 * @author bl07637
 * @date 3/25/2016
 * @description
 */
public class MongoConfig {
    private String database;
    private String host;
    private String port;
    private String username;
    private String password;
    private String table;

    public String getDatabase() {
        return database;
    }

    public void setDatabaseName(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
