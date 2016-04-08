package com.jengine.feature.concurrent.threadCommunication.threadLocal;

/**
 * @author bl07637
 * @date 4/5/2016
 * @description
 *
 * every thread has its own value
 */
public class ThreadLocalRunner {

    public void test() {
        ThreadLocalRunnerThread t1 = new ThreadLocalRunnerThread();
        t1.setName("A");

        ThreadLocalRunnerThread t2 = new ThreadLocalRunnerThread();
        t2.setName("B");

        t1.start();
        t2.start();
    }

    public void initThreadLocalTest() {
        ThreadLocalRunnerThread2 t1 = new ThreadLocalRunnerThread2();
        t1.setName("A");

        ThreadLocalRunnerThread2 t2 = new ThreadLocalRunnerThread2();
        t2.setName("B");

        t1.start();
        t2.start();
    }

}

class ThreadLocalRunnerThread extends Thread {

    @Override
    public void run() {
        try {
            for (int i=0; i<100; i++) {
                ThreadLocalUtil.threadLocal.set(Thread.currentThread().getName() + (i+1));
                Thread.sleep(1 * 1000);
                System.out.println(Thread.currentThread().getName() + " -> " + ThreadLocalUtil.threadLocal.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ThreadLocalRunnerThread2 extends Thread {

    @Override
    public void run() {
        try {
            for (int i=0; i<100; i++) {
                Thread.sleep(1 * 1000);
                System.out.println(Thread.currentThread().getName() + " -> " + ThreadLocalUtil.threadLocalExt.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

