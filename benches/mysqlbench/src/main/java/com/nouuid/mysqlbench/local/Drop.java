package com.nouuid.mysqlbench.local;

/**
 * @author nouuid
 * @description
 * @date 7/29/16
 */
public class Drop {

    BenchConfig benchConfig;
    MysqlService mysqlService = null;

    public Drop(BenchConfig benchConfig) {
        this.benchConfig = benchConfig;
        mysqlService = new MysqlService(benchConfig.getUrl(), benchConfig.getUsername(), benchConfig.getPwd(), "trx");
    }

    public void execute() {
        for (int i = 1; i <= benchConfig.getTableNum(); i++) {
            String sql = "drop table " + benchConfig.getTableName() + "" + i + ";";
            mysqlService.doUpdate(sql);
            System.out.println("drop table " + benchConfig.getTableName() + "" + i + ", finished.");
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mysqlService.close();
    }
}
