package com.jengine.j2se.concurrent.dataShare.volatileVariable;

/**
 * @author nouuid
 * @date 4/1/2016
 * @description
 */
public class DeadLoopRunner {

    public void test() {
        DeadLoopTask deadLoopTask = new DeadLoopTask();
        System.out.println("begin");
        deadLoopTask.process();
        System.out.println("want to stop");
        deadLoopTask.setFlag(false);
        System.out.println("end");
    }

}

class DeadLoopTask {
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
}
