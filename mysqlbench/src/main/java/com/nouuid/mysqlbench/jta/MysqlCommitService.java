package com.nouuid.mysqlbench.jta;

import com.nouuid.mysqlbench.common.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class MysqlCommitService {

    private Connection connA = null;
    private Connection connB = null;
    private Statement stmtA = null;
    private Statement stmtB = null;

    private MysqlCommitTask mysqlTask = null;
    private BenchConfig benchConfig = null;

    public MysqlCommitService(String url, String username, String password,
                              String url2, String username2, String password2,
                              BenchConfig benchConfig) {
        try {
            if (StringUtils.isBlank(url)) {
                throw new Exception("blank url");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connA = DriverManager.getConnection(url, username, password);
            connB = DriverManager.getConnection(url2, username2, password2);
            // 将自动提交设置为 false，
            //若设置为 true 则数据库将会把每一次数据更新认定为一个事务并自动提交
            connA.setAutoCommit(false);
            connB.setAutoCommit(false);
            System.out.println("connected to mysql.");

            mysqlTask = new MysqlCommitTask(benchConfig, connA, connB);
        } catch (Exception e) {
            e.printStackTrace();
            mysqlTask.close(stmtA, stmtB);
        }
    }

    public void create() {
        try {
            stmtA = connA.createStatement();
            stmtB = connB.createStatement();
            mysqlTask.create(stmtA, stmtB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert() {
        try {
            stmtA = connA.createStatement();
            stmtB = connB.createStatement();
            mysqlTask.insert(stmtA, stmtB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drop() {
        try {
            stmtA = connA.createStatement();
            stmtB = connB.createStatement();
            mysqlTask.drop(stmtA, stmtB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execute() {
        try {
            stmtA = connA.createStatement();
            stmtB = connB.createStatement();
            mysqlTask.execute(stmtA, stmtB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
