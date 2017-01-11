package com.nouuid.mysqlbench.local;

/**
 * @author nouuid
 * @description
 * @date 7/29/16
 */
public class Create {

    BenchConfig benchConfig;
    MysqlService mysqlService = null;

    public Create(BenchConfig benchConfig) {
        this.benchConfig = benchConfig;
        mysqlService = new MysqlService(benchConfig.getUrl(), benchConfig.getUsername(), benchConfig.getPwd(), "trx");
    }

    public void execute() {
        for (int i = 1; i <= benchConfig.getTableNum(); i++) {
            StringBuilder sql = new StringBuilder();
            sql.append("create table ").append(benchConfig.getTableName()).append(i).append("(\n");
            sql.append("id int not null auto_increment,\n");
            for (int j = 1; j <= benchConfig.getColNum(); j++) {
                sql.append("col" + j + " varchar(256) not null,\n");
            }
            sql.append("date date,\n");
            sql.append("primary key (id)\n");
            sql.append(");");
            mysqlService.doUpdate(sql.toString());
            System.out.println(sql.toString());
        }
        mysqlService.close();
    }
}
