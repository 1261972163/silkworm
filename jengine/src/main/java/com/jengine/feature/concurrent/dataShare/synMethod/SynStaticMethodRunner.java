package com.jengine.feature.concurrent.dataShare.synMethod;

/**
 * @author bl07637
 * @date 3/31/2016
 * @description
 *
 * synchronized method, lock Object
 * synchronized static method, lock Class
 */
public class SynStaticMethodRunner {
    public void test() {
        SynStaticMethodTask synStaticMethodTask = new SynStaticMethodTask();
        SynStaticMethodThreadA synStaticMethodThreadA = new SynStaticMethodThreadA(synStaticMethodTask);
        SynStaticMethodThreadB synStaticMethodThreadB = new SynStaticMethodThreadB(synStaticMethodTask);
        SynStaticMethodThreadC synStaticMethodThreadC = new SynStaticMethodThreadC(synStaticMethodTask);
        synStaticMethodThreadA.start();
        synStaticMethodThreadB.start();
        synStaticMethodThreadC.start();
    }
}

class SynStaticMethodTask {
    synchronized public static void serviceA() {
        System.out.println("service A begin time=" + System.currentTimeMillis());
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("service A end time=" + System.currentTimeMillis());
    }

    synchronized public static void serviceB() {
        System.out.println("service B begin time=" + System.currentTimeMillis());
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("service B end time=" + System.currentTimeMillis());
    }

    synchronized public void serviceC() {
        System.out.println("service C begin time=" + System.currentTimeMillis());
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("service C end time=" + System.currentTimeMillis());
    }
}

class SynStaticMethodThreadA extends Thread {
    SynStaticMethodTask synStaticMethodTask;

    public SynStaticMethodThreadA(SynStaticMethodTask synStaticMethodTask) {
        this.synStaticMethodTask = synStaticMethodTask;
    }

    @Override
    public void run() {
        super.run();
        synStaticMethodTask.serviceA();
    }
}

class SynStaticMethodThreadB extends Thread {
    SynStaticMethodTask synStaticMethodTask;

    public SynStaticMethodThreadB(SynStaticMethodTask synStaticMethodTask) {
        this.synStaticMethodTask = synStaticMethodTask;
    }

    @Override
    public void run() {
        super.run();
        synStaticMethodTask.serviceB();
    }
}

class SynStaticMethodThreadC extends Thread {
    SynStaticMethodTask synStaticMethodTask;

    public SynStaticMethodThreadC(SynStaticMethodTask synStaticMethodTask) {
        this.synStaticMethodTask = synStaticMethodTask;
    }

    @Override
    public void run() {
        super.run();
        synStaticMethodTask.serviceC();
    }
}
