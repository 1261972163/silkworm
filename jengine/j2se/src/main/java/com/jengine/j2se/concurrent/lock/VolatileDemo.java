package com.jengine.j2se.concurrent.lock;

import com.jengine.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. 普通变量不具有可见性，加volatile后变量可见
 * 2. volatile只保证可见性，无法保证线程安全，需要借助线程安全工具实现线程安全
 *
 * @author nouuid
 * @date 4/1/2016
 * @description
 */
public class VolatileDemo extends ConcurrentTest {

    // 1. 普通变量不具有可见性，加volatile后变量可见
    @Test
    public void deadLoop() throws InterruptedException {
        class DeadLoopRunnableTask implements Runnable {
            public volatile boolean flag = true;

            @Override
            public void run() {
                int i = 0;
                while (flag) {
                    System.out.println("i=" + i++);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        DeadLoopRunnableTask deadLoopRunnableTask = new DeadLoopRunnableTask();
        Thread thread = new Thread(deadLoopRunnableTask);
        thread.start();
        Thread.sleep(5 * 1000);
        deadLoopRunnableTask.flag = false;
        Thread.sleep(5 * 1000);
    }

    // 2. volatile只保证可见性，无法保证线程安全
    @Test
    public void unsafe() throws InterruptedException {
        class Counter {
            private volatile int count = 0;

            public void add1() {
                count += 1;
            }

            public void add2() {
                count += 2;
            }

            public int get() {
                return count;
            }
        }

        Counter counter = new Counter();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    counter.add1();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    counter.add2();
                }
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(5000);
        System.out.println(counter.get());
    }

    // 3. 使用AtomicInteger保证线程安全
    @Test
    public void safe() throws InterruptedException {
        class Counter {
            private volatile AtomicInteger count = new AtomicInteger(0);

            public void add1() {
                count.addAndGet(1);
            }

            public void add2() {
                count.addAndGet(2);
            }

            public int get() {
                return count.get();
            }
        }

        Counter counter = new Counter();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    counter.add1();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    counter.add2();
                }
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(5000);
        System.out.println(counter.get());
    }
}
