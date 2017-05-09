package com.jengine.j2se.concurrent.thread;

import java.util.Random;

/**
 * higher priority thread has more probability to run
 *
 * @author bl07637
 * @date 5/9/2017
 * @since 0.1.0
 */
public class PriorityDemo {

    public void test() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int count = 0;
                System.out.println(Thread.currentThread().getName() + " start");
                for (int i=0; i<10000000; i++) {
                    Random random = new Random();
                    random.nextInt();
                    count = count + 1;
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName());
                }
                System.out.println("                           " + Thread.currentThread().getName() + " end");
            }
        };
        for (int i=0; i<5; i++) {
            Thread threadA = new Thread(runnable);
            threadA.setName("TA" + i);
            threadA.setPriority(1);
            threadA.start();

            Thread threadB = new Thread(runnable);
            threadB.setName("TB" + i);
            threadB.setPriority(10);
            threadB.start();
        }
    }
}
