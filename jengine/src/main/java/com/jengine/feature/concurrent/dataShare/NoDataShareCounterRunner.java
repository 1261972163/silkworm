package com.jengine.feature.concurrent.dataShare;

/**
 * @author nouuid
 * @date 3/30/2016
 * @description
 */
public class NoDataShareCounterRunner {
    public void test() {
        Thread t1 = new Thread(new NoDataShareCounter("a"));
        Thread t2 = new Thread(new NoDataShareCounter("b"));
        t1.start();
        t2.start();
    }
}

class NoDataShareCounter implements Runnable {
    private String name;

    public NoDataShareCounter(String name) {
        this.name = name;
    }

    public void add() {
        int num = 0;
        if ("a".equals(name)) {
            num = 100;
            System.out.println("a add over");
        } else if ("b".equals(name)) {
            num = 200;
            System.out.println("b add over");
        }
        System.out.println(num);
    }

    @Override
    public void run() {
        add();
    }
}