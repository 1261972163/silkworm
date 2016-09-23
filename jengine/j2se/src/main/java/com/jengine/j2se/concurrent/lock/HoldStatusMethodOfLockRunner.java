package com.jengine.j2se.concurrent.lock;

import junit.framework.Assert;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nouuid
 * @date 4/7/2016
 * @description
 * lock.isFair(): is this lock a fair lock
 * lock.isHeldByCurrentThread(): is this lock locked by current thread
 * lock.isLocked(): is this lock locked by any threads
 */
public class HoldStatusMethodOfLockRunner {

    public void isFairTest() {
        HoldStatusMethodOfLockService holdStatusMethodOfLockService = new HoldStatusMethodOfLockService();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                holdStatusMethodOfLockService.service1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                holdStatusMethodOfLockService.service2();
            }
        });

        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        t2.start();
    }

    public void isHeldByCurrentThreadTest() {
        HoldStatusMethodOfLockService holdStatusMethodOfLockService = new HoldStatusMethodOfLockService();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                holdStatusMethodOfLockService.service3();
            }
        });

        t1.setName("t1");
        t1.start();
    }

    public void isLockedTest() throws InterruptedException {
        HoldStatusMethodOfLockService holdStatusMethodOfLockService = new HoldStatusMethodOfLockService();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                holdStatusMethodOfLockService.service4();
            }
        });

        t1.setName("t1");
        System.out.println("t1, before start, lock isLocked=" + holdStatusMethodOfLockService.getFairReentrantLock().isLocked());
        t1.start();
        Thread.sleep(1000);
        System.out.println("t1, start, lock, lock isLocked=" + holdStatusMethodOfLockService.getFairReentrantLock().isLocked());
        Thread.sleep(10000);
        System.out.println("t1, unlock, lock isLocked=" + holdStatusMethodOfLockService.getFairReentrantLock().isLocked());
    }
}

class HoldStatusMethodOfLockService {
    private ReentrantLock fairReentrantLock = new ReentrantLock(true);
    private ReentrantLock nofairReentrantLock = new ReentrantLock(false);

    public ReentrantLock getFairReentrantLock() {
        return fairReentrantLock;
    }

    public void service1() {
        System.out.println(Thread.currentThread().getName() + ", fairReentrantLock=" + fairReentrantLock.isFair());
        Assert.assertEquals(true, fairReentrantLock.isFair());
    }

    public void service2() {
        System.out.println(Thread.currentThread().getName() + ", nofairReentrantLock=" + nofairReentrantLock.isFair());
        Assert.assertEquals(false, nofairReentrantLock.isFair());
    }

    public void service3() {
        fairReentrantLock.lock();
        System.out.println(Thread.currentThread().getName() + ", fairReentrantLock isHeldByCurrentThread=" + fairReentrantLock.isHeldByCurrentThread());
        Assert.assertEquals(true, fairReentrantLock.isHeldByCurrentThread());

        System.out.println(Thread.currentThread().getName() + ", nofairReentrantLock isHeldByCurrentThread=" + nofairReentrantLock.isHeldByCurrentThread());
        Assert.assertEquals(false, nofairReentrantLock.isHeldByCurrentThread());
        fairReentrantLock.unlock();
    }

    public void service4() {
        try {
            fairReentrantLock.lock();
            System.out.print("                                                    ");
            System.out.println(Thread.currentThread().getName() + " do service4");
            Thread.sleep(5*1000);
            fairReentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
