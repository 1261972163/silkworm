package com.jengine.common.j2se.concurrent.synchronoustools;

import org.junit.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. 同一时刻多个线程访问同一资源时，Semaphore用来限制访问此资源的最大线程数目。
 * 2. 通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 * 3. release具有叠加效应，可以先执行几次release，增加许可数目
 * @author nouuid
 * @date 4/29/2016
 * @description
 */
public class SemaphoreRunner {

    @Test
    public void acquire() throws InterruptedException {
        Semaphore semaphore = new Semaphore(2);
        AtomicInteger count = new AtomicInteger(0);
        for (int i=1; i<=10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        count.incrementAndGet();
                        // Semaphore限制了只能有2个线程acquire到许可，只打印t1和t2
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t"+i);
            thread.start();
        }
        Thread.sleep(3000);
        // 只有2个线程可以进入semaphore.acquire()，打印2
        System.out.println(count.get());
    }

    @Test
    public void release() throws InterruptedException {
        Semaphore semaphore = new Semaphore(2);
        // 3. release具有叠加效应，可以先执行几次release，增加许可数目
        semaphore.release();
        semaphore.release();
        AtomicInteger count = new AtomicInteger(0);
        for (int i=1; i<=10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        count.incrementAndGet();
                        // Semaphore限制了只能有4个线程acquire到许可，只打印t1、t2、t3和t4
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t"+i);
            thread.start();
            Thread.sleep(100);
        }
        Thread.sleep(3000);
        // 只有4个线程可以进入semaphore.acquire()，打印4
        System.out.println(count.get());
    }
}

