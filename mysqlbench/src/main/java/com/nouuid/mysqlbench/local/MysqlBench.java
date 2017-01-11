package com.nouuid.mysqlbench.local;

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

    BenchConfig benchConfig;

    // thread control
    final CountDownLatch startGate;
    final CountDownLatch endGate;

    // time control
    volatile long startTime;
    volatile long currentTime;
    volatile long maxTime;

    // report
    AtomicLong lastOperations = new AtomicLong(0);
    AtomicLong operations = new AtomicLong(0);
    AtomicLong reportCount = new AtomicLong(1);
    Lock reportLock = new ReentrantLock();

    Create create = null;
    Insert insert = null;
    Query query = null;
    Drop drop = null;

    public MysqlBench(BenchConfig benchConfig) {
        this.benchConfig = benchConfig;
        startGate = new CountDownLatch(1);
        endGate = new CountDownLatch(benchConfig.getThreadNum());
        create = new Create(benchConfig);
        insert = new Insert(benchConfig);
        query = new Query(benchConfig);
        drop = new Drop(benchConfig);
    }

    public void prepare() throws Exception {
        System.out.println("do prepare...");
        create.execute();
        insert.execute();
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
                MysqlService mysqlService = new MysqlService(benchConfig.getUrl(), benchConfig.getUsername(), benchConfig.getPwd(), benchConfig.getTestMode());

                while (currentTime <= maxTime) {
                    if ("select".equals(benchConfig.getNontrxMode())) {
                        query.execute(mysqlService);
                    }
                    operations.getAndIncrement();
                    reportInterval();
                    currentTime = System.currentTimeMillis();
                }
                mysqlService.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                endGate.countDown();
            }
        }
    }

    public void cleanup() throws Exception {
        System.out.println("do cleanup...");
        drop.execute();
        System.out.println("cleanup over");
    }


    private void reportInterval() {
        reportLock.lock();
        try {
            long localReportCount = reportCount.get();
            if (currentTime >= (startTime + benchConfig.getReportDuration() * localReportCount)) {
                if (localReportCount == reportCount.get()) {
                    double speed = (double) (operations.get() - lastOperations.get()) / benchConfig.getReportDuration() * 1000;
                    System.out.println("[" + reportCount.get() * benchConfig.getReportDuration() / 1000 + "s]: " + speed + " qps.");
//                    System.out.print(speed + ",");
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
