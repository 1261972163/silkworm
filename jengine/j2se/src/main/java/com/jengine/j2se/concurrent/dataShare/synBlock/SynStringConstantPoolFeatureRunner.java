package com.jengine.j2se.concurrent.dataShare.synBlock;

/**
 * @author nouuid
 * @date 3/31/2016
 * @description
 *
 * String has constant pool feature
 * we prefer not to use String as part of synchronized(...)
 */
public class SynStringConstantPoolFeatureRunner {
    public void test() {
        SynStringConstantPoolFeatureTask synStringConstantPoolFeatureTask = new SynStringConstantPoolFeatureTask();
        synStringConstantPoolFeatureTask.setShareStr("TTT");
        SynStringConstantPoolFeatureThreadA synStringConstantPoolFeatureThreadA = new SynStringConstantPoolFeatureThreadA(synStringConstantPoolFeatureTask);
        synStringConstantPoolFeatureTask.setShareStr("TTT");
        SynStringConstantPoolFeatureThreadB synStringConstantPoolFeatureThreadB = new SynStringConstantPoolFeatureThreadB(synStringConstantPoolFeatureTask);
        synStringConstantPoolFeatureThreadA.start();
        synStringConstantPoolFeatureThreadB.start();
    }
}


class SynStringConstantPoolFeatureTask {
    private String shareStr;

    public String getShareStr() {
        return shareStr;
    }

    public void setShareStr(String shareStr) {
        this.shareStr = shareStr;
    }

    public void serviceA() {
        synchronized (shareStr) {
            System.out.println("service A begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service A end time=" + System.currentTimeMillis());
        }
    }

    public void serviceB() {
        synchronized (shareStr) {
            System.out.println("service B begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service B end time=" + System.currentTimeMillis());
        }
    }
}

class SynStringConstantPoolFeatureThreadA extends Thread {
    SynStringConstantPoolFeatureTask synStringConstantPoolFeatureTask;

    public SynStringConstantPoolFeatureThreadA(SynStringConstantPoolFeatureTask synStringConstantPoolFeatureTask) {
        this.synStringConstantPoolFeatureTask = synStringConstantPoolFeatureTask;
    }

    @Override
    public void run() {
        super.run();
        synStringConstantPoolFeatureTask.serviceA();
    }
}

class SynStringConstantPoolFeatureThreadB extends Thread {
    SynStringConstantPoolFeatureTask synStringConstantPoolFeatureTask;

    public SynStringConstantPoolFeatureThreadB(SynStringConstantPoolFeatureTask synStringConstantPoolFeatureTask) {
        this.synStringConstantPoolFeatureTask = synStringConstantPoolFeatureTask;
    }

    @Override
    public void run() {
        super.run();
        synStringConstantPoolFeatureTask.serviceB();
    }
}