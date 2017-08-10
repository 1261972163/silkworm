package com.jengine.common2.j2se.concurrent.synchronoustools;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 1. CyclicBarrier 指定数量线程进入await后，执行一次任务
 * 2. 使用ReentrantLock实现，原理：
 *      CyclicBarrier初始化时指定等待的await总数量count 和 要执行的任务barrierCommand
 *      每一次CyclicBarrier.await()，获取共享锁ReentrantLock
 *      获取锁后，index=--count，看index是否等于0
 *      index不等于0，当前线程trip.await()
 *      index等于0，调用barrierCommand的run方法，最后唤醒所有trip.await()的线程
 *
 * @author nouuid
 * @date 4/29/2016
 * @description
 */
public class BarrierDemo {
    AtomicLong count = new AtomicLong(0);

    @Test
    public void test() throws InterruptedException {
        // 10个线程，2个栅栏一次，打印5
        barrier(10, 2);
        Thread.sleep(10);
        System.out.println(count.get());

        // 10个线程，1个栅栏一次，打印10
        barrier(10, 2);
        Thread.sleep(10);
        System.out.println(count.get());

    }

    private void barrier(int tn, int barrierNum) throws InterruptedException {
        // barrierNum个线程barrier一次
        final CyclicBarrier barrier = new CyclicBarrier(barrierNum, new Runnable() {
            @Override
            public void run() {
                count.incrementAndGet();
            }
        });
        ThreadGroup threadGroup = new ThreadGroup("myGroup");
        for (int i=1; i<=tn; i++) {
            Thread thread = new Thread(threadGroup, new Runnable() {
                @Override
                public void run() {
                    try {
                        barrier.await();
//                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }, "t" + i);
            thread.start();
        };
        while (threadGroup.activeCount() > 0) {
            Thread.sleep(10);
        }
    }


}

