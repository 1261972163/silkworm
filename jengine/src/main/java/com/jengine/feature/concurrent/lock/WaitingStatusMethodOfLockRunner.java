package com.jengine.feature.concurrent.lock;

import junit.framework.Assert;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nouuid
 * @date 4/6/2016
 * @description
 *
 * lock.hasQueuedThread(thread): is the specified thread waiting for holding this lock
 * lock.hasQueuedThreads(): is there any thread waiting for holding this lock
 * lock.hasWaiters(): is there any thread waiting for the condition related to this lock
 */
public class WaitingStatusMethodOfLockRunner {

    public void hasQueuedThreadTest() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        WaitingStatusMethodOfLockService waitingStatusMethodOfLockService = new WaitingStatusMethodOfLockService(reentrantLock);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitingStatusMethodOfLockService.service1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitingStatusMethodOfLockService.service2();
            }
        });

        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        System.out.println("t2, before start, hasQueuedThread=" + reentrantLock.hasQueuedThread(t2));
        Assert.assertEquals(false, reentrantLock.hasQueuedThread(t2));
        Thread.sleep(1000);
        t2.start();
        System.out.println("t2, started, before lock, hasQueuedThread=" + reentrantLock.hasQueuedThread(t2));
        Assert.assertEquals(false, reentrantLock.hasQueuedThread(t2));
        Thread.sleep(3*1000);
        System.out.println("t2, started, lock, hasQueuedThread=" + reentrantLock.hasQueuedThread(t2));
        Assert.assertEquals(true, reentrantLock.hasQueuedThread(t2));
        Thread.sleep(10*1000);
        System.out.println("t2, ended, unlock, hasQueuedThread=" + reentrantLock.hasQueuedThread(t2));
        Assert.assertEquals(false, reentrantLock.hasQueuedThread(t2));
    }

    public void hasQueuedThreadsTest() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        WaitingStatusMethodOfLockService waitingStatusMethodOfLockService = new WaitingStatusMethodOfLockService(reentrantLock);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitingStatusMethodOfLockService.service1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitingStatusMethodOfLockService.service2();
            }
        });

        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        System.out.println("t2, before start, hasQueuedThreads=" + reentrantLock.hasQueuedThreads());
        Assert.assertEquals(false, reentrantLock.hasQueuedThreads());
        Thread.sleep(1000);
        t2.start();
        System.out.println("t2, started, before lock, hasQueuedThreads=" + reentrantLock.hasQueuedThreads());
        Assert.assertEquals(false, reentrantLock.hasQueuedThreads());
        Thread.sleep(3*1000);
        System.out.println("t2, started, lock, hasQueuedThreads=" + reentrantLock.hasQueuedThreads());
        Assert.assertEquals(true, reentrantLock.hasQueuedThreads());
        Thread.sleep(10*1000);
        System.out.println("t2, ended, unlock, hasQueuedThreads=" + reentrantLock.hasQueuedThreads());
        Assert.assertEquals(false, reentrantLock.hasQueuedThreads());
    }

    public void hasWaitersTest() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        WaitingStatusMethodOfLockService statusMethodOfLockService = new WaitingStatusMethodOfLockService(reentrantLock);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                statusMethodOfLockService.service3();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                statusMethodOfLockService.service4();
            }
        });

        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        Thread.sleep(1000);
        t2.start();
    }
}

class WaitingStatusMethodOfLockService {

    private ReentrantLock reentrantLock;

    private Condition condition;

    public WaitingStatusMethodOfLockService(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
        condition = reentrantLock.newCondition();
    }

    public void service1() {
        try {
            reentrantLock.lock();
            System.out.print("                                                    ");
            System.out.println(Thread.currentThread().getName() + " do service1");
            Thread.sleep(10*1000);
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service2() {
        try {
            Thread.sleep(2*1000);
            reentrantLock.lock();
            System.out.print("                                                    ");
            System.out.println(Thread.currentThread().getName() + " do service1");
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service3() {
        try {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName() + " is waiting");
            condition.await();
            System.out.print("                                                    ");
            System.out.println(Thread.currentThread().getName() + " do service3");
            System.out.println("hasWaiters=" + reentrantLock.hasWaiters(condition));
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service4() {
        try {
            reentrantLock.lock();
            System.out.print("                                                    ");
            System.out.println(Thread.currentThread().getName() + " do service4");
            System.out.println("hasWaiters=" + reentrantLock.hasWaiters(condition));
            Thread.sleep(10*1000);
            condition.signal();
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}