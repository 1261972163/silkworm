package com.jengine.common2.j2se.concurrent.lock;

import com.jengine.common2.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * 1. synchronized是互斥同步的基本手段，synchronized的对象会阻塞其他线程的访问
 * 2. synchronized普通方法 锁定的是当前对象
 * 3. synchronized块可以锁定任意对象
 * 4. 锁定不同的对象，产生不同的同步互斥
 * 5. synchronized块锁定Class
 * 6. synchronized静态方法，锁定的是Class
 * 7. synchronized块 优于 synchronized方法
 * 8. 锁定String时，因为String的常量池特性，synchronized同步块不能使用String作为锁对象
 * 9. 只要锁住的对象不变，对象属性变化对同步没有影响
 * 10. 使用共享数据的方法加synchronized，线程安全
 * 11. synchronized是可重入的，同一个线程可以多次获取该对象锁
 *
 *
 * @author nouuid
 * @date 3/31/2016
 * @description
 */
public class SynchronizedDemo extends ConcurrentTest {

    // 1. synchronized阻塞其他线程的访问
    @Test
    public void synBlock()  throws InterruptedException {
        class SynBlockTask {
            public synchronized void doLongTimeTask() {
                try {
                    int i = 1;
                    while (i<=10) {
                        System.out.println(Thread.currentThread().getName() + " -> " + i);
                        Thread.sleep(500);
                        i++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        SynBlockTask synBlockTask = new SynBlockTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.doLongTimeTask();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.doLongTimeTask();
            }
        }, "t2");
        t1.start();
        Thread.sleep(1000);
        // t1先获取对象锁，t2不能获取到锁，被阻塞
        t2.start();

        Thread.sleep(20*1000);
    }

    // 2. synchronized方法获取的是当前对象锁
    // t1先获取对象锁，t2无法获取this锁，被阻塞，说明t1获取的也是this锁，t1执行完后t2
    @Test
    public void synBlocksSynchronism() throws InterruptedException {
        class SynBlockTask {
            public synchronized void m1() {
                try {
                    Thread.sleep(3*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }

            public void m2() {
                synchronized (this) {
                    System.out.println(Thread.currentThread().getName());
                }
            }
        }

        SynBlockTask synBlockTask = new SynBlockTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.m1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.m2();
            }
        }, "t2");
        t1.start();
        Thread.sleep(1000);
        // t1先获取对象锁，t2不能获取到锁，被阻塞
        t2.start();
        Thread.sleep(5*1000);
    }

    // 3. synchronized块可以锁定任意对象
    @Test
    public void synBlockObject() throws InterruptedException {
        class SynBlockTask {
            Object object = new Object();
            public void m1() {
                synchronized (object) {
                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
            }

            public void m2() {
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName());
                }
            }

        }

