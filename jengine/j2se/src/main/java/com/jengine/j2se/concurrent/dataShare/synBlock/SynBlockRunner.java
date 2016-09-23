package com.jengine.j2se.concurrent.dataShare.synBlock;

/**
 * @author nouuid
 * @date 3/30/2016
 * @description
 *
 * synchronized block is superior to synchronized method
 */
public class SynBlockRunner {

    public void test() throws InterruptedException {
        SynBlockTask synBlockTask = new SynBlockTask();
        SynBlockThreadA t1 = new SynBlockThreadA(synBlockTask);
        SynBlockThreadB t2 = new SynBlockThreadB(synBlockTask);
        t1.start();
        t2.start();

        Thread.sleep(10*1000);

        long beginTime = TimeUtil.beginTime1;
        long endTime = TimeUtil.endTime1;

        if (TimeUtil.beginTime1>TimeUtil.beginTime2) {
            beginTime = TimeUtil.beginTime2;
        }
        if (TimeUtil.endTime1<TimeUtil.endTime2) {
            endTime = TimeUtil.endTime2;
        }
        System.out.println("cost: " + (endTime-beginTime)/1000);

    }
}

class TimeUtil {
    public static long beginTime1;
    public static long beginTime2;
    public static long endTime1;
    public static long endTime2;
}

class SynBlockThreadA extends Thread {

    private SynBlockTask synBlockTask;

    public SynBlockThreadA(SynBlockTask synBlockTask) {
        super();
        this.synBlockTask = synBlockTask;
    }

    @Override
    public void run() {
        super.run();
        TimeUtil.beginTime1 = System.currentTimeMillis();
        synBlockTask.doLongTimeTask();
        TimeUtil.endTime1 = System.currentTimeMillis();
    }
}

class SynBlockThreadB extends Thread {
    private SynBlockTask synBlockTask;

    public SynBlockThreadB(SynBlockTask synBlockTask) {
        super();
        this.synBlockTask = synBlockTask;
    }

    @Override
    public void run() {
        super.run();
        TimeUtil.beginTime2 = System.currentTimeMillis();
        synBlockTask.doLongTimeTask();
        TimeUtil.endTime2 = System.currentTimeMillis();
    }
}

class SynBlockTask {
    private String data1;
    private String data2;

    synchronized public void doLongTimeTask() {
        try {
            System.out.println("begin task");
            Thread.sleep(3*1000);
            String name1 = "long time task data1 by " + Thread.currentThread().getName();
            String name2 = "long time task data2 by " + Thread.currentThread().getName();

            data1 = name1;
            data2 = name2;

            System.out.println(data1);
            System.out.println(data2);
            System.out.println("end task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doLongTimeTask2() {
        try {
            System.out.println("begin task");
            Thread.sleep(3*1000);
            String name1 = "long time task data1 by " + Thread.currentThread().getName();
            String name2 = "long time task data2 by " + Thread.currentThread().getName();
            synchronized (this) {
                data1 = name1;
                data2 = name2;
            }

            System.out.println(data1);
            System.out.println(data2);
            System.out.println("end task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}