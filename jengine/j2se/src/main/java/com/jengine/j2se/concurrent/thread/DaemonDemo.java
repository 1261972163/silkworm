package com.jengine.j2se.concurrent.thread;

/**
 * content
 *
 * @author bl07637
 * @date 5/9/2017
 * @since 0.1.0
 */
public class DaemonDemo {

    public void test() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int count = 0;
                    while (true) {
                        count++;
                        System.out.println("count=" + count);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t1 = new Thread(runnable);
        t1.setName("T1");
//        t1.setDaemon(true);
        t1.start();

        Thread.sleep(5000);
        System.out.println("finish");
    }
}
