package com.jengine.common.j2se.concurrent.lock;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * 1. LockSupport提供了基本的线程同步原语。
 * 2. 线程调用park函数则等待“许可”。
 * 3. unpark函数为线程提供“许可(permit)”
 * 4. “许可”是不能叠加，是一次性的，
 *      比如线程B连续调用了三次unpark函数，当线程A调用park函数就使用掉这个“许可”，如果线程A再次调用park，则进入等待状态。
 *
 * @author nouuid
 * @date 5/10/2017
 * @since 0.1.0
 */
public class LockSupportDemo {

    @Test
    public void test() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // t1等待许可
                LockSupport.park(this);
                System.out.println(Thread.currentThread().getName());
            }
        }, "t1");
        t1.start();
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName());
        // 为t1提供许可
        LockSupport.unpark(t1);
        Thread.sleep(3000);
    }
}
