package com.jengine.common.j2se.concurrent.execute;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * content
 *
 * @author nouuid
 * @date 12/7/2016
 * @since 0.1.0
 */
public class ScheduledExecutorServiceDemo {

    // 1. scheduledExecutorService.schedule()指定一段时间后执行，只执行一次
    @Test
    public void schedule() throws InterruptedException {
        Date start = new Date();
        Date end = new Date();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
                end.setTime(System.currentTimeMillis());
            }
        }, 1000, TimeUnit.MILLISECONDS);
        Thread.sleep(2000);
        System.out.println((end.getTime() - start.getTime()));
    }

    // 2. newScheduledThreadPool(1)执行多个任务时单线程顺序执行
    // 多个计划任务按启动时间执行
    @Test
    public void scheduleStartByTime() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("2");
            }
        }, 3000, TimeUnit.MILLISECONDS);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
            }
        }, 1000, TimeUnit.MILLISECONDS);

        Thread.sleep(4000);
    }

    // 3. newScheduledThreadPool(2)执行多个任务时2个线程并发执行
    @Test
    public void scheduleStartConcurrent() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i<10) {
                    System.out.println("1");
                    i++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 500, TimeUnit.MICROSECONDS);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i<10) {
                    System.out.println("   2");
                    i++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 510, TimeUnit.MICROSECONDS);

        Thread.sleep(3*1000);
        System.out.println("end");
    }

    // 4. scheduledExecutorService.scheduleWithFixedDelay按照固定延迟循环执行
    // initialDelay初始延迟时间
    // delay每次执行的间隔时间
    // shutdown可以关闭循环
    @Test
    public void scheduleWithFixedDelay() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("do");
            }
        }, 0, 1, TimeUnit.SECONDS);
        Thread.sleep(2000);
        scheduledExecutorService.shutdown();
        Thread.sleep(1000);
    }


}
