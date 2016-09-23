package com.jengine.j2se.concurrent.dataShare.volatileVariable;

/**
 * @author nouuid
 * @date 4/1/2016
 * @description
 */
public class DeadLoopRunnable2Runner {
    public void test() {
        DeadLoopRunnable2Task deadLoopRunnable2Task = new DeadLoopRunnable2Task();
        Thread t1 = new Thread(deadLoopRunnable2Task);
        System.out.println("begin");
        t1.start();
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("want to stop");
        deadLoopRunnable2Task.setFlag(false);
        System.out.println("end");
    }
}

class DeadLoopRunnable2Task implements Runnable {
    volatile private boolean flag = true;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void process() {
        int i = 0;
        while (flag) {
            System.out.println("i=" + i++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        process();
    }
}
