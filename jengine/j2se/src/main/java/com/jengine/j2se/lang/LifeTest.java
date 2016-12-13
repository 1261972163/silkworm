package com.jengine.j2se.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author nouuid
 * @date 3/1/2016
 * @description
 */
public class LifeTest {

    @Test
    public void startThreadTest() {
        StartThread tt = new StartThread("TestThread");
        tt.start();

        try {
            tt.start();
        } catch (Exception e) {
            String excepted = "java.lang.IllegalThreadStateException";
            if (excepted.equals(e.getClass().getName())) {
                Assert.assertFalse(false);
            }
        }
    }

    @Test
    public void startTaskTest() {
        StartTask mt = new StartTask();
        Thread t = new Thread(mt, "thread1");
        t.start();
    }

    /**
     * interrupt()
     * isInterrupted()
     * interrupted()
     */
    @Test
    public void interruptCheckTest() {
        try {
            InterruptLoopThread it = new InterruptLoopThread();
            it.start();

            Thread.sleep(1 *1000);
            it.interrupt(); // interrupt(): change state of provided thread

            junit.framework.Assert.assertTrue(it.isInterrupted()); // isInterrupted():  get state of provided thread, will not clean state

            junit.framework.Assert.assertTrue(it.isInterrupted());

            junit.framework.Assert.assertTrue(it.isInterrupted());

            junit.framework.Assert.assertFalse(Thread.currentThread().isInterrupted());

            Thread.currentThread().interrupt();

            junit.framework.Assert.assertTrue(it.interrupted()); // interrupted(): get state of current thread, will clean thread state

            junit.framework.Assert.assertFalse(it.interrupted());

            junit.framework.Assert.assertFalse(Thread.currentThread().isInterrupted());

            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end!");
    }

    @Test
    public void interruptFlagTest() {
        try {
            InterruptFlagThread it = new InterruptFlagThread();
            it.start();
            Thread.sleep(2*1000);
            it.interrupt();

            Thread.sleep(30*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end!");
    }

    /**
     * suspend()
     * resume()
     */
    @Test
    public void suspendResumeTest() {
        try {
            SuspendResumeThread thread = new SuspendResumeThread();
            thread.start();
            Thread.sleep(5*1000);
            // A?
            thread.suspend();
            System.out.println("A= " + System.currentTimeMillis() + " i=" + thread.getI());
            Thread.sleep(5*1000);
            System.out.println("A= " + System.currentTimeMillis() + " i=" + thread.getI());
            // B?
            thread.resume();
            Thread.sleep(5*1000);
            System.out.println("B= " + System.currentTimeMillis() + " i=" + thread.getI());
            Thread.sleep(5*1000);
            System.out.println("B= " + System.currentTimeMillis() + " i=" + thread.getI());
            // C?
            thread.suspend();
            System.out.println("C= " + System.currentTimeMillis() + " i=" + thread.getI());
            Thread.sleep(5*1000);
            System.out.println("C= " + System.currentTimeMillis() + " i=" + thread.getI());

            Thread.sleep(30*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class StartTask implements Runnable {

    @Override
    public void run() {
        System.out.println("MyTask is running.");
    }
}

class StartThread extends Thread {

    private String name;

    public StartThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Thread[" + name + "] is running.");
        super.run();
        System.out.println("Thread[" + name + "] is finished.");
    }
}

class InterruptLoopThread extends Thread{
    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 100000; i++) {
            System.out.println("i=" + (i + 1));
        }
    }
}

class InterruptFlagThread extends Thread{
    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 500000; i++) {
            if (this.interrupted()) {
                System.out.println("stop state. be about to quit!");
                break;
            }
            System.out.println("i=" + (i + 1));
        }
    }
}

class SuspendResumeThread extends Thread {
    private long i = 0;
    public long getI() {
        return i;
    }
    public void setI(long i) {
        this.i = i;
    }
    @Override
    public void run() {
        while (true) {
            i++;
        }
    }
}