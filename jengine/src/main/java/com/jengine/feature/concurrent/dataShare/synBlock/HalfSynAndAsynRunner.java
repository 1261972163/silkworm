package com.jengine.feature.concurrent.dataShare.synBlock;

/**
 * @author nouuid
 * @date 3/30/2016
 * @description
 */
public class HalfSynAndAsynRunner {

    public void test() {
        HalfSynAndAsynTask halfSynAndAsynTask = new HalfSynAndAsynTask();
        Thread t1 = new Thread(halfSynAndAsynTask);
        Thread t2 = new Thread(halfSynAndAsynTask);
        t1.start();
        t2.start();
    }
}

class HalfSynAndAsynTask implements Runnable {

    public void print() {
        for (int i=0; i<100; i++) {
            System.out.println("nosynchronized thread name = " + Thread.currentThread().getName() + "-" + i);
        }

        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                System.out.println("synchronized thread name = " + Thread.currentThread().getName() + "-" + i);
            }
        }
    }

    @Override
    public void run() {
        print();
    }
}
