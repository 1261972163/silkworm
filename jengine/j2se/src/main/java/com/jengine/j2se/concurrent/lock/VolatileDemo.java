package com.jengine.j2se.concurrent.lock;

import com.jengine.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author nouuid
 * @date 4/1/2016
 * @description
 */
public class VolatileDemo extends ConcurrentTest {

    /**
     * main中死循环
     * @throws InterruptedException
     */
    @Test
    public void deadLoop() throws InterruptedException {
        DeadLoopRunner deadLoopRunner = new DeadLoopRunner();
        deadLoopRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * 线程中死循环
     * @throws InterruptedException
     */
    @Test
    public void deadLoopRunnable() throws InterruptedException {
        DeadLoopRunnableRunner deadLoopRunnableRunner = new DeadLoopRunnableRunner();
        deadLoopRunnableRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * volatile后不再死循环
     * @throws InterruptedException
     */
    @Test
    public void deadLoopRunnable2() throws InterruptedException {
        DeadLoopRunnable2Runner deadLoopRunnable2Runner = new DeadLoopRunnable2Runner();
        deadLoopRunnable2Runner.test();
        Thread.sleep(10*1000);
    }

    /**
     * volatile修饰 static int可见，但int操作未加锁，非线程安全
     * 线程安全情况下打印出的数值以100为间隔
     * @throws InterruptedException
     */
    @Test
    public void volatileAtomicity() throws InterruptedException {
        VolatileAtomicityRunner volatileAtomicityRunner = new VolatileAtomicityRunner();
        volatileAtomicityRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * volatile修饰 static int可见，int操作加锁，线程安全
     * 线程安全情况下打印出的数值以100为间隔
     * @throws InterruptedException
     */
    @Test
    public void volatileAtomicityRunner2() throws InterruptedException {
        VolatileAtomicityRunner volatileAtomicityRunner = new VolatileAtomicityRunner();
        volatileAtomicityRunner.test2();
        Thread.sleep(10*1000);
    }

    /**
     * volatile修饰 int可见，但int操作未加锁，非线程安全
     * 线程安全情况下打印出的数值以100为间隔
     * @throws InterruptedException
     */
    @Test
    public void VolatileAtomicity2Runner() throws InterruptedException {
        VolatileAtomicity2Runner volatileAtomicity2Runner = new VolatileAtomicity2Runner();
        volatileAtomicity2Runner.test();
        Thread.sleep(10*1000);
    }

    /**
     * volatile修饰 int可见，int操作加锁，线程安全
     * 线程安全情况下打印出的数值以100为间隔
     * @throws InterruptedException
     */
    @Test
    public void VolatileAtomicity2Runner2() throws InterruptedException {
        VolatileAtomicity2Runner volatileAtomicity2Runner = new VolatileAtomicity2Runner();
        volatileAtomicity2Runner.test2();
        Thread.sleep(10*1000);
    }

    /**
     * 使用AtomicInteger保证线程安全
     * 线程安全情况下打印出的数值以1为间隔
     * @throws InterruptedException
     */
    @Test
    public void atomicInteger() throws InterruptedException {
        AtomicIntegerRunner atomicIntegerRunner = new AtomicIntegerRunner();
        atomicIntegerRunner.test();
        Thread.sleep(10*1000);
    }
}

//======================================================================================================================

class DeadLoopRunner {

    public void test() {
        DeadLoopTask deadLoopTask = new DeadLoopTask();
        System.out.println("begin");
        deadLoopTask.process();
        System.out.println("want to stop");
        deadLoopTask.setFlag(false);
        System.out.println("end");
    }

}

class DeadLoopTask {
    private boolean flag = true;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void process() {
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

//======================================================================================================================

/**
 * @author nouuid
 * @date 4/1/2016
 * @description
 *
 * occur dead loop when run on JVM64bit with -server configuration
 */
class DeadLoopRunnableRunner {

    public void test() {
        DeadLoopRunnableTask deadLoopRunnableTask = new DeadLoopRunnableTask();
        Thread t1 = new Thread(deadLoopRunnableTask);
        System.out.println("begin");
        t1.start();
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("want to stop");
        deadLoopRunnableTask.setFlag(false);
        System.out.println("end");
    }
}

class DeadLoopRunnableTask implements Runnable {
    private boolean flag = true;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void process() {
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

    @Override
    public void run() {
        process();
    }
}

//======================================================================================================================

class DeadLoopRunnable2Runner {
    public void test() {
        DeadLoopRunnable2Task deadLoopRunnable2Task = new DeadLoopRunnable2Task();
        Thread t1 = new Thread(deadLoopRunnable2Task);
        System.out.println("begin");
        t1.start();
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("want to stop");
        deadLoopRunnable2Task.setFlag(false);
        System.out.println("end");
    }
}

class DeadLoopRunnable2Task implements Runnable {
    volatile private boolean flag = true;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void process() {
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

    @Override
    public void run() {
        process();
    }
}

//======================================================================================================================

class VolatileAtomicityRunner {

    public void test() {
        VolatileAtomicityThread[] volatileAtomicityThreads = new VolatileAtomicityThread[100];
        for (int i=0; i<100; i++) {
            volatileAtomicityThreads[i] = new VolatileAtomicityThread();
        }

        for(VolatileAtomicityThread volatileAtomicityThread : volatileAtomicityThreads) {
            volatileAtomicityThread.start();
        }
    }

    public void test2() {
        VolatileAtomicityThread2[] volatileAtomicityThread2s = new VolatileAtomicityThread2[100];
        for (int i=0; i<100; i++) {
            volatileAtomicityThread2s[i] = new VolatileAtomicityThread2();
        }

        for(VolatileAtomicityThread2 volatileAtomicityThread2 : volatileAtomicityThread2s) {
            volatileAtomicityThread2.start();
        }
    }
}

class VolatileAtomicityThread extends Thread {
    volatile public static int count;

    private void add() {
        for (int i=0; i<100; i++) {
            count++;
        }
        System.out.println("count=" + count);
    }

    @Override
    public void run() {
        super.run();
        add();
    }
}

class VolatileAtomicityThread2 extends Thread {
    volatile public static int count;

    synchronized static private void add() {
        for (int i=0; i<100; i++) {
            count++;
        }
        System.out.println("count=" + count);
    }

    @Override
    public void run() {
        super.run();
        add();
    }
}

//======================================================================================================================

class VolatileAtomicity2Runner {
    public void test() {
        VolatileAtomicityTask volatileAtomicityTask = new VolatileAtomicityTask();

        Thread[] threads = new Thread[100];
        for (int i=0; i<100; i++) {
            threads[i] = new Thread(volatileAtomicityTask);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void test2() {
        VolatileAtomicityTask2 volatileAtomicityTask2 = new VolatileAtomicityTask2();

        Thread[] threads = new Thread[100];
        for (int i=0; i<100; i++) {
            threads[i] = new Thread(volatileAtomicityTask2);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}

class VolatileAtomicityTask implements Runnable {
    volatile private int count;

    private void add() {
        for (int i=0; i<100; i++) {
            count++;
        }
        System.out.println("count=" + count);
    }

    @Override
    public void run() {
        add();
    }
}

class VolatileAtomicityTask2 implements Runnable {
    volatile private int count;

    private void add() {
        synchronized (VolatileAtomicityTask2.class) {
            for (int i = 0; i < 100; i++) {
                count++;
            }
            System.out.println("count=" + count);
        }
    }

    @Override
    public void run() {
        add();
    }
}

//======================================================================================================================

class AtomicIntegerRunner {
    public void test() {
        AtomicIntegerTask atomicIntegerTask = new AtomicIntegerTask();

        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(atomicIntegerTask);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}

class AtomicIntegerTask implements Runnable {
    private AtomicInteger count = new AtomicInteger(0);

    private void add() {
        for (int i = 0; i < 100; i++) {
            System.out.println("count=" + count.incrementAndGet());
        }
    }

    @Override
    public void run() {
        add();
    }
}

//======================================================================================================================
//======================================================================================================================
//======================================================================================================================
//======================================================================================================================
//======================================================================================================================
//======================================================================================================================
//======================================================================================================================
//======================================================================================================================
//======================================================================================================================
//======================================================================================================================
//======================================================================================================================
