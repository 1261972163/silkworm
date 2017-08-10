package com.jengine.common2.j2se.concurrent.threadCommunication;

import com.jengine.common2.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * join原理：
 *      join是由wait实现的，wait被join的线程
 *
 * 1. join()：当前线程z阻塞，等待join所属线程x执行完毕，x销毁后，z继续执行
 * 2. join(long)：当前线程z阻塞long时间后，继续执行
 * 3. join(long)内部使用wait(long)实现，释放线程对象的锁，一定要注意是【线程对象的锁】
 * 4. sleep(long)不释放锁
 *
 * @author nouuid
 * @date 4/5/2016
 * @description
 */
public class JoinDemo extends ConcurrentTest {

    // 1. join()：当前线程z阻塞，等待join所属线程x执行完毕，x销毁后，z继续执行
    @Test
    public void join() throws Exception{
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }, "t1");
        // 主线程执行到t1.start();等待t1完成后继续执行
        t1.start();
        t1.join();
        // 先打印t1，后打印main
        System.out.println(Thread.currentThread().getName());
    }

    // 2. join(long)：当前线程z阻塞long时间后，继续执行
    @Test
    public void timedJoin() throws Exception{
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }, "t1");
        // 主线程执行到t1.start();等待t1完成后继续执行
        t1.start();
        t1.join(1000);
        // 先打印main，后打印t1
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(3000);
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
     * join释放线程对象的锁，一定要注意是【线程对象的锁】
     * @throws Exception
     */
    @Test
    public void joinRelaseThreadObjectLock() throws Exception {
//    public static void main(String[] args) throws InterruptedException {
        Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }, "t0");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (t0) {
                    try {
                        // 当前线程wait，释放的是t0线程对象的锁
                        t0.join(100);
//                        t0.wait(0);
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, "t1");

        // t0运行
        t0.start();
        Thread.sleep(10);
        // t1获取myTask的锁
        t1.start();
        Thread.sleep(10);
        // t1持有myTask的锁，主线程获取不到锁
        // t1进入join后释放myTask的锁，主线程获取到锁，先打印main
        synchronized (t0) {
            System.out.println(Thread.currentThread().getName());
        }
        // t1等待t0执行完再执行，打印顺序为t0,t1
        Thread.sleep(5000);

    }
}
