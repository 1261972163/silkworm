package com.nouuid.mysqlbench.jta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * @author nouuid
 * @date 7/14/2016
 * @description
 */
public class MysqlJtaTask {
    private BenchConfig benchConfig = null;

    public MysqlJtaTask(BenchConfig benchConfig) {
        this.benchConfig = benchConfig;
    }

    public void create(Statement stmtA, Statement stmtB, int tableId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("create table ").append(benchConfig.getDatabase()).append(tableId).append("(\n");
        sql.append("id int not null auto_increment,\n");
        for (int j = 1; j <= benchConfig.getColNum(); j++) {
            sql.append("col" + j + " varchar(100) not null,\n");
        }
        sql.append("date date,\n");
        sql.append("primary key (id)\n");
        sql.append(");");

        stmtA.executeUpdate(sql.toString());
        stmtB.executeUpdate(sql.toString());
    }

    public void insert(Statement stmtA, Statement stmtB, int tableId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(benchConfig.getDatabase()).append(tableId).append("(");
        for (int k = 1; k <= benchConfig.getColNum(); k++) {
            sql.append("col" + k + ", ");
        }
        sql.append("date)  values (");
        for (int k = 1; k <= benchConfig.getColNum(); k++) {
            sql.append("\"" + UUID.randomUUID() + "\"" + ", ");
        }
        sql.append("NOW());");
        stmtA.executeUpdate(sql.toString());
        stmtB.executeUpdate(sql.toString());
    }

    public void drop(Statement stmtA, Statement stmtB, int tableId) throws SQLException {
        String sql = "drop table " + benchConfig.getDatabase() + "" + tableId + ";";
        stmtA.executeUpdate(sql.toString());
        stmtB.executeUpdate(sql.toString());
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

//        boolean flag = true;
//        Object col = null;
//        while (resultSetB.next() && flag) {
////            if (rtcid==1) {
////                resultSetB.getInt(rtcid);
////            } else if (rtcid==benchConfig.getColNum()+2) {
////                resultSetB.getDate(rtcid);
////            } else {
////                col = resultSetB.getString(rtcid);
////            }
//            rtcid = 1;
//            col = resultSetB.getString(1);
//            flag = false;
//        }
//
//        StringBuilder sqlA = new StringBuilder();
//        sqlA.append("select * from ").append(benchConfig.getDatabase()).append(rtid).append("\n");
//        sqlA.append("where col").append(rtcid).append("=").append("\"").append(col).append("\"");
//        sqlA.append(";");
//        ResultSet resultSetA = stmtA.executeQuery(sqlA.toString());
    }

    public void execute2(Statement stmtA, Statement stmtB) throws SQLException {
//        int rtid = randTableID();
        int rtid = 1;
        int rtcid = randColID();
        StringBuilder sqlB = new StringBuilder();
        sqlB.append("select col").append(rtcid).append(" from ").append(benchConfig.getDatabase()).append(rtid).append("\n");
        sqlB.append("where id=");
        sqlB.append(randRowID());
        sqlB.append(";");
        ResultSet resultSetB = stmtB.executeQuery(sqlB.toString());

        boolean flag = true;
        Object col = null;
        while (resultSetB.next() && flag) {
            rtcid = 1;
            col = resultSetB.getString(1);
            flag = false;
        }

        StringBuilder sqlA = new StringBuilder();
        sqlA.append("select * from ").append(benchConfig.getDatabase()).append(rtid).append("\n");
        sqlA.append("where col").append(rtcid).append("=").append("\"").append(col).append("\"");
        sqlA.append(";");
        ResultSet resultSetA = stmtA.executeQuery(sqlA.toString());
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
