package com.jengine.feature.concurrent.dataShare.synMethod;

/**
 * @author nouuid
 * @date 3/30/2016
 * @description
 */
public class DataShareCounterSafeRunner {
    public void test() {
        DataShareSafeCounter dataShareSafeCounter = new DataShareSafeCounter();
        SafeThreadA ta = new SafeThreadA(dataShareSafeCounter);
        SafeThreadB tb = new SafeThreadB(dataShareSafeCounter);
        ta.start();
        tb.start();
    }
}

class DataShareSafeCounter {
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
}

class SafeThreadA extends Thread {
    private DataShareSafeCounter dataShareSafeCounter;

    public SafeThreadA(DataShareSafeCounter dataShareSafeCounter) {
        this.dataShareSafeCounter = dataShareSafeCounter;
    }

    @Override
    public void run() {
        super.run();
        dataShareSafeCounter.add("a");
    }
}

class SafeThreadB extends Thread {
    private DataShareSafeCounter dataShareSafeCounter;

    public SafeThreadB(DataShareSafeCounter dataShareSafeCounter) {
        this.dataShareSafeCounter = dataShareSafeCounter;
    }

    @Override
    public void run() {
        super.run();
        dataShareSafeCounter.add("b");
    }
}