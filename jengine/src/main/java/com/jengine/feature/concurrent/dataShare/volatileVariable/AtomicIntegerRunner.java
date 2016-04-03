package com.jengine.feature.concurrent.dataShare.volatileVariable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author bl07637
 * @date 4/1/2016
 * @description
 */
public class AtomicIntegerRunner {
    public void test() {
        AtomicIntegerTask atomicIntegerTask = new AtomicIntegerTask();

        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(atomicIntegerTask);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}

class AtomicIntegerTask implements Runnable {
    private AtomicInteger count = new AtomicInteger(0);

    private void add() {
        for (int i = 0; i < 100; i++) {
            System.out.println("count=" + count.incrementAndGet());
        }
    }

    @Override
    public void run() {
        add();
    }
}
