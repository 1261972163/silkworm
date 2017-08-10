package com.jengine.data.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description mysql loadbalance service
 */
public class MysqlLBService {

    private String url;
    private String username;
    private String password;

    public void init(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Connection getNewConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

    public void executeUpdate(String sql){
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getNewConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt!=null && !stmt.isClosed()) {
                    stmt.close();
                }
                if (conn!=null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void executeQuery(String sql){
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getNewConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt!=null && !stmt.isClosed()) {
                    stmt.close();
                }
                if (conn!=null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
