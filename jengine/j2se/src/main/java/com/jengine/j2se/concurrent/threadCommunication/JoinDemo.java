package com.jengine.j2se.concurrent.threadCommunication;

import com.jengine.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * @join含义 当前线程z阻塞，等待join所属线程x执行完毕，x销毁后，z继续执行
 *
 * @join等待时间 join(long)当前线程z阻塞long时间后，继续执行
 *
 * @join等待时间和sleep等待时间的区别
 *
 * join(long)内部使用wait(long)实现，具有释放锁的特点
 * sleep(long)不释放锁
 *
 * @author nouuid
 * @date 4/5/2016
 * @description
 */
public class JoinDemo extends ConcurrentTest {

    /**
     * 主线程执行到childThread.start();等待childThread完成后继续执行
     * @throws Exception
     */
    @Test
    public void join() throws Exception{
        System.out.println("--------1");
        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2*1000);
                    System.out.println("--------4");
                    Thread.sleep(2*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("--------5");
            }
        });
        System.out.println("--------2");
        childThread.start();
        System.out.println("--------3");
        try {
            childThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--------6");
    }

    /**
     * join阻塞时间到后，当前线程继续执行
     * @throws Exception
     */
    @Test
    public void timedJoin() throws Exception {
        // 超时情况
        System.out.println("--------1");
        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1*1000);
                    System.out.println("--------4");
                    Thread.sleep(3*1000);
                    System.out.println("--------6");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("--------2");
        childThread.start();
        System.out.println("--------3");
        try {
            childThread.join(3*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--------5");
        Thread.sleep(3*1000);
        System.out.println("--------7");

        //未超时情况
        Thread.sleep(3*1000);
        Thread childThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1*1000);
                    System.out.println("--------10");
                    Thread.sleep(3*1000);
                    System.out.println("--------11");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("--------8");
        childThread2.start();
        System.out.println("--------9");
        childThread2.join(5*1000);
        System.out.println("--------12");
    }

    /**
     * sleep不释放锁
     * @throws Exception
     */
    @Test
    public void sleepTest() throws Exception {

        class ServiceThread extends Thread {
            @Override
            public void run() {
                try {
                    System.out.println("---------3");
                    Thread.sleep(3*1000);
                    System.out.println("---------4");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            synchronized public void service() {
                System.out.println("---------6");
            }
        }
        ServiceThread serviceThread = new ServiceThread();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (serviceThread) {
                        System.out.println("---------1");
                        Thread.sleep(2000);
                        serviceThread.start();
                        Thread.sleep(5*1000);
                        System.out.println("---------5");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("---------2");
                serviceThread.service();
                System.out.println("---------7");
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(20*1000);
    }

    /**
     * join释放锁
     * @throws Exception
     */
    @Test
    public void joinTest() throws Exception {
        class ServiceThread extends Thread {
            @Override
            public void run() {
                try {
                    System.out.println("---------5");
                    Thread.sleep(3*1000);
                    System.out.println("---------6");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            synchronized public void service() {
                System.out.println("---------3");
            }
        }
        ServiceThread serviceThread = new ServiceThread();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (serviceThread) {
                        System.out.println("---------1");
                        Thread.sleep(3000);
                        serviceThread.start();
                        serviceThread.join(10*1000);
                        Thread.sleep(5*1000);
                        System.out.println("---------7");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("---------2");
                    serviceThread.service();
                    System.out.println("---------4");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(20*1000);

    }
}
