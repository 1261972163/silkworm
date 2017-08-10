package com.jengine.common2.j2se.concurrent.threadCommunication;

import com.jengine.common2.j2se.concurrent.ConcurrentTest;
import junit.framework.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 *
 * 1. ThreadLocal目的：【变量线程隔离】
 * 2. ThreadLocal原理：
 *      在ThreadLocal类中有一个Map，用于存储每一个线程的变量副本，
 *      Map中元素的键为线程对象，而值对应线程的变量副本。
 *
 * 3. set方法：设置当前线程的线程局部变量的值。
 * 4. get方法： 返回当前线程所对应的线程局部变量。
 * 5. remove方法 将当前线程局部变量的值删除
 *      目的是为了减少内存的占用，该方法是JDK 5.0新增的方法。
 *      需要指出的是，当线程结束后，对应该线程的局部变量将自动被垃圾回收，
 *      所以显式调用该方法清除线程的局部变量并不是必须的操作，但它可以加快内存回收的速度。
 * 6. initialValue方法
 *      在未使用其他方法的前提下，初次使用get方法时会调用initialValue方法
 *      默认的initialValue方法直接返回null
 * 7. 静态ThreadLocal
 *      使用static ThreadLocal，同线程该类所有对象的ThreadLocal为同一个
 *      直接使用ThreadLocal，同线程该类所有对象的ThreadLocal不同
 * 8. InheritableThreadLocal 子线程获取父线程继承下来的值
 *      需要注意：子线程取值x1的同时，主线程修改值为x2，则子线程获取的仍然是x1
 *
 *
 * @author nouuid
 * @date 1/17/2017
 * @since 0.1.0
 */
public class ThreadLocalDemo  extends ConcurrentTest {

    /**
     * 静态ThreadLocal
     * @throws InterruptedException
     */
    @Test
    public void staticThreadLocal() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountTask countTask1_1 = new CountTask("A", 1);
        CountTask countTask1_2 = new CountTask("B", 11);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i<100) {
                    countTask1_1.count();
                    countTask1_2.count();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                countTask1_1.getCurrent();
                countTask1_2.getCurrent();
                countDownLatch.countDown();
            }
        });
        thread.start();
        countDownLatch.await();
        Thread.sleep(1*1000);
        Assert.assertEquals(208, countTask1_1.getCurrentCount().intValue());
        Assert.assertEquals(208, countTask1_2.getCurrentCount().intValue());
    }

    /**
     * 局部ThreadLocal
     * @throws InterruptedException
     */
    @Test
    public void localThreadLocal() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        LocalThreadLocalTask LocalThreadLocalTask2_1 = new LocalThreadLocalTask("A", 1);
        LocalThreadLocalTask LocalThreadLocalTask2_2 = new LocalThreadLocalTask("B", 11);

        LocalThreadLocalRunner localThreadLocalRunner = new LocalThreadLocalRunner(LocalThreadLocalTask2_1, LocalThreadLocalTask2_2, countDownLatch);
        Thread thread = new Thread(localThreadLocalRunner);
        thread.start();
        countDownLatch.await();
        Thread.sleep(1*1000);
        Assert.assertEquals(100, localThreadLocalRunner.getR1().intValue());
        Assert.assertEquals(110, localThreadLocalRunner.getR2().intValue());
    }

    @Test
    public void inheritableThreadLocal() throws InterruptedException {
        InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<Integer>() {
            public Integer initialValue() {
                return 0;
            }
        };
        threadLocal.set(5);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Assert.assertEquals(5, threadLocal.get().intValue());
            }
        });
        thread1.start();
        thread1.join();
    }
}

class LocalThreadLocalRunner implements Runnable {
    CountDownLatch countDownLatch;

    LocalThreadLocalTask localThreadLocalTask2_1;
    LocalThreadLocalTask localThreadLocalTask2_2;

    Integer r1;
    Integer r2;

    public LocalThreadLocalRunner(LocalThreadLocalTask localThreadLocalTask2_1, LocalThreadLocalTask localThreadLocalTask2_2, CountDownLatch countDownLatch) {
        this.localThreadLocalTask2_1 = localThreadLocalTask2_1;
        this.localThreadLocalTask2_2 = localThreadLocalTask2_2;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        int i = 1;
        while (i < 100) {
            localThreadLocalTask2_1.count();
            localThreadLocalTask2_2.count();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        r1 = localThreadLocalTask2_1.getCurrent();
        r2 = localThreadLocalTask2_2.getCurrent();
        countDownLatch.countDown();
    }

    public Integer getR1() {
        return r1;
    }

    public Integer getR2() {
        return r2;
    }
}

class CountTask {

    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
        }
    };
    private String name;
    private boolean first = true;
    private int init;

    private Integer currentCount;

    public CountTask(String name, int init) {
        this.name = name;
        this.init = init;
    }

    public void count() {
        if (first) {
            threadLocal.set(new Integer(init));
            System.out.println("init:" + name + "-" + threadLocal.get());
            first = false;
        }

        Integer x = threadLocal.get();
        x = x + 1;
        threadLocal.set(x);
        System.out.println(name + "-" + x);
    }

    public void getCurrent() {
        currentCount = threadLocal.get();
    }

    public Integer getCurrentCount() {
        return currentCount;
    }
}

class LocalThreadLocalTask {

    private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        public Integer initialValue() {
            return 0;
        }
    };

    private String name;
    private boolean first = true;
    private int init;

    public LocalThreadLocalTask(String name, int init) {
        this.name = name;
        this.init = init;
    }

    public void count() {
        if (first) {
            threadLocal.set(new Integer(init));
            System.out.println("init:" + name + "-" + threadLocal.get());
            first = false;
        }

        Integer x = threadLocal.get();
        x = x + 1;
        threadLocal.set(x);
        System.out.println(name + "-" + x);
    }

    public Integer getCurrent() {
        return threadLocal.get();
    }
}

