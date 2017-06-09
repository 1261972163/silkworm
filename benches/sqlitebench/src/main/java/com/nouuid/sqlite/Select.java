package com.nouuid.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * content
 *
 * @author nouuid
 * @date 2/8/2017
 * @since 0.1.0
 */
public class Select {

    private String dbFile = null;
    private String table               = "test1";
    private int    searchRange         = 5000;
    private int    requestNumPerThread = 10000;
    private int    reportSize          = 100;
    private int    threadNum           = 10;

    public Select(String dbFile, String table, int searchRange, int requestNumPerThread, int reportSize, int threadNum) {
        this.dbFile = dbFile;
        this.table = table;
        this.searchRange = searchRange;
        this.requestNumPerThread = requestNumPerThread;
        this.reportSize = reportSize;
        this.threadNum = threadNum;
    }

    public void on() throws InterruptedException {
        List<Thread> threads = new ArrayList<Thread>();
        List<GetTask> getTasks = new ArrayList<GetTask>();
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(threadNum);
        long allStart = System.currentTimeMillis();

        for (int i = 1; i <= threadNum; i++) {
            GetTask getTask = new GetTask(startLatch, endLatch);
            getTasks.add(getTask);
            Thread thread = new Thread(getTask);
            thread.setName("t" + i);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
        startLatch.countDown();
        endLatch.await();
        long allEnd = System.currentTimeMillis();
        double allLast = (double) (allEnd - allStart) / 1000;
        long allCount = 0;
        for (GetTask getTask : getTasks) {
            allCount += getTask.getCount();
        }

        System.out.println("result:");
        System.out.println(Thread.currentThread().getName() + "-" + "[" + allCount + ", " + allLast + ", " + (double) allCount / allLast + "]");
    }

    class GetTask implements Runnable {
        Connection conn = null;
        Statement  stmt = null;
        private CountDownLatch startLatch;
        private CountDownLatch endLatch;

        private int count = 0;

        public GetTask(CountDownLatch startLatch, CountDownLatch endLatch) {
            this.startLatch = startLatch;
            this.endLatch = endLatch;
            conn = ConnectionHelper.getConnect(dbFile);
        }

        public int getCount() {
            return count;
        }

        @Override
        public void run() {
            try {
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int x;
                Random random = new Random();
                long start = System.currentTimeMillis();
                long end = start;
                stmt = conn.createStatement();
                while (count < requestNumPerThread) {
//                    System.out.println(Thread.currentThread().getName() + "-" + count);
                    x = random.nextInt(searchRange);
//                String name = "\'" + "naw" + x + "mey" + x + "\'";
                    String sql = "SELECT id FROM " + table + " where id=" + x;
                    ResultSet rs = null;
                    try {
                        rs = stmt.executeQuery(sql);
                        count++;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                        return;
                    }
                    if (count % reportSize == 0) {
                        end = System.currentTimeMillis();
                        double last = (end - start) / (double) 1000;
                        System.out.println(Thread.currentThread().getName() + "-" + "[" + count + ", " + last + ", " + (double) count / last + "]");
                    }
                }
                endLatch.countDown();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
