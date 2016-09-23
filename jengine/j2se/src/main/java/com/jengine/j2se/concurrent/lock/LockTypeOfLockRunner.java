package com.jengine.j2se.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nouuid
 * @date 4/7/2016
 * @description
 * lock.lockInterruptibly(): lock which can be interrupted
 * lock.tryLock(): lock occurs when the lock was not held by others
 * lock.tryLock(timeout, timeunit): lock occurs when the lock was not held by others in provided times
 */
public class LockTypeOfLockRunner {

    public void lockInterruptiblyTest1() throws InterruptedException {
        LockTypeOfLockService lockTypeOfLockService = new LockTypeOfLockService();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lockTypeOfLockService.service1();
            }
        });

        thread.setName("T1");
        thread.start();
        thread.interrupt();
    }

    public void lockInterruptiblyTest2() throws InterruptedException {
        LockTypeOfLockService lockTypeOfLockService = new LockTypeOfLockService();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lockTypeOfLockService.service2();
            }
        });

        thread.setName("T1");
        thread.start();
        thread.interrupt();
    }

    public void tryLockTest() throws InterruptedException {
        LockTypeOfLockService lockTypeOfLockService = new LockTypeOfLockService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lockTypeOfLockService.service3();
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

    public void timedTryLockTest() throws InterruptedException {
        LockTypeOfLockService lockTypeOfLockService = new LockTypeOfLockService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lockTypeOfLockService.service4();
            }
        };

        Thread threadA = new Thread(runnable);
        Thread threadB = new Thread(runnable);

        threadA.setName("TA");
        threadB.setName("TB");

        threadA.start();
        Thread.sleep(1 * 1000);
        threadB.start();
    }

    public void timedTryLockTest2() throws InterruptedException {
        LockTypeOfLockService lockTypeOfLockService = new LockTypeOfLockService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lockTypeOfLockService.service5();
            }
        };

        Thread threadA = new Thread(runnable);
        Thread threadB = new Thread(runnable);

        threadA.setName("TA");
        threadB.setName("TB");

        threadA.start();
        Thread.sleep(1 * 1000);
        threadB.start();
    }
}

class LockTypeOfLockService {

    private ReentrantLock reentrantLock = new ReentrantLock();

    public void service1() {
        try {
            reentrantLock.lock();

            System.out.println(Thread.currentThread().getName() + " lock begin");
            for (int i=0; i<Integer.MAX_VALUE/10; i++) {
                Math.random();
            }
            System.out.println(Thread.currentThread().getName() + " lock end");

            reentrantLock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void service2() {
        try {
            reentrantLock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " lock begin");
            for (int i=0; i<Integer.MAX_VALUE/10; i++) {
                Math.random();
            }
            System.out.println(Thread.currentThread().getName() + " lock end");

            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service3() {
        try {
            if (reentrantLock.tryLock()) {
                System.out.println(Thread.currentThread().getName() + " get lock");
            } else {
                System.out.println(Thread.currentThread().getName() + " don't get lock");
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service4() {
        try {
            System.out.println(Thread.currentThread().getName() + " invoke time=" + System.currentTimeMillis());
            if (reentrantLock.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " get lock time=" + System.currentTimeMillis());
                Thread.sleep(2 * 1000);
            } else {
                System.out.println(Thread.currentThread().getName() + " don't get lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (reentrantLock.isHeldByCurrentThread()) {
                reentrantLock.unlock();
            }
        }
    }

    public void service5() {
        try {
            System.out.println(Thread.currentThread().getName() + " invoke time=" + System.currentTimeMillis());
            if (reentrantLock.tryLock(2, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " get lock time=" + System.currentTimeMillis());
                Thread.sleep(2 * 1000);
            } else {
                System.out.println(Thread.currentThread().getName() + " don't get lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (reentrantLock.isHeldByCurrentThread()) {
                reentrantLock.unlock();
            }
        }
    }
}