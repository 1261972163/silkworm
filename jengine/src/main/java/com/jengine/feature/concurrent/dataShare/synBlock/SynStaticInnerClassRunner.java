package com.jengine.feature.concurrent.dataShare.synBlock;

/**
 * @author bl07637
 * @date 3/31/2016
 * @description
 */

import com.jengine.feature.concurrent.dataShare.synBlock.SynStaticInnerClassOuterClass.SynStaticInnerClassInnerClassA;
import com.jengine.feature.concurrent.dataShare.synBlock.SynStaticInnerClassOuterClass.SynStaticInnerClassInnerClassB;

public class SynStaticInnerClassRunner {

    public void test() {
        final SynStaticInnerClassInnerClassA synStaticInnerClassInnerClassA = new SynStaticInnerClassInnerClassA();
        final SynStaticInnerClassInnerClassB synStaticInnerClassInnerClassB = new SynStaticInnerClassInnerClassB();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synStaticInnerClassInnerClassA.service1(synStaticInnerClassInnerClassB);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synStaticInnerClassInnerClassA.service2();
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synStaticInnerClassInnerClassB.service1();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}

class SynStaticInnerClassOuterClass {
    static class SynStaticInnerClassInnerClassA {
        public void service1(SynStaticInnerClassInnerClassB synStaticInnerClassInnerClass2) {
            synchronized (synStaticInnerClassInnerClass2) {
                System.out.println("A service1 begin");
                try {
                    Thread.sleep(2*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A service1 end");
            }
        }

        synchronized public void service2() {
            System.out.println("A service2 begin");
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A service2 end");
        }
    }

    static class SynStaticInnerClassInnerClassB {
        synchronized public void service1() {
            System.out.println("B service1 begin");
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B service1 end");
        }
    }
}


