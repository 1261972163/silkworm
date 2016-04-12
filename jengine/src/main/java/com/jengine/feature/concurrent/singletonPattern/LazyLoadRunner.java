package com.jengine.feature.concurrent.singletonPattern;

/**
 * Created by weiyang on 4/9/16.
 */
public class LazyLoadRunner {
    LazyLoadTask lazyLoadTask = new LazyLoadTask();
    LazyLoadTask2 lazyLoadTask2 = new LazyLoadTask2();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            lazyLoadTask.service();
        }
    };

    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            lazyLoadTask2.service();
        }
    };

    public void test() {
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();
    }

    public void test2() {
        Thread t1 = new Thread(runnable2);
        Thread t2 = new Thread(runnable2);
        Thread t3 = new Thread(runnable2);

        t1.start();
        t2.start();
        t3.start();
    }
}

class LazyLoadObject {
    private static LazyLoadObject lazyLoadObject;

    private LazyLoadObject() {

    }

    public static LazyLoadObject getInstance() {
        try {
            if (lazyLoadObject==null) {
                Thread.sleep(1000);
                lazyLoadObject = new LazyLoadObject();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lazyLoadObject;
    }
}

class LazyLoadObject2 {
    private static LazyLoadObject2 lazyLoadObject2;

    private LazyLoadObject2() {

    }

    public static LazyLoadObject2 getInstance() {
        try {
            if (lazyLoadObject2==null) {
                Thread.sleep(1000);
                synchronized (LazyLoadObject2.class) {
                    if (lazyLoadObject2==null) {
                        lazyLoadObject2 = new LazyLoadObject2();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lazyLoadObject2;
    }
}

class LazyLoadTask {
    public void service() {
        System.out.println(Thread.currentThread().getName() + " -> " + LazyLoadObject.getInstance().hashCode());
    }
}

class LazyLoadTask2 {
    public void service() {
        System.out.println(Thread.currentThread().getName() + " -> " + LazyLoadObject2.getInstance().hashCode());
    }
}