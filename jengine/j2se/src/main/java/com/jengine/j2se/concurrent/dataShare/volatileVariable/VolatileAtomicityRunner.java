package com.jengine.j2se.concurrent.dataShare.volatileVariable;

/**
 * @author nouuid
 * @date 4/1/2016
 * @description
 */
public class VolatileAtomicityRunner {

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
