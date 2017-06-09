package com.jengine.data.database.sqlite;

import java.sql.*;

/**
 * content
 *
 * @author nouuid
 * @date 6/9/2017
 * @since 0.1.0
 */
public class SqliteClient {
    private Connection connection = null;

    public SqliteClient(String dbFile) {
        // 1. 创建连接
        try {
            Class.forName("org.sqlite.JDBC");
            // db parameters
            String url = "jdbc:sqlite:" + dbFile;
            // create a connection to the database
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String sql) {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String sql) {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet select(String sql) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void close() {
        if (connection != null) {
            synchronized (ConnectionHelper.class) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    connection = null;
                }
            }
        }
    }
}
