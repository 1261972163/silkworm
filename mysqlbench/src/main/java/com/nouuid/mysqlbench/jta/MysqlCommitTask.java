package com.nouuid.mysqlbench.jta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * @author nouuid
 * @date 7/14/2016
 * @description
 */
public class MysqlCommitTask {
    private BenchConfig benchConfig = null;
    private Connection connA = null;
    private Connection connB = null;

    public MysqlCommitTask(BenchConfig benchConfig, Connection connA, Connection connB) {
        this.benchConfig = benchConfig;
        this.connA = connA;
        this.connB = connB;
    }

    public void create(Statement stmtA, Statement stmtB) {
        for (int i = 1; i <= benchConfig.getTableNum(); i++) {
            StringBuilder sql = new StringBuilder();
            sql.append("create table ").append(benchConfig.getDatabase()).append(i).append("(\n");
            sql.append("id int not null auto_increment,\n");
            for (int j = 1; j <= benchConfig.getColNum(); j++) {
                sql.append("col" + j + " varchar(100) not null,\n");
            }
            sql.append("date date,\n");
            sql.append("primary key (id)\n");
            sql.append(");");
            doUpdate(stmtA, stmtB, sql.toString());
            System.out.println("create table " + benchConfig.getDatabase() + "" + i + ", finished.");
        }
    }

    public void insert(Statement stmtA, Statement stmtB) {
        for (int i = 1; i <= benchConfig.getTableNum(); i++) {
            for (int j = 0; j < benchConfig.getTableSize(); j++) {
                StringBuilder sql = new StringBuilder();
                sql.append("insert into ").append(benchConfig.getDatabase()).append(i).append("(");
                for (int k = 1; k <= benchConfig.getColNum(); k++) {
                    sql.append("col" + k + ", ");
                }
                sql.append("date)  values (");
                for (int k = 1; k <= benchConfig.getColNum(); k++) {
                    sql.append("\"" + UUID.randomUUID() + "\"" + ", ");
                }
                sql.append("NOW());");
                doUpdate(stmtA, stmtB, sql.toString());
            }
            System.out.println("insert table " + benchConfig.getDatabase() + "" + i + ", finished.");
        }
    }



    public void drop(Statement stmtA, Statement stmtB) {
        for (int i = 1; i <= benchConfig.getTableNum(); i++) {
            String sql = "drop table " + benchConfig.getDatabase() + "" + i + ";";
            doUpdate(stmtA, stmtB, sql);
            System.out.println("drop table " + benchConfig.getDatabase() + "" + i + ", finished.");
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * model:
     *
     * select B.table.col form B.table
     * where B.table.id=randomid
     *
     * select * from A.table
     * where A.table.col=B.table.col
     *
     * @throws SQLException
     */
    public void execute(Statement stmtA, Statement stmtB) throws SQLException {
        int rtid = randTableID();
        int rtcid = randColID();
        StringBuilder sqlB = new StringBuilder();
        sqlB.append("select col").append(rtcid).append(" from ").append(benchConfig.getDatabase()).append(rtid).append("\n");
        sqlB.append("where id=");
        sqlB.append(randRowID());
        sqlB.append(";");
        ResultSet resultSetB = stmtB.executeQuery(sqlB.toString());
        connB.commit();

        boolean flag = true;
        Object col = null;
        while (resultSetB.next() && flag) {
//            if (rtcid==1) {
//                resultSetB.getInt(rtcid);
//            } else if (rtcid==benchConfig.getColNum()+2) {
//                resultSetB.getDate(rtcid);
//            } else {
//                col = resultSetB.getString(rtcid);
//            }
            rtcid = 1;
            col = resultSetB.getString(1);
            flag = false;
        }

        StringBuilder sqlA = new StringBuilder();
        sqlA.append("select * from ").append(benchConfig.getDatabase()).append(rtid).append("\n");
        sqlA.append("where col").append(rtcid).append("=").append("\"").append(col).append("\"");
        sqlA.append(";");
        ResultSet resultSetA = stmtA.executeQuery(sqlA.toString());
        connA.commit();
    }

    public void execute2(Statement stmtA, Statement stmtB) throws SQLException {
        int rtid = randTableID();
        int rtcid = randColID();
        int rrid = randRowID();
        StringBuilder sqlB = new StringBuilder();
        sqlB.append("select col").append(rtcid).append(" from ").append(benchConfig.getDatabase()).append(rtid).append("\n");
        sqlB.append("where id=").append(rrid);
        sqlB.append(";");
        ResultSet resultSetB = stmtB.executeQuery(sqlB.toString());
        connB.commit();

        ResultSet resultSetA = stmtA.executeQuery(sqlB.toString());
        connA.commit();
    }

    public void doUpdate(Statement stmtA, Statement stmtB, String sql) {
        try {
            stmtA.executeUpdate(sql);
            stmtB.executeUpdate(sql);
            connA.commit(); // 事务提交：转账的两步操作同时成功
            connB.commit(); // 事务提交：转账的两步操作同时成功
        } catch (SQLException sqle) {
            try {
                connA.rollback();
                connB.rollback();
            } catch (Exception ignore) {
                close(stmtA, stmtB);
            }
            sqle.printStackTrace();
        }
    }

    public void doQuery(Statement stmtA, Statement stmtB, String sql) {
        try {
            stmtA.executeQuery(sql);
            stmtB.executeQuery(sql);
            connA.commit(); // 事务提交：转账的两步操作同时成功
            connB.commit(); // 事务提交：转账的两步操作同时成功
        } catch (SQLException sqle) {
            try {
                connA.rollback();
                connB.rollback();
            } catch (Exception ignore) {
                close(stmtA, stmtB);
            }
            sqle.printStackTrace();
        }
    }

    public void close(Statement stmtA, Statement stmtB) {
        try {
            if (stmtA!=null && !stmtA.isClosed()) {
                stmtA.close();
            }
            if (connA!=null && !connA.isClosed()) {
                connA.close();
            }
            if (stmtB!=null && !stmtB.isClosed()) {
                stmtB.close();
            }
            if (connB!=null && !connB.isClosed()) {
                connB.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private int randTableID() {
        return rand(1, benchConfig.getTableNum());
    }

    private int randRowID() {
        return rand(1, benchConfig.getTableSize());
    }

    private int randColID() {
        return rand(1, benchConfig.getColNum());
    }

    private int rand(int min, int max) {
        int num = min + (int) (Math.random() * (max - min + 1));
        return num;
    }
}
