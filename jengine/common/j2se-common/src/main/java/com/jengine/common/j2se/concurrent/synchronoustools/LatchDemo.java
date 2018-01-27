package com.jengine.common.j2se.concurrent.synchronoustools;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 1. Latch闭锁，允许1或N个线程等待其他线程完成执行
 * 2. 通过共享锁实现，原理：
 *      初始化传入AQS的state值
 *      线程调用await方法时会检查state的值是否为0
 *      如果是就直接返回
 *      如果不是，将线程加入到等待队列，然后通过LockSupport.park(this)将自身阻塞。
 *      当其它线程调用countDown方法会将计数器减1，然后判断计数器的值是否为0，当它为0时，会唤醒队列中的第一个节点。
 *      由于CountDownLatch使用了AQS的共享模式，所以第一个节点被唤醒后又会唤醒第二个节点，以此类推，使得所有因await方法阻塞的线程都能被唤醒而继续执行。
 *
 * @author nouuid
 * @date 4/29/2016
 * @description
 */
public class LatchDemo {

    @Test
    public void test() throws InterruptedException {
        AtomicLong count = new AtomicLong(0);
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(10);
        for (int i=1; i<=10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 1. 所有线程堵在这
                        startGate.await();
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(500);
                        count.incrementAndGet();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 4. 主线程可以运行了
                        endGate.countDown();
                    }
                }
            }, "t"+i);
            thread.start();
        };
        // 先打印main
        System.out.println(Thread.currentThread().getName());
        // 2. 前面10个线程可以运行了
        startGate.countDown();
        // 3. 主线程堵在这，前面10个线程
        endGate.await();
        System.out.println(count.get());
    }


}

