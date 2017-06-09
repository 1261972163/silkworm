package com.nouuid.mysqlbench.jta;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.nouuid.mysqlbench.common.StringUtils;

import javax.transaction.UserTransaction;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class MysqlJtaService {

    AtomikosDataSourceBean dsA = new AtomikosDataSourceBean();
    AtomikosDataSourceBean dsB = new AtomikosDataSourceBean();

    UserTransaction txn = null;

    private MysqlJtaTask mysqlJtaTask;
    private BenchConfig benchConfig = null;

    public MysqlJtaService(String urlA, String usernameA, String passwordA,
                           String urlB, String usernameB, String passwordB,
                           BenchConfig benchConfig) {
        try {
            if (StringUtils.isBlank(urlA) || StringUtils.isBlank(urlB)) {
                throw new Exception("blank url");
            }

            this.benchConfig = benchConfig;
            mysqlJtaTask = new MysqlJtaTask(benchConfig);
            txn = new UserTransactionImp();

            dsA = new AtomikosDataSourceBean();
            dsA.setUniqueResourceName("mysqlA");
            dsA.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
            Properties pA = new Properties();
            pA.setProperty("user", usernameA);
            pA.setProperty("password", passwordA);
            pA.setProperty("URL", urlA);
            dsA.setXaProperties(pA);
            dsA.setMinPoolSize(benchConfig.getThreadNum()+100);
            dsA.setMaxPoolSize(benchConfig.getThreadNum()+100);
            dsA.setMaxIdleTime(60);

            dsB = new AtomikosDataSourceBean();
            dsB.setUniqueResourceName("mysqlB");
            dsB.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
            Properties pB = new Properties();
            pB.setProperty("user", usernameB);
            pB.setProperty("password", passwordB);
            pB.setProperty("URL", urlB);
            dsB.setXaProperties(pB);
            dsA.setMinPoolSize(benchConfig.getThreadNum()+100);
            dsA.setMaxPoolSize(benchConfig.getThreadNum()+100);
            dsB.setMaxIdleTime(60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnA() {
        Connection connA = null;
        try {
            connA = dsA.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connA;
    }

    public Connection getConnB() {
        Connection connB = null;
        try {
            connB = dsB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connB;
    }

    public void create(Connection connA, Connection connB, int tableId) {
        Statement stmtA = null;
        Statement stmtB = null;
        try {
            stmtA = connA.createStatement();
            stmtB = connB.createStatement();
            mysqlJtaTask.create(stmtA, stmtB, tableId);
        } catch (Exception sqle) {
            try{
                connA.rollback();
                connB.rollback();
            }catch(Exception ignore){

            }
            sqle.printStackTrace();
        } finally {
            closeStatement(stmtA, stmtB);
            closeConnection(connA, connB);
        }
    }

    public void insert(Connection connA, Connection connB, int tableId) {
        try {
            txn.begin();
            Statement stmtA = connA.createStatement();
            Statement stmtB = connB.createStatement();
            mysqlJtaTask.insert(stmtA, stmtB, tableId);
            txn.commit();
        } catch (Exception sqle) {
            try{
                txn.rollback();
            }catch(Exception ignore){
                ignore.printStackTrace();
            }
            sqle.printStackTrace();
        } finally {
            closeConnection(connA, connB);
        }
    }

    public void drop(Connection connA, Connection connB, int tableId) {
        Statement stmtA = null;
        Statement stmtB = null;
        try {
            stmtA = connA.createStatement();
            stmtB = connB.createStatement();
            mysqlJtaTask.drop(stmtA, stmtB, tableId);
        } catch (Exception sqle) {
            try{
                connA.rollback();
                connB.rollback();
            }catch(Exception ignore){

            }
            sqle.printStackTrace();
        } finally {
            closeStatement(stmtA, stmtB);
            closeConnection(connA, connB);
        }
    }

    public void execute(Connection connA, Connection connB) {
        try {
            txn.begin();
            Statement stmtA = connA.createStatement();
            Statement stmtB = connB.createStatement();
            mysqlJtaTask.execute(stmtA, stmtB);
            txn.commit();
        } catch (Exception sqle) {
            try{
                txn.rollback();
            }catch(Exception ignore){

            }
            sqle.printStackTrace();
        } finally {
            closeConnection(connA, connB);
        }
    }

    private void closeStatement(Statement stmA, Statement stmB) {
        try {
            if (stmA !=null && !stmA.isClosed()) {
                stmA.close();
            }
            if (stmB !=null && !stmB.isClosed()) {
                stmB.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(Connection connA, Connection connB) {
        try {
            if (connA !=null && !connA.isClosed()) {
                connA.close();
            }
            if (connB !=null && !connB.isClosed()) {
                connB.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
