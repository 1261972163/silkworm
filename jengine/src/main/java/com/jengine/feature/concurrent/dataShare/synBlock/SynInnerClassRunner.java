package com.jengine.feature.concurrent.dataShare.synBlock;

import com.jengine.feature.concurrent.dataShare.synBlock.SynInnerClassOuterClass.SynInnerClassInnerClassA;
import com.jengine.feature.concurrent.dataShare.synBlock.SynInnerClassOuterClass.SynInnerClassInnerClassB;

/**
 * @author nouuid
 * @date 3/31/2016
 * @description
 */
public class SynInnerClassRunner {
    public void test() {
        SynInnerClassOuterClass synInnerClassOuterClass = new SynInnerClassOuterClass();
        SynInnerClassInnerClassA synInnerClassInnerClassA = synInnerClassOuterClass.new SynInnerClassInnerClassA();
        SynInnerClassInnerClassB synInnerClassInnerClassB = synInnerClassOuterClass.new SynInnerClassInnerClassB();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synInnerClassInnerClassA.service1(synInnerClassInnerClassB);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synInnerClassInnerClassA.service2();
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synInnerClassInnerClassB.service1();
            }
        });

//        t1.start();
        t2.start();
        t3.start();
    }
}

class SynInnerClassOuterClass {
    class SynInnerClassInnerClassA {
        public void service1(SynInnerClassInnerClassB synInnerClassInnerClassB) {
            synchronized (synInnerClassInnerClassB) {
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

    class SynInnerClassInnerClassB {
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