package com.jengine.feature.concurrent.dataShare.synMethod;

/**
 * @author bl07637
 * @date 3/30/2016
 * @description
 *
 * synchronized method, lock this Object
 */
public class SynAllMethodRunner {
    public void allMethodSynchronized() {
        SynAllMethodCounter synAllMethodCounter = new SynAllMethodCounter();
        SynAllMethodThreadA ta = new SynAllMethodThreadA(synAllMethodCounter);
        SynAllMethodThreadB tb = new SynAllMethodThreadB(synAllMethodCounter);
        ta.start();
        tb.start();
    }
}

class SynAllMethodCounter {
    private int num;

    synchronized public void add(String name) {
        if ("a".equals(name)) {
            num = 100;
            System.out.println("a add over");
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if ("b".equals(name)) {
            num = 200;
            System.out.println("b add over");
        }
        System.out.println(name + " set " + num);
    }

    synchronized public void add2(String name) {
        if ("a".equals(name)) {
            num = 100;
            System.out.println("a add2 over");
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if ("b".equals(name)) {
            num = 200;
            System.out.println("b add2 over");
        }
        System.out.println(name + " set " + num);
    }
}

class SynAllMethodThreadA extends Thread {
    private SynAllMethodCounter synAllMethodCounter;

    public SynAllMethodThreadA(SynAllMethodCounter synAllMethodCounter) {
        this.synAllMethodCounter = synAllMethodCounter;
    }

    @Override
    public void run() {
        super.run();
        synAllMethodCounter.add("a");
    }
}

class SynAllMethodThreadB extends Thread {
    private SynAllMethodCounter synAllMethodCounter;

    public SynAllMethodThreadB(SynAllMethodCounter synAllCounter) {
        this.synAllMethodCounter = synAllCounter;
    }

    @Override
    public void run() {
        super.run();
        synAllMethodCounter.add2("b");
    }
}