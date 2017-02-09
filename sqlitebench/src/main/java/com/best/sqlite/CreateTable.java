package com.best.sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * content
 *
 * @author nouuid
 * @date 2/8/2017
 * @since 0.1.0
 */
public class CreateTable {
    private int columnNum = 1;

    private Connection connection = null;
    private String sql;
    public CreateTable(String dbFile, String table, int columnNum) {
        connection = ConnectionHelper.getConnect(dbFile);
        sql = "CREATE TABLE " + table + " (id integer not null primary key, ";
        for (int i = 1; i <= columnNum; i++) {
            if (i == columnNum) {
                sql += "name" + i + " text);";
            } else {
                sql += "name" + i + " text, ";
            }
        }
    }

    public void on() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
