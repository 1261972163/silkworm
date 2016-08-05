package com.jengine.java.util.concurrent.synchronoustools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author nouuid
 * @date 4/29/2016
 * @description
 * test time cost that n threads concurrently execute a task
 * main thread firstly execute
 */
public class LatchRunner {
    public void test(int tn) {
        AtomicLong count = new AtomicLong(0);
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(tn);
        Thread[] threads = new Thread[tn];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        System.out.println(count.addAndGet(1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        endGate.countDown();
                    }
                }
            });
        };

        for (int i=0; i<tn; i++) {
            threads[i].start();
        }
        try {
            long start = System.currentTimeMillis();
            startGate.countDown();
            endGate.await();
            long end = System.currentTimeMillis();
            System.out.println("cost:" + (end - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

