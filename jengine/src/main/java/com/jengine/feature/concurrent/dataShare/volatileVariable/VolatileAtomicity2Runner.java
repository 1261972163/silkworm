package com.jengine.feature.concurrent.dataShare.volatileVariable;

/**
 * @author nouuid
 * @date 4/1/2016
 * @description
 */
public class VolatileAtomicity2Runner {
    public void test() {
        VolatileAtomicityTask volatileAtomicityTask = new VolatileAtomicityTask();

        Thread[] threads = new Thread[100];
        for (int i=0; i<100; i++) {
            threads[i] = new Thread(volatileAtomicityTask);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void test2() {
        VolatileAtomicityTask2 volatileAtomicityTask2 = new VolatileAtomicityTask2();

        Thread[] threads = new Thread[100];
        for (int i=0; i<100; i++) {
            threads[i] = new Thread(volatileAtomicityTask2);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}

class VolatileAtomicityTask implements Runnable {
    volatile private int count;

    private void add() {
        for (int i=0; i<100; i++) {
            count++;
        }
        System.out.println("count=" + count);
    }

    @Override
    public void run() {
        add();
    }
}

class VolatileAtomicityTask2 implements Runnable {
    volatile private int count;

    private void add() {
        synchronized (VolatileAtomicityTask2.class) {
            for (int i = 0; i < 100; i++) {
                count++;
            }
            System.out.println("count=" + count);
        }
    }

    @Override
    public void run() {
        add();
    }
}
