package com.jengine.feature.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nouuid
 * @date 4/6/2016
 * @description
 *
 * lock.getHoldCount(): count number of threads which hold this lock
 * lock.getQueueLength(): count number of threads which is waiting for holding this lock
 * lock.getWaitQueueLength(condition): count number of threads which is waiting for condition related to this lock
 */
public class CountMethodOfLockRunner {

    public void getHoldCountTest() {
        GetHoldCountService getHoldCountService = new GetHoldCountService();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getHoldCountService.service1();
            }
        });
        thread.start();
    }

    public void getQueueLengthTest() {
        GetQueueLengthService getQueueLengthService = new GetQueueLengthService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getQueueLengthService.service1();
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

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getQueueLength=" + getQueueLengthService.getLocker().getQueueLength());
    }

    public void getWaitQueueLengthServiceTest() {
        GetWaitQueueLengthService getWaitQueueLengthService = new GetWaitQueueLengthService();
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                getWaitQueueLengthService.service1();
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                getWaitQueueLengthService.service2();
            }
        };

        Thread[] threads1 = new Thread[10];
        for (int i=0; i<10; i++) {
            threads1[i] = new Thread(runnable1);
            threads1[i].setName("AT" + i);
        }

        Thread[] threads2 = new Thread[10];
        for (int i=0; i<10; i++) {
            threads2[i] = new Thread(runnable2);
            threads2[i].setName("BT" + i);
        }

        for (int i=0; i<10; i++) {
            threads1[i].start();
            threads2[i].start();
        }
    }
}

class GetHoldCountService {

    private ReentrantLock locker = new ReentrantLock();

    public void service1() {
        locker.lock();
        System.out.println("service1 getHoldCount=" + locker.getHoldCount());
        service2();
        locker.unlock();
    }

    public void service2() {
        locker.lock();
        System.out.println("service2 getHoldCount=" + locker.getHoldCount());
        locker.unlock();
    }
}

class GetQueueLengthService {
    private ReentrantLock locker = new ReentrantLock();

    public ReentrantLock getLocker() {
        return locker;
    }

    public void setLocker(ReentrantLock locker) {
        this.locker = locker;
    }

    public void service1() {
        try {
            locker.lock();
            System.out.println(Thread.currentThread().getName() + " do service1");
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }
}

class GetWaitQueueLengthService {
    private ReentrantLock locker = new ReentrantLock();
    private Condition conditionA = locker.newCondition();
    private Condition conditionB = locker.newCondition();

    public Condition getConditionA() {
        return conditionA;
    }

    public void setConditionA(Condition conditionA) {
        this.conditionA = conditionA;
    }

    public ReentrantLock getLocker() {
        return locker;
    }

    public void setLocker(ReentrantLock locker) {
        this.locker = locker;
    }

    public void service1() {
        try {
            locker.lock();
            System.out.println("A getWaitQueueLength=" + locker.getWaitQueueLength(conditionA));
            conditionA.await();
            System.out.println(Thread.currentThread().getName() + " do service1");
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }

    public void service2() {
        try {
            locker.lock();
            System.out.println("                               B getWaitQueueLength=" + locker.getWaitQueueLength(conditionB));
            conditionA.signal();
            conditionB.await();
            System.out.println("                               "  + Thread.currentThread().getName() + " do service2");
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }
}