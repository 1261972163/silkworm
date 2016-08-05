package com.jengine.feature.concurrent.threadCommunication.join;

/**
 * @author nouuid
 * @date 4/5/2016
 * @description
 */
public class CompareTimedJoinTimedSleepRunner {

    public void sleepTest() {
        CompareTimedJoinTimedSleepThreadA compareTimedJoinTimedSleepThreadA = new CompareTimedJoinTimedSleepThreadA();
        CompareTimedJoinTimedSleepThreadB compareTimedJoinTimedSleepThreadB = new CompareTimedJoinTimedSleepThreadB(compareTimedJoinTimedSleepThreadA);
        CompareTimedJoinTimedSleepThreadC compareTimedJoinTimedSleepThreadC = new CompareTimedJoinTimedSleepThreadC(compareTimedJoinTimedSleepThreadA);

        compareTimedJoinTimedSleepThreadB.start();
        compareTimedJoinTimedSleepThreadC.start();
    }

    public void joinTest() {
        CompareTimedJoinTimedSleepThreadA compareTimedJoinTimedSleepThreadA = new CompareTimedJoinTimedSleepThreadA();
        CompareTimedJoinTimedSleepThreadB2 compareTimedJoinTimedSleepThreadB2 = new CompareTimedJoinTimedSleepThreadB2(compareTimedJoinTimedSleepThreadA);
        CompareTimedJoinTimedSleepThreadC compareTimedJoinTimedSleepThreadC = new CompareTimedJoinTimedSleepThreadC(compareTimedJoinTimedSleepThreadA);

        compareTimedJoinTimedSleepThreadB2.start();
        compareTimedJoinTimedSleepThreadC.start();
    }
}

class CompareTimedJoinTimedSleepThreadA extends Thread {

    @Override
    public void run() {
        try {
            System.out.println("threadA run start");
            Thread.sleep(6*1000);
            System.out.println("threadA run over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void service() {
        System.out.println("threadA do service.");
    }
}

class CompareTimedJoinTimedSleepThreadB extends Thread {

    private CompareTimedJoinTimedSleepThreadA compareTimedJoinTimedSleepThreadA;

    public CompareTimedJoinTimedSleepThreadB(CompareTimedJoinTimedSleepThreadA compareTimedJoinTimedSleepThreadA) {
        this.compareTimedJoinTimedSleepThreadA = compareTimedJoinTimedSleepThreadA;
    }

    @Override
    public void run() {
        try {
            synchronized (compareTimedJoinTimedSleepThreadA) {
                System.out.println("in ThreadB...");
                compareTimedJoinTimedSleepThreadA.start();
                Thread.sleep(10*1000);
                System.out.println("out ThreadB...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class CompareTimedJoinTimedSleepThreadB2 extends Thread {

    private CompareTimedJoinTimedSleepThreadA compareTimedJoinTimedSleepThreadA;

    public CompareTimedJoinTimedSleepThreadB2(CompareTimedJoinTimedSleepThreadA compareTimedJoinTimedSleepThreadA) {
        this.compareTimedJoinTimedSleepThreadA = compareTimedJoinTimedSleepThreadA;
    }

    @Override
    public void run() {
        try {
            synchronized (compareTimedJoinTimedSleepThreadA) {
                System.out.println("in ThreadB...");
                compareTimedJoinTimedSleepThreadA.start();
                compareTimedJoinTimedSleepThreadA.join(10*1000);
                System.out.println("out ThreadB...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class CompareTimedJoinTimedSleepThreadC extends Thread {
    private CompareTimedJoinTimedSleepThreadA compareTimedJoinTimedSleepThreadA;

    public CompareTimedJoinTimedSleepThreadC(CompareTimedJoinTimedSleepThreadA compareTimedJoinTimedSleepThreadA) {
        this.compareTimedJoinTimedSleepThreadA = compareTimedJoinTimedSleepThreadA;
    }

    @Override
    public void run() {
        System.out.println("in ThreadC...");
        compareTimedJoinTimedSleepThreadA.service();
        System.out.println("out ThreadC...");
    }
}