package com.jengine.feature.concurrent.dataShare.synMethod;

/**
 * @author bl07637
 * @date 3/30/2016
 * @description synchronized lock object
 */
public class SynOneMethodRunner {
    public void oneMethodSynchronized() {
        SynOneMethodCounter synchronizedCounter = new SynOneMethodCounter();
        SynOneMethodThreadA ta = new SynOneMethodThreadA(synchronizedCounter);
        SynOneMethodThreadB tb = new SynOneMethodThreadB(synchronizedCounter);
        ta.start();
        tb.start();
    }
}

class SynOneMethodCounter {
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

    public void add2(String name) {
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



class SynOneMethodThreadA extends Thread {
    private SynOneMethodCounter synOneMethodCounter;

    public SynOneMethodThreadA(SynOneMethodCounter synOneMethodCounter) {
        this.synOneMethodCounter = synOneMethodCounter;
    }

    @Override
    public void run() {
        super.run();
        synOneMethodCounter.add("a");
    }
}

class SynOneMethodThreadB extends Thread {
    private SynOneMethodCounter synOneMethodCounter;

    public SynOneMethodThreadB(SynOneMethodCounter synOneMethodCounter) {
        this.synOneMethodCounter = synOneMethodCounter;
    }

    @Override
    public void run() {
        super.run();
        synOneMethodCounter.add2("b");
    }
}


