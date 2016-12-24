package com.jengine.j2se.concurrent.execute;

import junit.framework.Assert;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * content
 *
 * @author bl07637
 * @date 12/7/2016
 * @since 0.1.0
 */
public class ScheduledExecutorServiceDemo {

    /**
     * 只执行一次
     * public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit)
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(new Runnable() {

            @Override
            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("2");
            }
        }, 5000, TimeUnit.MILLISECONDS);

        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("3");
            }
        }, 10000, TimeUnit.MILLISECONDS);

        System.out.println("1");
        Thread.sleep(6000);
        scheduledExecutorService.shutdown();
//        scheduledExecutorService.shutdownNow();
        System.out.println("4");
        System.out.println("isShutdown:" + scheduledExecutorService.isShutdown());
        System.out.println("isTerminated:" + scheduledExecutorService.isTerminated());

        Thread.sleep(15000);
        System.out.println("5");
        System.out.println("isShutdown:" + scheduledExecutorService.isShutdown());
        System.out.println("isTerminated:" + scheduledExecutorService.isTerminated());

        try {
            scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("6");
                }
            }, 1000, TimeUnit.MILLISECONDS);

            Thread.sleep(5000);
            System.out.println("7");
            System.out.println("isShutdown:" + scheduledExecutorService.isShutdown());
            System.out.println("isTerminated:" + scheduledExecutorService.isTerminated());
        } catch (Exception e) {
            if (e instanceof RejectedExecutionException) {
                Assert.assertTrue(true);
            }
        }
    }

    /**
     * 循环执行
     */
    @Test
    public void test2() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("start1");
            }
        }, 50, 50, TimeUnit.MICROSECONDS);

        System.out.println("1");
        Thread.sleep(1000);
        System.out.println("2");
        scheduledExecutorService.shutdown();
    }

    /**
     * newScheduledThreadPool(1)执行多个任务时单线程顺序执行
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println("3：" + System.currentTimeMillis());
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("4：" + System.currentTimeMillis());
            }
        }, 1000, TimeUnit.MICROSECONDS);
        System.out.println("1：" + System.currentTimeMillis());

        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("5：" + System.currentTimeMillis());
            }
        }, 2000, TimeUnit.MICROSECONDS);

        System.out.println("2：" + System.currentTimeMillis());

        Thread.sleep(10000);
        System.out.println("6：" + System.currentTimeMillis());
    }

    /**
     * newScheduledThreadPool(2)执行多个任务时2个线程并发执行
     * @throws InterruptedException
     */
    @Test
    public void test4() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println("3：" + System.currentTimeMillis());
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("4：" + System.currentTimeMillis());
            }
        }, 1000, TimeUnit.MICROSECONDS);
        System.out.println("1：" + System.currentTimeMillis());

        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("5：" + System.currentTimeMillis());
            }
        }, 2000, TimeUnit.MICROSECONDS);

        System.out.println("2：" + System.currentTimeMillis());

        Thread.sleep(10000);
        System.out.println("6：" + System.currentTimeMillis());
    }
}
