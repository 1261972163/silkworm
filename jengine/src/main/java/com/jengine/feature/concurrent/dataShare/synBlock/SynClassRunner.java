package com.jengine.feature.concurrent.dataShare.synBlock;

/**
 * @author nouuid
 * @date 3/30/2016
 * @description
 *
 * lock Class
 */
public class SynClassRunner {
    public void test() {
        SynClassTask synClassTask = new SynClassTask();
        SynClassThreadA synClassThreadA = new SynClassThreadA(synClassTask);
        SynClassThreadB synClassThreadB = new SynClassThreadB(synClassTask);
        SynClassThreadC synClassThreadC = new SynClassThreadC(synClassTask);
        synClassThreadA.start();
        synClassThreadB.start();
        synClassThreadC.start();
    }
}

class SynClassTask {
    public static void serviceA() {
        synchronized (SynClassTask.class) {
            System.out.println("service A begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service A end time=" + System.currentTimeMillis());
        }
    }

    public static void serviceB() {
        synchronized (SynClassTask.class) {
            System.out.println("service B begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service B end time=" + System.currentTimeMillis());
        }
    }

    public void serviceC() {
        synchronized (SynClassTask.class) {
            System.out.println("service C begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service C end time=" + System.currentTimeMillis());
        }
    }
}

class SynClassThreadA extends Thread {
    SynClassTask synClassTask;

    public SynClassThreadA(SynClassTask synClassTask) {
        this.synClassTask = synClassTask;
    }

    @Override
    public void run() {
        super.run();
        synClassTask.serviceA();
    }
}

class SynClassThreadB extends Thread {
    SynClassTask synClassTask;

    public SynClassThreadB(SynClassTask synClassTask) {
        this.synClassTask = synClassTask;
    }

    @Override
    public void run() {
        super.run();
        synClassTask.serviceB();
    }
}

class SynClassThreadC extends Thread {
    SynClassTask synClassTask;

    public SynClassThreadC(SynClassTask synClassTask) {
        this.synClassTask = synClassTask;
    }

    @Override
    public void run() {
        super.run();
        synClassTask.serviceC();
    }
}
