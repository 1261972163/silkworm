package com.jengine.feature.concurrent.threadCommunication.join;

/**
 * @author bl07637
 * @date 4/5/2016
 * @description
 *
 * join: wait thread object to be destoryed
 */
public class JoinRunner {

    public void test() {
        JoinService joinService = new JoinService();
        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                joinService.serviceA();
            }
        });
        childThread.start();
        System.out.println("test...");
    }

    public void test2() {
        JoinService joinService = new JoinService();
        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                joinService.serviceA();
                try {
                    Thread.sleep(2*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("--------");
            }
        });
        childThread.start();
        try {
            childThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test...");
    }
}

class JoinService {

    public void serviceA() {
        System.out.println("do serviceA");
    }
}
