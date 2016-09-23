package com.jengine.j2se.concurrent.dataShare.synBlock;

/**
 * @author nouuid
 * @date 4/1/2016
 * @description
 */
public class SynBlockObjectChangedRunner {

    public void test() {
        SynBlockObjectChangedTask synBlockObjectChangedTask = new SynBlockObjectChangedTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockObjectChangedTask.service();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockObjectChangedTask.service();
            }
        });

        t1.setName("A");
        t2.setName("B");

        t1.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();

    }
}

class SynBlockObjectChangedTask {

    private String lockStr = "123";

    public void service() {
        System.out.println("---------------" + Thread.currentThread().getName() + " lockStr=" + lockStr);
        synchronized (lockStr) {
            System.out.println(Thread.currentThread().getName() + " service begin"+ ", lockStr=" + lockStr);
//            try {
//                Thread.sleep(2*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            lockStr = "456";
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " service end");
        }
    }
}

