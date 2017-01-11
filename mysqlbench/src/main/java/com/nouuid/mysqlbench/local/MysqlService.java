package com.nouuid.mysqlbench.local;

import com.nouuid.mysqlbench.common.StringUtils;

import java.sql.*;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class MysqlService {

    private String url;
    private Connection conn = null;
    private Statement stmt = null;
    private boolean isTrx = true;

    public MysqlService(String url, String username, String password, String trx) {
        try {
            if (StringUtils.isBlank(url)) {
                throw new Exception("blank url");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            if ("nontrx".equals(trx)) {
                isTrx = false;
            }
            System.out.println("connected to mysql.");
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
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

    public void doUpdate(String sql) {
        try {
            stmt.executeUpdate(sql);
            if (isTrx) {
                conn.commit();
            }
        } catch (SQLException sqle) {
            try {
                conn.rollback(); // 发生异常，回滚在本事务中的操做
                stmt.close(); // 事务回滚：转账的两步操作完全撤销
                conn.close();
            } catch (Exception ignore) {

            }
            sqle.printStackTrace();
        }
    }

    public void doQuery(String sql) {
        try {
            stmt.executeQuery(sql);
            if (isTrx) {
                conn.commit();
            }
        } catch (SQLException sqle) {
            try {
                conn.rollback();
                stmt.close();
                conn.close();
            } catch (Exception ignore) {

            }
            sqle.printStackTrace();
        }
    }

    public void close() {
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
