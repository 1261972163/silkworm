package com.nouuid.mysqlbench;

import com.nouuid.mysqlbench.common.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author nouuid
 * @date 7/14/2016
 * @description
 */
public abstract class MysqlTransaction {

    private String url;
    private String username;
    private String password;
    protected Connection conn = null;
    protected Statement stmt = null;

    public MysqlTransaction(String url, String username, String password) {
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
            // 将自动提交设置为 false，
            //若设置为 true 则数据库将会把每一次数据更新认定为一个事务并自动提交
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn!=null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void query(String sql) {
        try {
            doQuery(sql);
            conn.commit(); // 事务提交：转账的两步操作同时成功
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

    protected abstract void doQuery(String sql) throws SQLException;
}
