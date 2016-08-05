package com.jengine.feature.concurrent.dataShare.synBlock;

/**
 * @author nouuid
 * @date 3/31/2016
 * @description
 */
public class DeadLockRunner {

    public void test() {
        DeadLockTask deadLockTask = new DeadLockTask();
        DeadLockThreadA deadLockThreadA = new DeadLockThreadA(deadLockTask);
        DeadLockThreadB deadLockThreadB = new DeadLockThreadB(deadLockTask);
        deadLockThreadA.start();
        deadLockThreadB.start();
    }
}

class DeadLockThreadA extends Thread {
    DeadLockTask deadLockTask;

    public DeadLockThreadA(DeadLockTask deadLockTask) {
        this.deadLockTask = deadLockTask;
    }

    @Override
    public void run() {
        super.run();
        deadLockTask.serviceA();
    }
}

class DeadLockThreadB extends Thread {
    DeadLockTask deadLockTask;

    public DeadLockThreadB(DeadLockTask deadLockTask) {
        this.deadLockTask = deadLockTask;
    }

    @Override
    public void run() {
        super.run();
        deadLockTask.serviceB();
    }
}

class DeadLockTask {

    Object o1 = new Object();
    Object o2 = new Object();

    public void serviceA() {
        synchronized (o1) {
            System.out.println("service A begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2) {
                System.out.println("service A end time=" + System.currentTimeMillis());
            }
        }
    }

    public void serviceB() {
        synchronized (o2) {
            System.out.println("service B begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o1) {
                System.out.println("service B end time=" + System.currentTimeMillis());
            }
        }
    }
}
