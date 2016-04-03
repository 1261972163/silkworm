package com.jengine.feature.concurrent.dataShare.synMethod;

/**
 * @author bl07637
 * @date 3/30/2016
 * @description
 *
 * synchronized is reenterable
 */
public class SynLockInRunner {

    public void lockIn() {
        SynLockInThread synLockInThread = new SynLockInThread();
        synLockInThread.start();
    }
}

class SynLockInThread extends Thread {
    @Override
    public void run() {
        super.run();
        SynLockInService synLockInService = new SynLockInService();
        synLockInService.service1();
    }
}

class SynLockInService {
    synchronized public void service1() {
        service2();
        System.out.println("service1");
    }

    synchronized public void service2() {
        service3();
        System.out.println("service2");
    }

    synchronized public void service3() {
        System.out.println("service3");
    }
}
