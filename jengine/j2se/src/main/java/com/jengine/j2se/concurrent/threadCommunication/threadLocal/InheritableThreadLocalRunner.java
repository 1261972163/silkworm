package com.jengine.j2se.concurrent.threadCommunication.threadLocal;

/**
 * @author nouuid
 * @date 4/6/2016
 * @description
 *
 * InheritableThreadLocal can get value from parent thread
 *
 */
public class InheritableThreadLocalRunner {

    public void inheritableThreadLocalTest() {
        ThreadLocalUtil.inheritableThreadLocal.set(Thread.currentThread().getName());

        InheritableThreadLocalRunnerThread t1 = new InheritableThreadLocalRunnerThread();
        t1.setName("A");

        InheritableThreadLocalRunnerThread t2 = new InheritableThreadLocalRunnerThread();
        t2.setName("B");

        t1.start();
        t2.start();
    }

    public void initInheritableThreadLocalTest() {
        InheritableThreadLocalRunnerThread2 t1 = new InheritableThreadLocalRunnerThread2();
        t1.setName("A");

        InheritableThreadLocalRunnerThread2 t2 = new InheritableThreadLocalRunnerThread2();
        t2.setName("B");

        t1.start();
        t2.start();
    }

    public void inheritableThreadLocalChangeTest() {
        ThreadLocalUtil.inheritableThreadLocalExt.set(Thread.currentThread().getName());
        InheritableThreadLocalRunnerThread3 t1 = new InheritableThreadLocalRunnerThread3();
        t1.setName("A");

        InheritableThreadLocalRunnerThread3 t2 = new InheritableThreadLocalRunnerThread3();
        t2.setName("B");

        System.out.println(Thread.currentThread().getName() + " -> " + ThreadLocalUtil.inheritableThreadLocalExt.get());

        t1.start();
        t2.start();
    }
}



class InheritableThreadLocalRunnerThread extends Thread {

    @Override
    public void run() {
        try {
            for (int i=0; i<100; i++) {
                Thread.sleep(1 * 1000);
                System.out.println(Thread.currentThread().getName() + " -> " + ThreadLocalUtil.inheritableThreadLocal.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class InheritableThreadLocalRunnerThread2 extends Thread {

    @Override
    public void run() {
        try {
            for (int i=0; i<100; i++) {
                Thread.sleep(1 * 1000);
                System.out.println(Thread.currentThread().getName() + " -> " + ThreadLocalUtil.inheritableThreadLocalExt.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class InheritableThreadLocalRunnerThread3 extends Thread {

    @Override
    public void run() {
        try {
            for (int i=0; i<100; i++) {
//                ThreadLocalUtil.inheritableThreadLocalExt.set(Thread.currentThread().getName() + (i+1));
                Thread.sleep(1 * 1000);
                System.out.println(Thread.currentThread().getName() + " -> " + ThreadLocalUtil.inheritableThreadLocalExt.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
