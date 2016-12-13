package com.jengine.j2se.concurrent.lock;

import com.jengine.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * @author nouuid
 * @date 3/31/2016
 * @description
 */
public class SynchronizedDemo extends ConcurrentTest {

    /**
     *
     * @throws InterruptedException
     */
    @Test
    public void synBlock()  throws InterruptedException {
        SynBlockRunner synBlockRunner = new SynBlockRunner();
        synBlockRunner.test();

        Thread.sleep(20*1000);
    }

    /**
     * @throws InterruptedException
     */
    @Test
    public void halfSynAndAsyn() throws InterruptedException {
        HalfSynAndAsynRunner halfSynAndAsynRunner = new HalfSynAndAsynRunner();
        halfSynAndAsynRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     *
     * @throws InterruptedException
     */
    @Test
    public void synBlocksSynchronism() throws InterruptedException {
        SynBlocksSynchronismRunner synBlocksSynchronismRunner = new SynBlocksSynchronismRunner();
        synBlocksSynchronismRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     *
     * @throws InterruptedException
     */
    @Test
    public void synBlockObject() throws InterruptedException {
        SynBlockObjectRunner synBlockObjectRunner = new SynBlockObjectRunner();
        synBlockObjectRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     *
     * @throws InterruptedException
     */
    @Test
    public void synStaticMethod() throws InterruptedException {
        SynStaticMethodRunner synStaticMethodRunner = new SynStaticMethodRunner();
        synStaticMethodRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * 锁class
     * @throws InterruptedException
     */
    @Test
    public void synClass() throws InterruptedException {
        SynClassRunner synClassRunner = new SynClassRunner();
        synClassRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * 锁String对象
     * @throws InterruptedException
     */
    @Test
    public void synStringConstantPoolFeature() throws InterruptedException {
        SynStringConstantPoolFeatureRunner synStringConstantPoolFeatureRunner = new SynStringConstantPoolFeatureRunner();
        synStringConstantPoolFeatureRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * 死锁
     * @throws InterruptedException
     */
    @Test
    public void deadLock() throws InterruptedException {
        DeadLockRunner deadLockRunner = new DeadLockRunner();
        deadLockRunner.test();
        Thread.sleep(30*60*1000);
    }

    /**
     * 锁静态内部类
     * @throws InterruptedException
     */
    @Test
    public void synStaticInnerClass() throws InterruptedException {
        SynStaticInnerClassRunner synStaticInnerClassRunner = new SynStaticInnerClassRunner();
        synStaticInnerClassRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * 锁内部类
     * @throws InterruptedException
     */
    @Test
    public void synInnerClass() throws InterruptedException {
        SynInnerClassRunner synInnerClassRunner = new SynInnerClassRunner();
        synInnerClassRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * 锁住的对象发生变化
     * @throws InterruptedException
     */
    @Test
    public void synBlockObjectChanged() throws InterruptedException {
        SynBlockObjectChangedRunner synBlockObjectChangedRunner = new SynBlockObjectChangedRunner();
        synBlockObjectChangedRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * 无共享数据，线程安全
     * @throws InterruptedException
     */
    @Test
    public void noDataShareCounterRunnerTest() throws InterruptedException {
        NoDataShareCounterRunner noDataShareCounterRunner = new NoDataShareCounterRunner();
        noDataShareCounterRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * 使用共享数据的方法加synchronized，线程安全
     * @throws InterruptedException
     */
    @Test
    public void dataShareCounterSafeRunnerTest() throws InterruptedException {
        DataShareCounterSafeRunner dataShareCounterSafeRunner = new DataShareCounterSafeRunner();
        dataShareCounterSafeRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * 方法不加synchronized，非线程安全
     * @throws InterruptedException
     */
    @Test
    public void dataShareCounterUnsafeRunnerTest() throws InterruptedException {
        DataShareCounterUnsafeRunner dataShareCounterUnsafeRunner = new DataShareCounterUnsafeRunner();
        dataShareCounterUnsafeRunner.test();
        Thread.sleep(10*1000);
    }

    /**
     * synchronized一个方法线程安全，另一个方法非线程安全
     * @throws InterruptedException
     */
    @Test
    public void synOneMethod() throws InterruptedException {
        SynOneMethodRunner synOneMethodRunner = new SynOneMethodRunner();
        synOneMethodRunner.oneMethodSynchronized();
        Thread.sleep(10*1000);
    }

    /**
     * synchronized所有方法，所有方法线程安全
     * @throws InterruptedException
     */
    @Test
    public void synAllMethod() throws InterruptedException {
        SynAllMethodRunner synAllMethodRunner = new SynAllMethodRunner();
        synAllMethodRunner.allMethodSynchronized();
        Thread.sleep(10*1000);
    }

    /**
     * synchronized是可重入的
     * @throws InterruptedException
     */
    @Test
    public void synLockIn() throws InterruptedException {
        SynLockInRunner synLockInRunner = new SynLockInRunner();
        synLockInRunner.lockIn();
        Thread.sleep(10*1000);
    }
}

//======================================================================================================================

/**
 * @author nouuid
 * @date 3/30/2016
 * @description
 *
 * synchronized block is superior to synchronized method
 */
class SynBlockRunner {

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


//======================================================================================================================

class HalfSynAndAsynRunner {

    public void test() {
        HalfSynAndAsynTask halfSynAndAsynTask = new HalfSynAndAsynTask();
        Thread t1 = new Thread(halfSynAndAsynTask);
        Thread t2 = new Thread(halfSynAndAsynTask);
        t1.start();
        t2.start();
    }
}

class HalfSynAndAsynTask implements Runnable {

    public void print() {
        for (int i=0; i<100; i++) {
            System.out.println("nosynchronized thread name = " + Thread.currentThread().getName() + "-" + i);
        }

        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                System.out.println("synchronized thread name = " + Thread.currentThread().getName() + "-" + i);
            }
        }
    }

    @Override
    public void run() {
        print();
    }
}

//======================================================================================================================

/**
 * @author nouuid
 * @date 3/30/2016
 * @description
 *
 * synchronized block, lock specified Object
 * different Object specified, has different synchronism
 */
class SynBlocksSynchronismRunner {
    public void test() {
        SynBlocksSynchronismTask synBlocksSynchronismTask = new SynBlocksSynchronismTask();
        SynBlocksSynchronismThreadA synBlocksSynchronismThreadA = new SynBlocksSynchronismThreadA(synBlocksSynchronismTask);
        SynBlocksSynchronismThreadB synBlocksSynchronismThreadB = new SynBlocksSynchronismThreadB(synBlocksSynchronismTask);
        synBlocksSynchronismThreadA.start();
        synBlocksSynchronismThreadB.start();
    }
}

class SynBlocksSynchronismThreadA extends Thread {
    SynBlocksSynchronismTask synBlocksSynchronismTask;

    public SynBlocksSynchronismThreadA(SynBlocksSynchronismTask synBlocksSynchronismTask) {
        this.synBlocksSynchronismTask = synBlocksSynchronismTask;
    }

    @Override
    public void run() {
        super.run();
        synBlocksSynchronismTask.serviceA();
    }
}

class SynBlocksSynchronismThreadB extends Thread {
    SynBlocksSynchronismTask synBlocksSynchronismTask;

    public SynBlocksSynchronismThreadB(SynBlocksSynchronismTask synBlocksSynchronismTask) {
        this.synBlocksSynchronismTask = synBlocksSynchronismTask;
    }


    @Override
    public void run() {
        super.run();
        synBlocksSynchronismTask.serviceB();
    }
}

class SynBlocksSynchronismTask {
    public void serviceA() {
        synchronized (this) {
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
        synchronized (this) {
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

//======================================================================================================================

/**
 * @author nouuid
 * @date 3/30/2016
 * @description
 *
 * synchronized block, lock specified Object
 * different Object specified, has different synchronism
 */
class SynBlockObjectRunner {
    public void test() {
        SynBlockObjectTask synBlockObjectTask = new SynBlockObjectTask();
        SynBlockObjectThreadA synBlockObjectThreadA = new SynBlockObjectThreadA(synBlockObjectTask);
        SynBlockObjectThreadB synBlockObjectThreadB = new SynBlockObjectThreadB(synBlockObjectTask);
        SynBlockObjectThreadC synBlockObjectThreadC = new SynBlockObjectThreadC(synBlockObjectTask);
        synBlockObjectThreadA.start();
        synBlockObjectThreadB.start();
        synBlockObjectThreadC.start();
    }
}

class SynBlockObjectTask {
    Object anyObject = new Object();

    public void serviceA() {
        synchronized (anyObject) {
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
        synchronized (anyObject) {
            System.out.println("service B begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service B end time=" + System.currentTimeMillis());
        }
    }

    public void serviceC() {
        synchronized (this) {
            System.out.println("service C begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service C end time=" + System.currentTimeMillis());
        }
    }
}

class SynBlockObjectThreadA extends Thread {
    SynBlockObjectTask synBlockObjectTask;

    public SynBlockObjectThreadA(SynBlockObjectTask synBlockObjectTask) {
        this.synBlockObjectTask = synBlockObjectTask;
    }

    @Override
    public void run() {
        super.run();
        synBlockObjectTask.serviceA();
    }
}

class SynBlockObjectThreadB extends Thread {
    SynBlockObjectTask synBlockObjectTask;

    public SynBlockObjectThreadB(SynBlockObjectTask synBlockObjectTask) {
        this.synBlockObjectTask = synBlockObjectTask;
    }

    @Override
    public void run() {
        super.run();
        synBlockObjectTask.serviceB();
    }
}

class SynBlockObjectThreadC extends Thread {
    SynBlockObjectTask synBlockObjectTask;

    public SynBlockObjectThreadC(SynBlockObjectTask synBlockObjectTask) {
        this.synBlockObjectTask = synBlockObjectTask;
    }

    @Override
    public void run() {
        super.run();
        synBlockObjectTask.serviceC();
    }
}

//======================================================================================================================

/**
 * @author nouuid
 * @date 3/31/2016
 * @description
 *
 * synchronized method, lock Object
 * synchronized static method, lock Class
 */
class SynStaticMethodRunner {
    public void test() {
        SynStaticMethodTask synStaticMethodTask = new SynStaticMethodTask();
        SynStaticMethodThreadA synStaticMethodThreadA = new SynStaticMethodThreadA(synStaticMethodTask);
        SynStaticMethodThreadB synStaticMethodThreadB = new SynStaticMethodThreadB(synStaticMethodTask);
        SynStaticMethodThreadC synStaticMethodThreadC = new SynStaticMethodThreadC(synStaticMethodTask);
        synStaticMethodThreadA.start();
        synStaticMethodThreadB.start();
        synStaticMethodThreadC.start();
    }
}

class SynStaticMethodTask {
    synchronized public static void serviceA() {
        System.out.println("service A begin time=" + System.currentTimeMillis());
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("service A end time=" + System.currentTimeMillis());
    }

    synchronized public static void serviceB() {
        System.out.println("service B begin time=" + System.currentTimeMillis());
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("service B end time=" + System.currentTimeMillis());
    }

    synchronized public void serviceC() {
        System.out.println("service C begin time=" + System.currentTimeMillis());
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("service C end time=" + System.currentTimeMillis());
    }
}

class SynStaticMethodThreadA extends Thread {
    SynStaticMethodTask synStaticMethodTask;

    public SynStaticMethodThreadA(SynStaticMethodTask synStaticMethodTask) {
        this.synStaticMethodTask = synStaticMethodTask;
    }

    @Override
    public void run() {
        super.run();
        synStaticMethodTask.serviceA();
    }
}

class SynStaticMethodThreadB extends Thread {
    SynStaticMethodTask synStaticMethodTask;

    public SynStaticMethodThreadB(SynStaticMethodTask synStaticMethodTask) {
        this.synStaticMethodTask = synStaticMethodTask;
    }

    @Override
    public void run() {
        super.run();
        synStaticMethodTask.serviceB();
    }
}

class SynStaticMethodThreadC extends Thread {
    SynStaticMethodTask synStaticMethodTask;

    public SynStaticMethodThreadC(SynStaticMethodTask synStaticMethodTask) {
        this.synStaticMethodTask = synStaticMethodTask;
    }

    @Override
    public void run() {
        super.run();
        synStaticMethodTask.serviceC();
    }
}

//======================================================================================================================

/**
 * @author nouuid
 * @date 3/30/2016
 * @description
 *
 * lock Class
 */
class SynClassRunner {
    public void test() {
        SynClassTask synClassTask = new SynClassTask();
        SynClassThreadA synClassThreadA = new SynClassThreadA(synClassTask);
        SynClassThreadB synClassThreadB = new SynClassThreadB(synClassTask);
        SynClassThreadC synClassThreadC = new SynClassThreadC(synClassTask);
        synClassThreadA.start();
        synClassThreadB.start();
        synClassThreadC.start();
    }
}

class SynClassTask {
    public static void serviceA() {
        synchronized (SynClassTask.class) {
            System.out.println("service A begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service A end time=" + System.currentTimeMillis());
        }
    }

    public static void serviceB() {
        synchronized (SynClassTask.class) {
            System.out.println("service B begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service B end time=" + System.currentTimeMillis());
        }
    }

    public void serviceC() {
        synchronized (SynClassTask.class) {
            System.out.println("service C begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("service C end time=" + System.currentTimeMillis());
        }
    }
}

class SynClassThreadA extends Thread {
    SynClassTask synClassTask;

    public SynClassThreadA(SynClassTask synClassTask) {
        this.synClassTask = synClassTask;
    }

    @Override
    public void run() {
        super.run();
        synClassTask.serviceA();
    }
}

class SynClassThreadB extends Thread {
    SynClassTask synClassTask;

    public SynClassThreadB(SynClassTask synClassTask) {
        this.synClassTask = synClassTask;
    }

    @Override
    public void run() {
        super.run();
        synClassTask.serviceB();
    }
}

class SynClassThreadC extends Thread {
    SynClassTask synClassTask;

    public SynClassThreadC(SynClassTask synClassTask) {
        this.synClassTask = synClassTask;
    }

    @Override
    public void run() {
        super.run();
        synClassTask.serviceC();
    }
}

//======================================================================================================================

/**
 * @author nouuid
 * @date 3/31/2016
 * @description
 *
 * String has constant pool feature
 * we prefer not to use String as part of synchronized(...)
 */
class SynStringConstantPoolFeatureRunner {
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

//======================================================================================================================

class DeadLockRunner {

    public void test() {
        DeadLockTask deadLockTask = new DeadLockTask();
        DeadLockThreadA deadLockThreadA = new DeadLockThreadA(deadLockTask);
        DeadLockThreadB deadLockThreadB = new DeadLockThreadB(deadLockTask);
        deadLockThreadA.start();
        deadLockThreadB.start();
    }
}

class DeadLockThreadA extends Thread {
    DeadLockTask deadLockTask;

    public DeadLockThreadA(DeadLockTask deadLockTask) {
        this.deadLockTask = deadLockTask;
    }

    @Override
    public void run() {
        super.run();
        deadLockTask.serviceA();
    }
}

class DeadLockThreadB extends Thread {
    DeadLockTask deadLockTask;

    public DeadLockThreadB(DeadLockTask deadLockTask) {
        this.deadLockTask = deadLockTask;
    }

    @Override
    public void run() {
        super.run();
        deadLockTask.serviceB();
    }
}

class DeadLockTask {

    Object o1 = new Object();
    Object o2 = new Object();

    public void serviceA() {
        synchronized (o1) {
            System.out.println("service A begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2) {
                System.out.println("service A end time=" + System.currentTimeMillis());
            }
        }
    }

    public void serviceB() {
        synchronized (o2) {
            System.out.println("service B begin time=" + System.currentTimeMillis());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o1) {
                System.out.println("service B end time=" + System.currentTimeMillis());
            }
        }
    }
}

//======================================================================================================================

class SynStaticInnerClassRunner {

    public void test() {
        final SynStaticInnerClassOuterClass.SynStaticInnerClassInnerClassA synStaticInnerClassInnerClassA = new SynStaticInnerClassOuterClass.SynStaticInnerClassInnerClassA();
        final SynStaticInnerClassOuterClass.SynStaticInnerClassInnerClassB synStaticInnerClassInnerClassB = new SynStaticInnerClassOuterClass.SynStaticInnerClassInnerClassB();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synStaticInnerClassInnerClassA.service1(synStaticInnerClassInnerClassB);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synStaticInnerClassInnerClassA.service2();
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synStaticInnerClassInnerClassB.service1();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}

class SynStaticInnerClassOuterClass {
    static class SynStaticInnerClassInnerClassA {
        public void service1(SynStaticInnerClassInnerClassB synStaticInnerClassInnerClass2) {
            synchronized (synStaticInnerClassInnerClass2) {
                System.out.println("A service1 begin");
                try {
                    Thread.sleep(2*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A service1 end");
            }
        }

        synchronized public void service2() {
            System.out.println("A service2 begin");
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A service2 end");
        }
    }

    static class SynStaticInnerClassInnerClassB {
        synchronized public void service1() {
            System.out.println("B service1 begin");
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B service1 end");
        }
    }
}

//======================================================================================================================

class SynInnerClassRunner {
    public void test() {
        SynInnerClassOuterClass synInnerClassOuterClass = new SynInnerClassOuterClass();
        SynInnerClassOuterClass.SynInnerClassInnerClassA synInnerClassInnerClassA = synInnerClassOuterClass.new SynInnerClassInnerClassA();
        SynInnerClassOuterClass.SynInnerClassInnerClassB synInnerClassInnerClassB = synInnerClassOuterClass.new SynInnerClassInnerClassB();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synInnerClassInnerClassA.service1(synInnerClassInnerClassB);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synInnerClassInnerClassA.service2();
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synInnerClassInnerClassB.service1();
            }
        });

//        t1.start();
        t2.start();
        t3.start();
    }
}

class SynInnerClassOuterClass {
    class SynInnerClassInnerClassA {
        public void service1(SynInnerClassInnerClassB synInnerClassInnerClassB) {
            synchronized (synInnerClassInnerClassB) {
                System.out.println("A service1 begin");
                try {
                    Thread.sleep(2*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A service1 end");
            }
        }

        synchronized public void service2() {
            System.out.println("A service2 begin");
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A service2 end");
        }
    }

    class SynInnerClassInnerClassB {
        synchronized public void service1() {
            System.out.println("B service1 begin");
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B service1 end");
        }
    }
}

//======================================================================================================================

class SynBlockObjectChangedRunner {

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

//======================================================================================================================

class NoDataShareCounterRunner {
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

//======================================================================================================================

class DataShareCounterSafeRunner {
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

//======================================================================================================================

class DataShareCounterUnsafeRunner {
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

//======================================================================================================================

class SynOneMethodRunner {
    public void oneMethodSynchronized() {
        SynOneMethodCounter synchronizedCounter = new SynOneMethodCounter();
        SynOneMethodThreadA ta = new SynOneMethodThreadA(synchronizedCounter);
        SynOneMethodThreadB tb = new SynOneMethodThreadB(synchronizedCounter);
        ta.start();
        tb.start();
    }
}

class SynOneMethodCounter {
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

    public void add2(String name) {
        if ("a".equals(name)) {
            num = 100;
            System.out.println("a add2 over");
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if ("b".equals(name)) {
            num = 200;
            System.out.println("b add2 over");
        }
        System.out.println(name + " set " + num);
    }
}



class SynOneMethodThreadA extends Thread {
    private SynOneMethodCounter synOneMethodCounter;

    public SynOneMethodThreadA(SynOneMethodCounter synOneMethodCounter) {
        this.synOneMethodCounter = synOneMethodCounter;
    }

    @Override
    public void run() {
        super.run();
        synOneMethodCounter.add("a");
    }
}

class SynOneMethodThreadB extends Thread {
    private SynOneMethodCounter synOneMethodCounter;

    public SynOneMethodThreadB(SynOneMethodCounter synOneMethodCounter) {
        this.synOneMethodCounter = synOneMethodCounter;
    }

    @Override
    public void run() {
        super.run();
        synOneMethodCounter.add2("b");
    }
}

//======================================================================================================================

class SynAllMethodRunner {
    public void allMethodSynchronized() {
        SynAllMethodCounter synAllMethodCounter = new SynAllMethodCounter();
        SynAllMethodThreadA ta = new SynAllMethodThreadA(synAllMethodCounter);
        SynAllMethodThreadB tb = new SynAllMethodThreadB(synAllMethodCounter);
        ta.start();
        tb.start();
    }
}

class SynAllMethodCounter {
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

    synchronized public void add2(String name) {
        if ("a".equals(name)) {
            num = 100;
            System.out.println("a add2 over");
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if ("b".equals(name)) {
            num = 200;
            System.out.println("b add2 over");
        }
        System.out.println(name + " set " + num);
    }
}

class SynAllMethodThreadA extends Thread {
    private SynAllMethodCounter synAllMethodCounter;

    public SynAllMethodThreadA(SynAllMethodCounter synAllMethodCounter) {
        this.synAllMethodCounter = synAllMethodCounter;
    }

    @Override
    public void run() {
        super.run();
        synAllMethodCounter.add("a");
    }
}

class SynAllMethodThreadB extends Thread {
    private SynAllMethodCounter synAllMethodCounter;

    public SynAllMethodThreadB(SynAllMethodCounter synAllCounter) {
        this.synAllMethodCounter = synAllCounter;
    }

    @Override
    public void run() {
        super.run();
        synAllMethodCounter.add2("b");
    }
}

//======================================================================================================================

class SynLockInRunner {

    public void lockIn() {
        SynLockInThread synLockInThread = new SynLockInThread();
        synLockInThread.start();
    }
}

class SynLockInThread extends Thread {
    @Override
    public void run() {
        super.run();
        SynLockInService synLockInService = new SynLockInService();
        synLockInService.service1();
    }
}

class SynLockInService {
    synchronized public void service1() {
        service2();
        System.out.println("service1");
    }

    synchronized public void service2() {
        service3();
        System.out.println("service2");
    }

    synchronized public void service3() {
        System.out.println("service3");
    }
}

//======================================================================================================================
//======================================================================================================================
//======================================================================================================================
//======================================================================================================================