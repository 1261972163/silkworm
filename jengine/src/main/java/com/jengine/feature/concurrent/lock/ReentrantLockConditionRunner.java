package com.jengine.feature.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author bl07637
 * @date 4/6/2016
 * @description
 */
public class ReentrantLockConditionRunner {

    public void reentrantLockConditionAwaitTest() {
        ReentrantLockConditionTask reentrantLockConditionTask = new ReentrantLockConditionTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockConditionTask.service();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockConditionTask.service2();
            }
        });

        t1.setName("A");
        t2.setName("B");

        t1.start();
        t2.start();
    }

    public void reentrantLockConditionAwaitSingalTest() {
        ReentrantLockConditionTask reentrantLockConditionTask = new ReentrantLockConditionTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockConditionTask.service();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockConditionTask.service3();
            }
        });

        t1.setName("A");
        t2.setName("B");

        t1.start();
        t2.start();
    }

    public void reentrantLockMultiConditionAwaitSingalTest() throws InterruptedException {
        Lock locker = new ReentrantLock();
        Condition conditionA = locker.newCondition();
        Condition conditionB = locker.newCondition();
        Condition conditionC = locker.newCondition();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    locker.lock();
                    System.out.println("A start");
                    conditionA.await();
                    System.out.println("A end");
                    locker.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    locker.lock();
                    System.out.println("B start");
                    conditionB.await();
                    System.out.println("B end");
                    locker.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    locker.lock();
                    System.out.println("C start");
                    conditionA.signalAll();
                    System.out.println("C end");
                    locker.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        Thread.sleep(1000);
        t2.start();
        Thread.sleep(1000);
        t3.start();

    }
}

class ReentrantLockConditionTask {
    private Lock locker = new ReentrantLock();
    private Condition condition = locker.newCondition();

    public void service() {
        try {
            locker.lock();
            for (int i = 0; i < 10; i++) {
                if (i==5) {
                    condition.await();
                }
                System.out.println(Thread.currentThread().getName() + " do service " + i);
                Thread.sleep(1000);
            }
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service2() {
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

    public void service3() {
        try {
            locker.lock();
            for (int i = 0; i < 10; i++) {
                if (i==5) {
                    condition.signal();
                }
                System.out.println(Thread.currentThread().getName() + " do service " + i);
                Thread.sleep(1000);
            }
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}