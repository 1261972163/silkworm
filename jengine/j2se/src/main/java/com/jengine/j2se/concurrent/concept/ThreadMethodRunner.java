package com.jengine.j2se.concurrent.concept;

import java.util.Random;

/**
 * @author nouuid
 * @date 4/11/2016
 * @description
 */
public class ThreadMethodRunner {

    public void currentThreadMethodTest() {
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

    public void isAliveMethodTest() throws InterruptedException {
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

    public void getIdMethodTest() {
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
