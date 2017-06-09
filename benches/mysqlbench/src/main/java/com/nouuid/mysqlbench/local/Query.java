package com.nouuid.mysqlbench.local;

/**
 * @author nouuid
 * @description
 * @date 7/29/16
 */
public class Query {

    BenchConfig benchConfig;

    public Query(BenchConfig benchConfig) {
        this.benchConfig = benchConfig;
    }

    public void execute(MysqlService mysqlService) {
        if ("string".equals(benchConfig.getSelectMode())) {
            queryString(mysqlService);
        } else {
            queryId(mysqlService);
        }
    }

    private void queryId(MysqlService mysqlService) {
        StringBuilder sql = new StringBuilder();
        sql.append("select id from ").append(benchConfig.getTableName()).append(randTableID()).append("\n");
        sql.append("where id=");
        sql.append(randRowID());
        sql.append(";");
        mysqlService.doQuery(sql.toString());
    }

    private void queryString(MysqlService mysqlService) {
        StringBuilder sql = new StringBuilder();
        sql.append("select col1 from ").append(benchConfig.getTableName()).append(randTableID()).append("\n");
        sql.append("where col1=");
        long rrid = randRowID();
        sql.append("\"");
        sql.append(rrid).append("a").append(rrid).append("a").append(rrid);
        sql.append("\"");
        sql.append(";");
        mysqlService.doQuery(sql.toString());
    }

    private long randTableID() {
        return rand(1, benchConfig.getTableNum());
    }

    private long randRowID() {
        return rand(1, benchConfig.getTableRowSize());
    }

    private long rand(long min, long max) {
        long num = min + (long) (Math.random() * (max - min + 1));
        return num;
    }
}
