package com.jengine.j2se.concurrent.execute;

import org.junit.Test;
import java.util.concurrent.Executors;
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
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("start1");
            }
        }, 50, TimeUnit.MICROSECONDS);

        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("start2");
            }
        }, 100, TimeUnit.MICROSECONDS);

        System.out.println("1");

        Thread.sleep(5000);
        System.out.println("2");
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
}
