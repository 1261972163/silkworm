package com.jengine.common2.j2se.concurrent.thread;

import com.jengine.common2.j2se.concurrent.ConcurrentTest;
import junit.framework.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 1. Thread线程，执行Runnable任务，由start()启动。
 * 2. 一个线程的start方法只能调用一次，多次调用start方法会抛错java.lang.IllegalThreadStateException
 * 3. 一些方法
 *      Thread.currentThread().getName()    线程名称
 *      Thread.currentThread().getId()      线程id
 *      thread.isAlive()                    是否存活
 * 4. Thread.sleep(long)    当前线程睡眠一段时间
 * 5. interrupt()方法并不能终止线程。
 *      调用此方法仅仅是在当前中打一个停止的标记，并不是真的停止线程。
 *      但是如果线程处于等待状态或睡眠状态时，此方法的调用会导致线程抛出InterruptException异常，从而导致线程停止。
 * 6. 放弃当前的CPU资源，让给其他的任务去占用CPU资源。放弃的时间不确定，有可能刚放弃CPU资源，又立马获得。
 * 7. 高优先级的线程具有更高的运行概率，但优先级具有随机性，优先级高的线程不一定就先执行完
 * @author nouuid
 * @date 4/11/2016
 * @description
 */
public class ThreadDemo extends ConcurrentTest {

    // 2. 一个线程的start方法只能调用一次，多次调用start方法会抛错java.lang.IllegalThreadStateException
    @Test
    public void multiStart() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1");
            }
        });
        t.start();
        Thread.sleep(5000);
        // 不管前面启动的线程是否完成，都不能再次启动这个线程了
        t.start();

        Thread.sleep(5000);
    }

    // 3. 一些方法
    //       Thread.currentThread().getName()    线程名称
    //       Thread.currentThread().getId()      线程id
    //       thread.isAlive()                    是否存活
    @Test
    public void methods() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 线程名称
                Assert.assertEquals("t1", Thread.currentThread().getName());
                // 线程id
                System.out.println(Thread.currentThread().getId());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");
        thread.start();
        Thread.sleep(100);
        // 正在运行
        Assert.assertTrue(thread.isAlive());
    }

    // 5. interrupt()方法并不能终止线程。
    //      调用此方法仅仅是在当前中打一个停止的标记，并不是真的停止线程。
    //      但是如果线程处于等待状态或睡眠状态时，此方法的调用会导致线程抛出InterruptException异常，可能导致线程停止。
    volatile long count = 0;
    @Test
    public void interruptTest() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<100000000; i++) {
                    count++;
                }
            }
        });
        thread.start();
        // interrupt()方法并不能终止线程
        thread.interrupt();
        // thread没有被终止
        Assert.assertTrue(thread.isAlive());
        // thread没有运行结束，count不为100000000
        System.out.println(count);
        Thread.sleep(5*1000);
        // thread运行结束，count为100000000
        Assert.assertFalse(thread.isAlive());
        Assert.assertEquals(100000000, count);
    }

    // 5. 但是如果线程处于等待状态或睡眠状态时，interrupt()的调用会导致线程抛出InterruptException异常，可能导致线程停止。
    @Test
    public void interruptExceptionTest() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(100);
                        count++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        Thread.sleep(10);
        // interrupt()方法导致线程抛出InterruptException异常
        thread.interrupt();
        // 因为异常，线程跳出while，导致线程停止。
        long count1 = count;
        Thread.sleep(1000);
        long count2 = count;
        Assert.assertEquals(count1, count2);
        // thread处于终止状态
        Assert.assertFalse(thread.isAlive());
    }

    // 6. 放弃当前的CPU资源，让给其他的任务去占用CPU资源。放弃的时间不确定，有可能刚放弃CPU资源，又立马获得。
    @Test
    public void yeldTest() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int count = 0;
                long start = System.currentTimeMillis();
                for (int i=0; i<10000000; i++) {
                    Thread.yield();
                    count = count + 1;
                }
                long end = System.currentTimeMillis();
                System.out.println("cost=" + (end-start) + " mills");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(30*1000);
    }

    // 7. 高优先级的线程具有更高的运行概率，但优先级具有随机性，优先级高的线程不一定就先执行完
    volatile long count1 = 0;
    volatile long count2 = 0;
    volatile long count3 = 0;
    volatile boolean flag = true;
    @Test
    public void priorityTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i=1; i<=3; i++) {
            final int index = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while(flag) {
                        Math.random();
                        if (index==1) {
                            count1++;
                        } else if(index==2) {
                            count2++;
                        } else if(index==3) {
                            count3++;
                        }
                    }
                }
            }, "t"+i);
            if (index==1) {
                thread.setPriority(10);
            } else if(index==2) {
                thread.setPriority(5);
            } else if(index==3) {
                thread.setPriority(1);
            }
            thread.start();
        }
        countDownLatch.countDown();
        Thread.sleep(3*1000);
        flag = false;
        // 优先级具有随机性，优先级高的线程不一定就先执行完
        System.out.println(count1);
        System.out.println(count2);
        System.out.println(count3);
    }

    @Test
    public void test() throws InterruptedException {
        class Task implements Runnable {

            private volatile boolean flag = true;

            @Override
            public void run() {
                while(flag) {
                    System.out.println("in " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void stop() {
                System.out.println("stop " + Thread.currentThread().getName());
            }
        }
        Task task = new Task();
        Thread thread = new Thread(task, "t1");
        thread.start();

        Thread.sleep(5000);
        task.stop();
        Thread.sleep(5000);
        System.out.println("end");

    }
}
