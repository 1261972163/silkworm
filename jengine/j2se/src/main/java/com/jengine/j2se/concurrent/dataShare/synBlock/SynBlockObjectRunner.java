package com.jengine.j2se.concurrent.dataShare.synBlock;

/**
 * @author nouuid
 * @date 3/30/2016
 * @description
 *
 * synchronized block, lock specified Object
 * different Object specified, has different synchronism
 */
public class SynBlockObjectRunner {
    public void test() {
        SynBlockObjectTask synBlockObjectTask = new SynBlockObjectTask();
        SynBlockObjectThreadA synBlockObjectThreadA = new SynBlockObjectThreadA(synBlockObjectTask);
        SynBlockObjectThreadB synBlockObjectThreadB = new SynBlockObjectThreadB(synBlockObjectTask);
        SynBlockObjectThreadC synBlockObjectThreadC = new SynBlockObjectThreadC(synBlockObjectTask);
        synBlockObjectThreadA.start();
        synBlockObjectThreadB.start();
        synBlockObjectThreadC.start();
    }
}

class SynBlockObjectTask {
    Object anyObject = new Object();

    public void serviceA() {
        synchronized (anyObject) {
            System.out.println("service A begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service A end time=" + System.currentTimeMillis());
        }
    }

    public void serviceB() {
        synchronized (anyObject) {
            System.out.println("service B begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service B end time=" + System.currentTimeMillis());
        }
    }

    public void serviceC() {
        synchronized (this) {
            System.out.println("service C begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service C end time=" + System.currentTimeMillis());
        }
    }
}

class SynBlockObjectThreadA extends Thread {
    SynBlockObjectTask synBlockObjectTask;

    public SynBlockObjectThreadA(SynBlockObjectTask synBlockObjectTask) {
        this.synBlockObjectTask = synBlockObjectTask;
    }

    @Override
    public void run() {
        super.run();
        synBlockObjectTask.serviceA();
    }
}

class SynBlockObjectThreadB extends Thread {
    SynBlockObjectTask synBlockObjectTask;

    public SynBlockObjectThreadB(SynBlockObjectTask synBlockObjectTask) {
        this.synBlockObjectTask = synBlockObjectTask;
    }

    @Override
    public void run() {
        super.run();
        synBlockObjectTask.serviceB();
    }
}

class SynBlockObjectThreadC extends Thread {
    SynBlockObjectTask synBlockObjectTask;

    public SynBlockObjectThreadC(SynBlockObjectTask synBlockObjectTask) {
        this.synBlockObjectTask = synBlockObjectTask;
    }

    @Override
    public void run() {
        super.run();
        synBlockObjectTask.serviceC();
    }
}