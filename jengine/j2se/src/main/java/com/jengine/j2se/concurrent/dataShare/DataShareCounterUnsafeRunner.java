package com.jengine.j2se.concurrent.dataShare;

/**
 * @author nouuid
 * @date 3/30/2016
 * @description
 */
public class DataShareCounterUnsafeRunner {
    public void test() {
        DataShareUnsafeCounter dataShareUnsafeCounter = new DataShareUnsafeCounter();
        ThreadA ta = new ThreadA(dataShareUnsafeCounter);
        ThreadB tb = new ThreadB(dataShareUnsafeCounter);
        ta.start();
        tb.start();
    }
}

class DataShareUnsafeCounter {
    private int num;

    public void add(String name) {
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

class ThreadA extends Thread {
    private DataShareUnsafeCounter dataShareUnsafeCounter;

    public ThreadA(DataShareUnsafeCounter dataShareUnsafeCounter) {
        this.dataShareUnsafeCounter = dataShareUnsafeCounter;
    }

    @Override
    public void run() {
        super.run();
        dataShareUnsafeCounter.add("a");
    }
}

class ThreadB extends Thread {
    private DataShareUnsafeCounter dataShareUnsafeCounter;

    public ThreadB(DataShareUnsafeCounter dataShareUnsafeCounter) {
        this.dataShareUnsafeCounter = dataShareUnsafeCounter;
    }

    @Override
    public void run() {
        super.run();
        dataShareUnsafeCounter.add("b");
    }
}
