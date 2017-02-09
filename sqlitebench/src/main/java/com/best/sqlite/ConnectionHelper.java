package com.best.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * content
 *
 * @author nouuid
 * @date 2/8/2017
 * @since 0.1.0
 */
public class ConnectionHelper {

    private static Connection conn = null;

    public static Connection getConnect(String dbFile) {
        if (conn==null) {
            synchronized (ConnectionHelper.class) {
                if (conn == null) {
                    try {
                        Class.forName("org.sqlite.JDBC");
                        // db parameters
                        String url = "jdbc:sqlite:" + dbFile;
                        // create a connection to the database
                        conn = DriverManager.getConnection(url);
                        System.out.println("Connection to SQLite has been established.");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return conn;
    }

    public static void close() {
        if (conn!=null) {
            synchronized (ConnectionHelper.class) {
                if (conn!=null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    conn = null;
                }
            }
        }
    }
}
