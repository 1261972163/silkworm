package com.jengine.j2se.concurrent.threadCommunication.join;

/**
 * @author nouuid
 * @date 4/5/2016
 * @description
 */
public class TimedJoinRunner {

    public void timedJoin() {
        TimedJoinService timedJoinService = new TimedJoinService();
        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                timedJoinService.serviceA();
                try {
                    Thread.sleep(60*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("--->");
            }
        });
        childThread.start();
        try {
            childThread.join(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test...");
    }
}

class TimedJoinService {

    public void serviceA() {
        System.out.println("do serviceA");
    }
}

