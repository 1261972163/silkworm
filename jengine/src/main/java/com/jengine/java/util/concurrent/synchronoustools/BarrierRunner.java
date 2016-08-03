package com.jengine.java.util.concurrent.synchronoustools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author bl07637
 * @date 4/29/2016
 * @description
 * A synchronization aid that allows a set of threads to all wait for
 * each other to reach a common barrier point.
 * the common barrier point is the location where await() method is called.
 */
public class BarrierRunner {

    private long startTime;
    private long endTime;

    public void test(int tn) {
        AtomicLong count = new AtomicLong(0);
        startTime = System.currentTimeMillis();
        final CyclicBarrier barrier = new CyclicBarrier(tn, new Runnable() {
            @Override
            public void run() {
                endTime = System.currentTimeMillis();
                System.out.println("threads preparation costs=" + (endTime-startTime));
            }
        });

        Thread[] threads = new Thread[tn];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " waiting");
                        barrier.await();
                        System.out.println("                   " + Thread.currentThread().getName() + count.addAndGet(1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[i].setName("t" + i);
        };

        for (int i=0; i<tn; i++) {
            threads[i].start();
        }
    }


}

