package com.nouuid.mysqlbench.jta;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class MysqlCommitBench {

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

    MysqlCommitService mysqlCommitService = null;

    public MysqlCommitBench(BenchConfig benchConfig) {
        this.benchConfig = benchConfig;
        startGate = new CountDownLatch(1);
        endGate = new CountDownLatch(benchConfig.getThreadNum());
        mysqlCommitService = new MysqlCommitService(benchConfig.getUrl(), benchConfig.getUsername(), benchConfig.getPwd(),
                benchConfig.getUrl2(), benchConfig.getUsername2(), benchConfig.getPwd2(), benchConfig);
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
                while (currentTime <= maxTime) {
                    if ("crossSelect".equals(benchConfig.getType())) {
                        execute();
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
        mysqlCommitService.create();
    }

    private void insert() {
        mysqlCommitService.insert();
    }

    private void drop() {
        mysqlCommitService.drop();
    }

    private void execute() {
        mysqlCommitService.execute();
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
