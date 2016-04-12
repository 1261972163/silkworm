package com.jengine.feature.concurrent.singletonPattern;

/**
 * Created by nouuid on 4/9/16.
 */
public class HungryLoadRunner {

    public void test() {
        HungryLoadTask hungryLoadTask = new HungryLoadTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                hungryLoadTask.service();
            }
        };


        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();

    }
}

class HungryLoadObject {
    private static HungryLoadObject hungryLoadObject = new HungryLoadObject();

    private HungryLoadObject() {

    }

    public static HungryLoadObject getInstance() {
        return hungryLoadObject;
    }
}

class HungryLoadTask {
    public void service() {
        System.out.println(Thread.currentThread().getName() + " -> " + HungryLoadObject.getInstance().hashCode());
    }
}
