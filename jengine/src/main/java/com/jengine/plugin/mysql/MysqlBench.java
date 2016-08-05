package com.jengine.plugin.mysql;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class MysqlBench {
    protected final Log logger = LogFactory.getLog(getClass());

    // config
    String url = "jdbc:mysql:loadbalance://10.45.11.84:3306/test?loadBalanceConnectionGroup=first&loadBalanceEnableJMX=true&useUnicode=true&characterEncoding=UTF8";
    String username = "root";
    String pwd = "";

    String database = "test";
    int tableNum = 10;
    int colNum = 1;
    int tableSize = 10 * 10000;
    String type = "select";
    long duration = 60 * 1000;
    long reportDuration = 10 * 1000;
    int threadNum = 1;

    // thread control
    final CountDownLatch startGate = new CountDownLatch(1);
    final CountDownLatch endGate = new CountDownLatch(threadNum);

    // time control
    long startTime;
    long currentTime;
    long maxTime;

    // report
    AtomicLong lastOperations = new AtomicLong(0);
    AtomicLong operations = new AtomicLong(0);
    AtomicLong reportCount = new AtomicLong(1);
    Lock reportLock = new ReentrantLock();

    // run
    MysqlService defaultMysqlService = new MysqlService();
    volatile boolean flag = true;

    public void prepare() throws Exception {
        logger.info("do prepare...");
        defaultMysqlService.init(url, username, pwd);
        create();
        insert();
        logger.info("prepare over.");
    }

    public void run() throws Exception {
        logger.info("do run...");
        Thread[] operationThreads = new Thread[threadNum];
        for (int i=0; i<threadNum; i++) {
            operationThreads[i] = new Thread(new OperationTask());
            operationThreads[i].start();
        }
        startGate.countDown();

        startTime = System.currentTimeMillis();
        currentTime = startTime;
        maxTime = currentTime + duration;

        endGate.await();
        logger.info("run over.");
        report();
    }

    class OperationTask implements Runnable {
        public void run() {
            try {
                startGate.await();
                MysqlService mysqlService = new MysqlService();
                mysqlService.init(url, username, pwd);

                while (currentTime <=maxTime) {
                    if ("select".equals(type)) {
                        query(mysqlService);
                    }
                    operations.getAndIncrement();
                    reportLock.lock();
                    long localReportCount;
                    try {
                        localReportCount = reportCount.get();
                    } finally {
                        reportLock.unlock();
                    }
                    if (currentTime >= (startTime + reportDuration * localReportCount)) {
                        reportInterval(localReportCount);
                    }
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
        logger.info("do cleanup...");
        defaultMysqlService.init(url, username, pwd);
        drop();
        logger.info("cleanup over");
    }

    private void create() {
        for (int i = 1; i <= tableNum; i++) {
            StringBuilder sql = new StringBuilder();
            sql.append("create table ").append(database).append(i).append("(\n");
            sql.append("id int not null auto_increment,\n");
            for (int j = 1; j <= colNum; j++) {
                sql.append("col" + j + " varchar(100) not null,\n");
            }
            sql.append("date date,\n");
            sql.append("primary key (id)\n");
            sql.append(");");
            defaultMysqlService.doUpdate(sql.toString());
            logger.info("create table " + database + "" + i + ", finished.");
        }
    }

    private void insert() {
        for (int i = 1; i <= tableNum; i++) {
            for (int j = 0; j < tableSize; j++) {
                StringBuilder sql = new StringBuilder();
                sql.append("insert into ").append(database).append(i).append("(");
                for (int k = 1; k <= colNum; k++) {
                    sql.append("col" + k + ", ");
                }
                sql.append("date)  values (");
                for (int k = 1; k <= colNum; k++) {
                    sql.append("\"" + UUID.randomUUID() + "\"" + ", ");
                }
                sql.append("NOW());");
                defaultMysqlService.doUpdate(sql.toString());
            }
            logger.info("insert table " + database + "" + i + ", finished.");
        }
    }

    private void query(MysqlService mysqlService) {
        StringBuilder sql = new StringBuilder();
        sql.append("select id from ").append(database).append(randTableID()).append("\n");
        sql.append("where id=");
        sql.append(randRowID());
        sql.append(";");
//        logger.info(sql.toString());
        mysqlService.doQuery(sql.toString());
    }

    private void drop() {
        for (int i = 1; i <= tableNum; i++) {
            String sql = "drop table " + database + "" + i + ";";
            defaultMysqlService.doUpdate(sql);
            logger.info("drop table " + database + "" + i + ", finished.");
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int randTableID() {
        return rand(1, tableNum);
    }

    private int randRowID() {
        return rand(1, tableSize);
    }

    private int rand(int min, int max) {
        int num = min + (int) (Math.random() * (max - min + 1));
        return num;
    }

    private void reportInterval(long localReportCount) {
        reportLock.lock();
        try {
            if (localReportCount == reportCount.get()) {
                double speed = (double) (operations.get() - lastOperations.get()) / reportDuration * 1000;
                logger.info("[" + reportCount.get() * reportDuration / 1000 + "]: " + speed + " qps.");
                reportCount.getAndIncrement();
                lastOperations.set(operations.get());
            }
        } finally {
            reportLock.unlock();
        }
    }

    private void report() {
        reportLock.lock();
        try {
            double speed = (double) (operations.get()) / duration * 1000;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("result:\n").append("qps: ").append(speed);
            logger.info(stringBuilder.toString());
        } finally {
            reportLock.unlock();
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setReportDuration(long reportDuration) {
        this.reportDuration = reportDuration;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }
}
