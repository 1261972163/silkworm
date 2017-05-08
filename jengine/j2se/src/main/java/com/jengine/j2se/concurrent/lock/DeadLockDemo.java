package com.jengine.j2se.concurrent.lock;

import org.junit.Test;

/**
 * @author nouuid
 * @description
 * @date 5/7/17
 */
public class DeadLockDemo {

    // 利用synchronized写一个死锁
    @Test
    public void synchronizedDeadLock() throws InterruptedException {
        class DeadLockTask {
            Object o1 = new Object();
            Object o2 = new Object();

            public void m1() {
                synchronized (o1) {
                    System.out.println(Thread.currentThread().getName() + " hold o1");
                    try {
                        Thread.sleep(2*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        System.out.println(Thread.currentThread().getName() + " hold o2");
                    }
                }
            }

            public void m2() {
                synchronized (o2) {
                    System.out.println(Thread.currentThread().getName() + " hold o2");
                    try {
                        Thread.sleep(2*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {
                        System.out.println(Thread.currentThread().getName() + " hold o1");
                    }
                }
            }
        }

        DeadLockTask deadLockTask = new DeadLockTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                deadLockTask.m1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                deadLockTask.m2();
            }
        }, "t2");
        t1.start();
        Thread.sleep(1000);
        t2.start();
        Thread.sleep(5000);
    }


}


