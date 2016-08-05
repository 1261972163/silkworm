package com.jengine.feature.concurrent.dataShare.volatileVariable;

/**
 * @author nouuid
 * @date 4/1/2016
 * @description
 *
 * occur dead loop when run on JVM64bit with -server configuration
 */
public class DeadLoopRunnableRunner {

    public void test() {
        DeadLoopRunnableTask deadLoopRunnableTask = new DeadLoopRunnableTask();
        Thread t1 = new Thread(deadLoopRunnableTask);
        System.out.println("begin");
        t1.start();
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("want to stop");
        deadLoopRunnableTask.setFlag(false);
        System.out.println("end");
    }
}

class DeadLoopRunnableTask implements Runnable {
    private boolean flag = true;

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
