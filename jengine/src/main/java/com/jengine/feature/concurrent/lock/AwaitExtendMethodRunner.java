package com.jengine.feature.concurrent.lock;

import java.util.Calendar;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author bl07637
 * @date 4/7/2016
 * @description
 * awaitUninterruptibly()
 * awaitUntil(date)
 */
public class AwaitExtendMethodRunner {

    public void awaitUninterruptiblyTest() throws InterruptedException {
        AwaitExtendMethodService awaitExtendMethodService = new AwaitExtendMethodService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                awaitExtendMethodService.service1();
            }
        };
        Thread t1 = new Thread(runnable);
        t1.start();

        Thread.sleep(2*1000);
        System.out.println("before interrupt");
        t1.interrupt();
        System.out.println("after interrupt");
    }

    public void awaitUninterruptiblyTest2() throws InterruptedException {
        AwaitExtendMethodService awaitExtendMethodService = new AwaitExtendMethodService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                awaitExtendMethodService.service2();
            }
        };
        Thread t1 = new Thread(runnable);
        t1.start();

        Thread.sleep(2*1000);
        System.out.println("before interrupt");
        t1.interrupt();
    }

    public void awaitUtilTest() throws InterruptedException {
        AwaitExtendMethodService awaitExtendMethodService = new AwaitExtendMethodService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                awaitExtendMethodService.service3();
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t1.setName("T1");
        t2.setName("T2");

        t1.start();
        Thread.sleep(2*1000);
        t2.start();
    }

    public void awaitUtilTest2() throws InterruptedException {
        AwaitExtendMethodService awaitExtendMethodService = new AwaitExtendMethodService();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                awaitExtendMethodService.service3();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                awaitExtendMethodService.service4();
            }
        });

        t1.setName("T1");
        t2.setName("T2");

        t1.start();
        Thread.sleep(3*1000);
        t2.start();
    }
}

class AwaitExtendMethodService {

    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();

    public void service1() {
        try {
            reentrantLock.lock();
            condition.await();
            System.out.println(Thread.currentThread().getName() + " do service1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void service2() {
        reentrantLock.lock();
        condition.awaitUninterruptibly();
        System.out.println(Thread.currentThread().getName() + " do service2");
        reentrantLock.unlock();
    }

    public void service3() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);
        try {
            reentrantLock.lock();
            long start = System.currentTimeMillis();
            condition.awaitUntil(calendar.getTime());
            long end = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " cost " + (end-start)/1000);
            System.out.println(Thread.currentThread().getName() + " do service3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void service4() {
        try {
            reentrantLock.lock();
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + " do service4");
        } finally {
            reentrantLock.unlock();
        }
    }
}


