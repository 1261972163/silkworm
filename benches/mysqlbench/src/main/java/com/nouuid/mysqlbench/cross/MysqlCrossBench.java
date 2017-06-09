package com.nouuid.mysqlbench.cross;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class MysqlCrossBench {

    BenchConfig benchConfig;

    // thread control
    final CountDownLatch startGate;
    final CountDownLatch endGate;

    // time control
    long startTime;
    long currentTime;
    long maxTime;

    // report
    AtomicLong lastOperations = new AtomicLong(0);
    AtomicLong operations = new AtomicLong(0);
    AtomicLong reportCount = new AtomicLong(1);
    Lock reportLock = new ReentrantLock();

    MysqlCrossService defaultMysqlService = null;

    public MysqlCrossBench(BenchConfig benchConfig) {
        this.benchConfig = benchConfig;
        startGate = new CountDownLatch(1);
        endGate = new CountDownLatch(benchConfig.getThreadNum());
        defaultMysqlService = new MysqlCrossService(benchConfig.getUrl(), benchConfig.getUsername(), benchConfig.getPwd());
    }

    public void prepare() throws Exception {

        System.out.println("do prepare...");
        create();
        insert();
        System.out.println("prepare over.");
    }

    public void run() throws Exception {
        System.out.println("do run...");
        Thread[] operationThreads = new Thread[benchConfig.getThreadNum()];
        for (int i = 0; i < benchConfig.getThreadNum(); i++) {
            operationThreads[i] = new Thread(new OperationTask());
            operationThreads[i].start();
        }
        startGate.countDown();

        startTime = System.currentTimeMillis();
        currentTime = startTime;
        maxTime = currentTime + benchConfig.getDuration();

        endGate.await();
        System.out.println("run over.");
        report();
    }

    class OperationTask implements Runnable {
        public void run() {
            try {
                startGate.await();
                MysqlCrossService mysqlService = new MysqlCrossService(benchConfig.getUrl(), benchConfig.getUsername(), benchConfig.getPwd());

                while (currentTime <= maxTime) {
                    if ("select".equals(benchConfig.getType())) {
                        query(mysqlService);
                    }
                    operations.getAndIncrement();
                    reportInterval();
                    currentTime = System.currentTimeMillis();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                endGate.countDown();
            }
        }
    }

    public void cleanup() throws Exception {
        System.out.println("do cleanup...");
        drop();
        System.out.println("cleanup over");
    }

    private void create() {
        for (int i = 1; i <= benchConfig.getTableNum(); i++) {
            StringBuilder sql1 = new StringBuilder();
            StringBuilder sql2 = new StringBuilder();
            sql1.append("create table ").append(benchConfig.getDatabase()).append(i).append("(\n");
            sql2.append("create table ").append(benchConfig.getDatabase()).append("x").append(i).append("(\n");

            StringBuilder sql = new StringBuilder();
            sql.append("id int not null auto_increment,\n");
            for (int j = 1; j <= benchConfig.getColNum(); j++) {
                sql.append("col" + j + " varchar(100) not null,\n");
            }
            sql.append("date date,\n");
            sql.append("primary key (id)\n");
            sql.append(");");

            sql1.append(sql.toString());
            sql2.append(sql.toString());
            defaultMysqlService.doUpdate(sql1.toString());
            defaultMysqlService.doUpdate(sql2.toString());
            System.out.println("create table " + benchConfig.getDatabase() + "" + i + ", finished.");
        }
    }

    private void insert() {
        for (int i = 1; i <= benchConfig.getTableNum(); i++) {
            for (int j = 0; j < benchConfig.getTableSize(); j++) {
                StringBuilder sql1 = new StringBuilder();
                StringBuilder sql2 = new StringBuilder();
                StringBuilder sql = new StringBuilder();
                sql1.append("insert into ").append(benchConfig.getDatabase()).append(i).append("(");
                sql2.append("insert into ").append(benchConfig.getDatabase()).append("x").append(i).append("(");
                for (int k = 1; k <= benchConfig.getColNum(); k++) {
                    sql.append("col" + k + ", ");
                }
                sql.append("date)  values (");
                for (int k = 1; k <= benchConfig.getColNum(); k++) {
                    sql.append("\"" + UUID.randomUUID() + "\"" + ", ");
                }
                sql.append("NOW());");

                sql1.append(sql.toString());
                sql2.append(sql.toString());
                defaultMysqlService.doUpdate(sql1.toString());
                defaultMysqlService.doUpdate(sql2.toString());
            }
            System.out.println("insert table " + benchConfig.getDatabase() + "" + i + ", finished.");
        }
    }

    /**
     * model:
     *
     * select * from table
     * where col=(
     *     select col from table2
     *     where id=randomid
     * )
     */
    private void query(MysqlCrossService mysqlService) {
        StringBuilder sql = new StringBuilder();
        int rtid = randTableID();
        int rtcid = randColID();
        sql.append("select * from ").append(benchConfig.getDatabase()).append(rtid).append("\n");
        sql.append("where col").append(rtcid).append("=(");
        sql.append("select col").append(rtcid).append(" from ").append(benchConfig.getDatabase()).append("x").append(rtid).append("\n");
        sql.append("where id=").append(randRowID()).append("\n");
        sql.append(");");
        mysqlService.doQuery(sql.toString());
    }

    private void drop() {
        for (int i = 1; i <= benchConfig.getTableNum(); i++) {
            String sql1 = "drop table " + benchConfig.getDatabase() + "" + i + ";";
            String sql2 = "drop table " + benchConfig.getDatabase() + "x" + i + ";";
            defaultMysqlService.doUpdate(sql1);
            defaultMysqlService.doUpdate(sql2);
            System.out.println("drop table " + benchConfig.getDatabase() + "" + i + ", finished.");
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    private void reportInterval() {
        reportLock.lock();
        try {
            long localReportCount = reportCount.get();
            if (currentTime >= (startTime + benchConfig.getReportDuration() * localReportCount)) {
                if (localReportCount == reportCount.get()) {
                    double speed = (double) (operations.get() - lastOperations.get()) / benchConfig.getReportDuration() * 1000;
                    System.out.println("[" + reportCount.get() * benchConfig.getReportDuration() / 1000 + "s]: " + speed + " qps.");
                    reportCount.getAndIncrement();
                    lastOperations.set(operations.get());
                }
            }
        } finally {
            reportLock.unlock();
        }
    }

    private void report() {
        reportLock.lock();
        try {
            double speed = (double) (operations.get()) / benchConfig.getDuration() * 1000;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("result:\n").append("qps: ").append(speed);
            System.out.println(stringBuilder.toString());
        } finally {
            reportLock.unlock();
        }
    }
}
