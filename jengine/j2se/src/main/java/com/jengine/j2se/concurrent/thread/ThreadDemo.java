package com.jengine.j2se.concurrent.thread;

import com.jengine.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

import java.util.Random;

/**
 * @author nouuid
 * @date 4/11/2016
 * @description
 */
public class ThreadDemo extends ConcurrentTest {

    // 一个线程的start方法只能调用一次，多次调用start方法会抛错java.lang.IllegalThreadStateException
    @Test
    public void multiStart() throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1");
            }
        });
        t.start();
        Thread.sleep(5000);
        // 不管前面启动的线程是否完成，都不能再次启动这个线程了
        t.start();

        Thread.sleep(5000);
    }

    /**
     * 基本使用，打印2,1,3
     * @throws InterruptedException
     */
    @Test
    public void basic() throws InterruptedException {
        ThreadRunner threadRunner = new ThreadRunner();
        threadRunner.basic();
    }

    /**
     * 获取当前线程名称
     * Thread.currentThread().getName()
     * @throws InterruptedException
     */
    @Test
    public void currentThreadName() throws InterruptedException {
        ThreadRunner threadRunner = new ThreadRunner();
        threadRunner.currentThreadName();
        Thread.sleep(10*1000);
    }

    /**
     * 指定线程是否存活
     * thread.isAlive()
     * @throws InterruptedException
     */
    @Test
    public void isAlive() throws InterruptedException {
        ThreadRunner threadRunner = new ThreadRunner();
        threadRunner.isAlive();
        Thread.sleep(10*1000);
    }

    /**
     * 获取当前线程id
     * Thread.currentThread().getId()
     * @throws InterruptedException
     */
    @Test
    public void getId() throws InterruptedException {
        ThreadRunner threadRunner = new ThreadRunner();
        threadRunner.getId();
        Thread.sleep(10*1000);
    }

    /**
     * join
     * A线程在B线程中使用join，B线程等待A线程执行任务完毕，然后B执行完成自己的任务
     * 执行结果: start -> T1 start -> T2 start -> T1 end -> T2 end -> end
     * @throws InterruptedException
     */
    @Test
    public void joinTest() throws InterruptedException {
        ThreadRunner threadRunner = new ThreadRunner();
        threadRunner.joinTest();
        Thread.sleep(10*1000);
    }

    /**
     * 限时join
     * A线程在B线程中使用join，B线程等待x时间，在此期间A可执行自己的任务，然后B执行完成自己的任务
     * 执行结果: start -> T1 start -> T2 start -> T2 end -> T1 end -> end
     * @throws InterruptedException
     */
    @Test
    public void timedJoinTest() throws InterruptedException {
        ThreadRunner threadRunner = new ThreadRunner();
        threadRunner.timedJoinTest();
        Thread.sleep(10*1000);
    }

    /**
     * yield
     *
     * @throws InterruptedException
     */
    @Test
    public void yieldTest() throws InterruptedException {
        ThreadRunner threadRunner = new ThreadRunner();
        threadRunner.yieldTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void priorityTest() throws InterruptedException {
        ThreadRunner threadRunner = new ThreadRunner();
        threadRunner.priorityTest();
        Thread.sleep(30*1000);
    }

    /**
     * daemonTest
     */
    public static void main(String[] args) throws InterruptedException {
        ThreadRunner threadRunner = new ThreadRunner();
        threadRunner.daemonTest();
    }


}

class ThreadRunner {

    public void basic() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println("2");
        Thread.sleep(5000);
        System.out.println("3");
    }

    public void currentThreadName() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " do");
            }
        };

        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(runnable);
        }
        for (int i=0; i<10; i++) {
            threads[i].start();
        }
    }

    public void isAlive() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " do");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(runnable);
            threads[i].setName("T" + i);
        }
        for (int i=0; i<10; i++) {
            threads[i].start();
        }
        while (true) {
            System.out.println("T6 isAlive=" + threads[6].isAlive());
            Thread.sleep(1*1000);
        }
    }

    public void getId() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId() + " do");
            }
        };

        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(runnable);
        }
        for (int i=0; i<10; i++) {
            threads[i].start();
        }
    }

    /**
     * A thread use join method in B thread
     * B thread will wait A thread to finish its task
     * then B thread finish its own task
     *
     */
    public void joinTest() throws InterruptedException {
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " start " + System.currentTimeMillis());
                    Thread.sleep(5*1000);
                    System.out.println(Thread.currentThread().getName() + " end " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread1 = new Thread(runnable1);
        thread1.setName("T1");
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " start " + System.currentTimeMillis());
                    thread1.join();
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " end " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread2 = new Thread(runnable2);
        thread2.setName("T2");

        thread1.start();
        Thread.sleep(1000);
        thread2.start();
    }

    public void timedJoinTest() throws InterruptedException {
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " start " + System.currentTimeMillis());
                    Thread.sleep(5*1000);
                    System.out.println(Thread.currentThread().getName() + " end " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread1 = new Thread(runnable1);
        thread1.setName("T1");
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " start " + System.currentTimeMillis());
                    thread1.join(2000);
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " end " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread2 = new Thread(runnable2);
        thread2.setName("T2");

        thread1.start();
        Thread.sleep(1000);
        thread2.start();
    }

    /**
     * yield used thread will abandon CPU resource
     */
    public void yieldTest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int count = 0;
                long start = System.currentTimeMillis();
                for (int i=0; i<10000000; i++) {
                    Thread.yield();
                    count = count + 1;
                }
                long end = System.currentTimeMillis();
                System.out.println("cost=" + (end-start) + " mills");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * higher priority thread has more probability to run
     */
    public void priorityTest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int count = 0;
                System.out.println(Thread.currentThread().getName() + " start");
                for (int i=0; i<10000000; i++) {
                    Random random = new Random();
                    random.nextInt();
                    count = count + 1;
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName());
                }
                System.out.println("                           " + Thread.currentThread().getName() + " end");
            }
        };
        for (int i=0; i<5; i++) {
            Thread threadA = new Thread(runnable);
            threadA.setName("TA" + i);
            threadA.setPriority(1);
            threadA.start();

            Thread threadB = new Thread(runnable);
            threadB.setName("TB" + i);
            threadB.setPriority(10);
            threadB.start();
        }
    }

    public void daemonTest() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int count = 0;
                    while (true) {
                        count++;
                        System.out.println("count=" + count);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t1 = new Thread(runnable);
        t1.setName("T1");
//        t1.setDaemon(true);
        t1.start();

        Thread.sleep(5000);
        System.out.println("finish");
    }

}
