package com.jengine.feature.concurrent.dataShare.synBlock;

/**
 * @author bl07637
 * @date 3/30/2016
 * @description
 *
 * synchronized block, lock specified Object
 * different Object specified, has different synchronism
 */
public class SynBlocksSynchronismRunner {
    public void test() {
        SynBlocksSynchronismTask synBlocksSynchronismTask = new SynBlocksSynchronismTask();
        SynBlocksSynchronismThreadA synBlocksSynchronismThreadA = new SynBlocksSynchronismThreadA(synBlocksSynchronismTask);
        SynBlocksSynchronismThreadB synBlocksSynchronismThreadB = new SynBlocksSynchronismThreadB(synBlocksSynchronismTask);
        synBlocksSynchronismThreadA.start();
        synBlocksSynchronismThreadB.start();
    }
}

class SynBlocksSynchronismThreadA extends Thread {
    SynBlocksSynchronismTask synBlocksSynchronismTask;

    public SynBlocksSynchronismThreadA(SynBlocksSynchronismTask synBlocksSynchronismTask) {
        this.synBlocksSynchronismTask = synBlocksSynchronismTask;
    }

    @Override
    public void run() {
        super.run();
        synBlocksSynchronismTask.serviceA();
    }
}

class SynBlocksSynchronismThreadB extends Thread {
    SynBlocksSynchronismTask synBlocksSynchronismTask;

    public SynBlocksSynchronismThreadB(SynBlocksSynchronismTask synBlocksSynchronismTask) {
        this.synBlocksSynchronismTask = synBlocksSynchronismTask;
    }


    @Override
    public void run() {
        super.run();
        synBlocksSynchronismTask.serviceB();
    }
}

class SynBlocksSynchronismTask {
    public void serviceA() {
        synchronized (this) {
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
        synchronized (this) {
            System.out.println("service B begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service B end time=" + System.currentTimeMillis());
        }
    }
}