        SynBlockTask synBlockTask = new SynBlockTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.m1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.m2();
            }
        }, "t2");
        // t1获取object锁
        t1.start();
        Thread.sleep(1000);
        // t2被阻塞无法获取object锁
        t2.start();
        // 结果：t1、t2
        Thread.sleep(5*1000);
    }

    // 4. 锁定不同的对象，产生不同的同步互斥
    @Test
    public void synBlockDifferentObject() throws InterruptedException {
        class SynBlockTask {
            Object object = new Object();
            Object object2 = new Object();
            public void m1() {
                synchronized (object) {
                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
            }

            public void m2() {
                synchronized (object2) {
                    System.out.println(Thread.currentThread().getName());
                }
            }
        }

        SynBlockTask synBlockTask = new SynBlockTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.m1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.m2();
            }
        }, "t3");
        // t1获取object锁
        t1.start();
        Thread.sleep(1000);
        // t2获取object2锁，和t1锁不互斥
        t2.start();
        // 结果：t2、t1
        Thread.sleep(5*1000);
    }

    // 5. synchronized块锁定Class
    @Test
    public void synBlockClass() throws InterruptedException {
        class SynBlockTask {
            public void m1() {
                synchronized (SynchronizedDemo.class) {
                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
            }

            public void m2() {
                synchronized (SynchronizedDemo.class) {
                    System.out.println(Thread.currentThread().getName());
                }
            }
        }

        SynBlockTask synBlockTask = new SynBlockTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.m1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.m2();
            }
        }, "t2");
        // t1获取SynchronizedDemo.class锁
        t1.start();
        Thread.sleep(1000);
        // t2被阻塞无法获取SynchronizedDemo.class锁
        t2.start();
        // 结果：t1、t2
        Thread.sleep(5*1000);
    }

    // 6. synchronized静态方法，锁定的是Class
    @Test
    public void synStaticMethod() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                SynStaticMethodTask.m1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                SynStaticMethodTask.m2();
            }
        }, "t2");
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                SynStaticMethodTask.m3();
            }
        }, "t3");
        // t1获取Class锁
        t1.start();
        Thread.sleep(1000);
        // t2被阻塞在获取Class锁的时候
        t2.start();
        // t3获取的是Object对象锁，和t1、t2不互斥
        t3.start();
        // 结果：t3、t1、t2
        Thread.sleep(5*1000);
    }

    // 8. 锁定String时，一定要注意String的常量池特性
    // 直接赋值的String是相同的对象，synchronized同步块不能使用String作为锁对象
    // new String()出来的对象是不相同的，
    @Test
    public void synStringConstantPoolFeature() throws InterruptedException {
        class SynStringConstantPoolFeatureTask {
            public String str1;
            public String str2;
            public void m1() {
                synchronized (str1) {
                    try {
                        Thread.sleep(2*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
            }

            public void m2() {
                synchronized (str2) {
                    System.out.println(Thread.currentThread().getName());
                }
            }
        }
        SynStringConstantPoolFeatureTask sscpft = new SynStringConstantPoolFeatureTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                sscpft.m1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                sscpft.m2();
            }
        }, "t2");
        sscpft.str1 = "XXX";
        sscpft.str2 = "XXX";
        // t1获取str1锁
        t1.start();
        Thread.sleep(1000);
        // t2准备获取str2锁，但被阻塞无法获取str2锁
        t2.start();
        // 结果：t1、t2
        Thread.sleep(5*1000);
    }

    // 9. 只要锁住的对象不变，对象属性变化对同步没有影响
    @Test
    public void synBlockObjectChanged() throws InterruptedException {
        class MyLock {
            public String value = "value";
        }
        class SynBlockTask {
            public MyLock myLock = new MyLock();
            public void m1() {
                synchronized (myLock) {
                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
            }

            public void m2() {
                synchronized (myLock) {
                    System.out.println(Thread.currentThread().getName());
                }
            }

        }

        SynBlockTask synBlockTask = new SynBlockTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.m1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synBlockTask.m2();
            }
        }, "t2");
        // t1获取myLock锁
        t1.start();
        Thread.sleep(1000);
        synBlockTask.myLock.value = new String("value2");
        // t2被阻塞无法获取myLock锁
        t2.start();
        // 结果：t1、t2
        Thread.sleep(5*1000);
    }

    // 10. 使用共享数据的方法加synchronized，线程安全
    // 共享数据线程不安全和线程安全的实现
    @Test
    public void dataShareCounterSafeRunnerTest() throws InterruptedException {
        // 线程不安全
        class DataShareUnsafeCounter {
            private int num;
            public void add1() { num += 1; }
            public void add2() { num += 2; }
            public int get() { return num; }
        }

        DataShareUnsafeCounter unsafeCounter = new DataShareUnsafeCounter();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i<=10000) {
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    unsafeCounter.add1();
                    i++;
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i<=10000) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    unsafeCounter.add2();
                    i++;
                }
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(40*1000);
        System.out.println(unsafeCounter.get());

        // 线程安全
        class DataShareSafeCounter {
            private int num;
            public synchronized void add1() { num += 1; }
            public synchronized void add2() { num += 2; }
            public synchronized int get() { return num; }
        }
        DataShareSafeCounter counter = new DataShareSafeCounter();
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i<=10000) {
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter.add1();
                    i++;
                }
            }
        });
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i<=10000) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter.add2();
                    i++;
                }
            }
        });
        t3.start();
        t4.start();
        Thread.sleep(40*1000);
        System.out.println(counter.get());
    }

    // 11. synchronized是可重入的，同一个线程可以多次获取该对象锁
    @Test
    public void synReentrant() throws InterruptedException {
        class ReentrantTask {
            public synchronized void m1() {
                // 再次获取锁
                m2();
                System.out.println(Thread.currentThread().getName() + "-m1");
            }
            public synchronized void m2() {
                System.out.println(Thread.currentThread().getName() + "-m2");
            }
        }
        ReentrantTask reentrantTask = new ReentrantTask();
        // 获取锁
        reentrantTask.m1();
        Thread.sleep(3*1000);
    }

}

class SynStaticMethodTask {
    public synchronized static void m1() {
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }

    public static void m2() {
        synchronized (SynStaticMethodTask.class) {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void m3() {
        synchronized (new Object()) {
            System.out.println(Thread.currentThread().getName());
        }
    }
}