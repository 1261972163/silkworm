package com.jengine.feature.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author bl07637
 * @date 4/6/2016
 * @description
 */
public class ReentrantLockRunner {

    public void reentrantLockTest() {
        ReentrantLockTask reentrantLockTask = new ReentrantLockTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTask.service();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTask.service();
            }
        });

        t1.setName("A");
        t2.setName("B");

        t1.start();
        t2.start();

    }
}

class ReentrantLockTask {
    private Lock locker = new ReentrantLock();

    public void service() {
        try {
            locker.lock();
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " do service " + i);
                Thread.sleep(1000);
            }
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
