package com.jengine.data.sqlite;

/**
 * content
 *
 * @author nouuid
 * @date 6/9/2017
 * @since 0.1.0
 */
public class SqliteClientDemo {
    SqliteClient sqliteClient = new SqliteClient("");

    public void createTable() {

        String sql = "CREATE TABLE test (id integer not null primary key, name text)";
        sqliteClient.createTable(sql);
    }

    public void insert() {
        String sql = "INSERT INTO test (1, \"jerry\")";
        sqliteClient.insert(sql);
    }

    public void select() {
        String sql = "SELECT id FROM test where id=1";
        sqliteClient.select(sql);
    }
}
