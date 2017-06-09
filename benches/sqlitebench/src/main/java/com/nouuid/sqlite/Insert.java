package com.nouuid.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * content
 *
 * @author nouuid
 * @date 2/8/2017
 * @since 0.1.0
 */
public class Insert {

    AtomicLong count = new AtomicLong(0);

    private int insertMax = 5000;
    private int threadNum  = 1;
    private int reportSize = 100;

    private String dbFile = null;
    private String table  = null;
    private int columnSize = 1;

    public Insert(int insertMax, int threadNum, int reportSize, String dbFile, String table, int columnSize) {
        this.insertMax = insertMax;
        this.threadNum = threadNum;
        this.reportSize = reportSize;
        this.dbFile = dbFile;
        this.table = table;
        this.columnSize = columnSize;
    }

    public void on() throws InterruptedException {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadNum);
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 1; i <= threadNum; i++) {
            Thread thread = new Thread(new InsertTask(startLatch, endLatch));
            thread.setName("t" + i);
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        ReportTask reportTask = new ReportTask(startLatch);
        Thread reportThread = new Thread(reportTask);
        reportThread.start();
        startLatch.countDown();
        endLatch.await();
        reportTask.setFlag(false);
        Thread.sleep(5 * 1000);
    }

    class InsertTask implements Runnable {
        private Connection connection = null;
        private CountDownLatch startLatch;
        private CountDownLatch endLatch;
        private String            sql   = null;
        private PreparedStatement pstmt = null;

        public InsertTask(CountDownLatch startLatch, CountDownLatch endLatch) {
            this.startLatch = startLatch;
            this.endLatch = endLatch;
            connection = ConnectionHelper.getConnect(dbFile);
            sql = "INSERT INTO " + table + "(";
            for (int i = 1; i <= columnSize; i++) {
                if (i != columnSize) {
                    sql += "name" + i + ",";
                } else {
                    sql += "name" + i + ") VALUES(";
                }
            }
            for (int i = 1; i <= columnSize; i++) {
                if (i != columnSize) {
                    sql += "?,";
                } else {
                    sql += "?);";
                }
            }
            try {
                pstmt = connection.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                startLatch.await();
                while (count.get() < insertMax) {
                    prepareData(count.get());
                    pstmt.executeUpdate();
//                    System.out.println(Thread.currentThread().getName() + "-" + count.get());
                    count.addAndGet(1);
                }
                endLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void prepareData(long index) {
            for (int i = 1; i <= columnSize; i++) {
                try {
                    pstmt.setString(i, "na" + index + "me" + index);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ReportTask implements Runnable {
        private CountDownLatch startLatch;
        volatile boolean flag = true;

        public ReportTask(CountDownLatch startLatch) {
            this.startLatch = startLatch;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            try {
                startLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long start = System.currentTimeMillis();
            long end;
            double last;
            long lastCount = 0;
            while (flag) {
                long nowCount = count.get();
                if (nowCount != lastCount && count.get() % reportSize == 0) {
                    end = System.currentTimeMillis();
                    last = (double)(end - start) / 1000;
                    // [count, time, rate]
                    System.out.println("[" + count.get() + ", " + last + ", " + (double) count.get() / last + "]");
                    lastCount = nowCount;
                }
            }
            System.out.println("result:");
            end = System.currentTimeMillis();
            last = (double)(end - start) / 1000;
            System.out.println("[" + count.get() + ", " + last + ", " + (double) count.get() / last + "]");
        }
    }
}
