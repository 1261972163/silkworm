package com.nouuid.mysqlbench.local;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author nouuid
 * @description
 * @date 7/29/16
 */
public class Insert {

    BenchConfig benchConfig;
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.HOURS, new ArrayBlockingQueue(10));

    public Insert(BenchConfig benchConfig) {
        this.benchConfig = benchConfig;
    }

    public void execute() {
        if ("string".equals(benchConfig.getInsertMode())) {
            for (int i = 1; i <= benchConfig.getTableNum(); i++) {
                final int rowid = i;
                final MysqlService mysqlService = new MysqlService(benchConfig.getUrl(), benchConfig.getUsername(), benchConfig.getPwd(), "trx");
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 1; j <= benchConfig.getTableRowSize(); j++) {
                            insertUnit(rowid, j, mysqlService);
                        }
                        mysqlService.close();
                        System.out.println("insert table " + benchConfig.getTableName() + "" + rowid + ", finished.");
                    }
                });
            }
        } else {
            for (int i = 1; i <= benchConfig.getTableNum(); i++) {
                final int rowid = i;
                final MysqlService mysqlService = new MysqlService(benchConfig.getUrl(), benchConfig.getUsername(), benchConfig.getPwd(), "trx");
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 1; j <= benchConfig.getTableRowSize(); j++) {
                            insertUUID(rowid, j, mysqlService);
                        }
                        mysqlService.close();
                        System.out.println("insert table " + benchConfig.getTableName() + "" + rowid + ", finished.");
                    }
                });
            }
        }
    }

    private void insertUUID(int tableId, int rowId, MysqlService mysqlService) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(benchConfig.getTableName()).append(tableId).append("(");
        for (int k = 1; k <= benchConfig.getColNum(); k++) {
            sql.append("col" + k + ", ");
        }
        sql.append("date)  values (");
        for (int k = 1; k <= benchConfig.getColNum(); k++) {
            String s = UUID.randomUUID().toString();
            sql.append("\"" + s);
//            for (int kk=1; kk <= 5; kk++) {
                sql.append(s);
//            }
            sql.append(s + "\", ");
        }
        sql.append("NOW());");
        mysqlService.doUpdate(sql.toString());
    }

    private void insertUnit(int tableId, int rowId, MysqlService mysqlService) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(benchConfig.getTableName()).append(tableId).append("(");
        for (int k = 1; k <= benchConfig.getColNum(); k++) {
            sql.append("col" + k + ", ");
        }
        sql.append("date)  values (");
        for (int k = 1; k <= benchConfig.getColNum(); k++) {
            StringBuilder unitStr = new StringBuilder();
            unitStr.append(rowId).append("a").append(rowId).append("a").append(rowId);
            sql.append("\"");
//            for (int kk=1; kk <= 5; kk++) {
                sql.append(unitStr);
//            }
            sql.append("\", ");
        }
        sql.append("NOW());");
        mysqlService.doUpdate(sql.toString());
    }

}
