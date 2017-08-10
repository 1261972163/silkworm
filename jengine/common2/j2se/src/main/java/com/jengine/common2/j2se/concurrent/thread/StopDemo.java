package com.jengine.common2.j2se.concurrent.thread;

import com.jengine.common2.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * @author nouuid
 * @date 4/11/2016
 * @description
 */
public class StopDemo extends ConcurrentTest {

    @Test
    public void loopTest() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " do");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        thread.interrupted();

        Thread.sleep(1*1000);
        System.out.println("isInterrupted=" + thread.isInterrupted());
        Thread.sleep(3*1000);
        thread.interrupt();
        Thread.sleep(1*1000);
        System.out.println("isInterrupted=" + thread.isInterrupted());
        Thread.sleep(10*1000);
    }

    /**
     * throw exception will stop thread
     * but the flag will not be changed
     * @throws InterruptedException
     */
    @Test
    public void interruptTest() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {

                    for (int i=0; i<Integer.MAX_VALUE; i++) {
                        if (Thread.currentThread().interrupted()) {
                            throw new InterruptedException();
                        }
                        System.out.println(Thread.currentThread().getName() + " do " + i);
                    }
                    System.out.println("out of while");
                } catch (Exception e) {
                    System.out.println("runnable catch");
                    e.printStackTrace();
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        try {
            Thread.sleep(3 * 1000);
            System.out.println("before, isInterrupted=" + thread.isInterrupted());
            thread.interrupt();
            Thread.sleep(1*1000);
            System.out.println("after, isInterrupted=" + thread.isInterrupted());
        } catch (Exception e) {
            System.out.println("test catch");
            e.printStackTrace();
        }
        Thread.sleep(10*1000);
    }

    /**
     * interrupt method can make thread in blocking throw an exception
     * thread will to stoped
     * and change the interrupted flag to false
     * @throws InterruptedException
     */
    @Test
    public void sleepInterruptTest() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("sleep before");
                    Thread.sleep(30*1000);
                    System.out.println("sleep after");
                } catch (Exception e) {
                    System.out.println("catch, isInterrupted=" + Thread.currentThread().isInterrupted());
                    e.printStackTrace();
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(2*1000);
        System.out.println("before, isInterrupted=" + thread.isInterrupted());
        thread.interrupt();
        Thread.sleep(60*1000);
    }

}