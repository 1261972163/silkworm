package com.jengine.j2se.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nouuid
 * @date 4/6/2016
 * @description
 */
public class FairNofairRunner {

    public void fairTest() {
        FairNofairTask fairNofairTask = new FairNofairTask(true);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start");
                fairNofairTask.service();
            }
        };

        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(runnable);
            threads[i].setName("T" + i);
        }
        for (int i=0; i<10; i++) {
            threads[i].start();
        }
    }

    public void nofairTest() {
        FairNofairTask fairNofairTask = new FairNofairTask(false);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start");
                fairNofairTask.service();
            }
        };

        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(runnable);
            threads[i].setName("T" + i);
        }
        for (int i=0; i<10; i++) {
            threads[i].start();
        }
    }
}

class FairNofairTask {
    private Lock locker;

    public FairNofairTask(boolean isFair) {
        locker = new ReentrantLock(isFair);
    }

    public void service() {
        locker.lock();
        System.out.println("                     " + Thread.currentThread().getName() + " do service");
        locker.unlock();
    }
}