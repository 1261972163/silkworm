package com.jengine.common.j2se.concurrent.thread;

import junit.framework.Assert;
import org.junit.Test;

/**
 * 1. Thread.setDaemon(true)设置为守护线程，作用是为其他线程的运行提供便利服务。
 * 2. 不能把正在运行的常规线程设置为守护线程。setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。
 * 3. 在Daemon线程中产生的新线程也是Daemon的
 * 4. 主线程结束后用户线程还会继续运行，JVM存活。
 * 5. 如果没有用户线程，都是守护线程，那么JVM结束
 *
 * @author bl07637
 * @date 5/9/2017
 * @since 0.1.0
 */
public class DaemonDemo {

    @Test
    public void daemonTest() throws InterruptedException {
        Thread daemon = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName());
                    }
                }, "t1");
                thread.start();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 3. 在Daemon线程中产生的新线程也是Daemon的
                Assert.assertTrue(thread.isDaemon());
            }
        }, "daemon");
        // 1. Thread.setDaemon(true)设置为守护线程，作用是为其他线程的运行提供便利服务。
        // 2. 不能把正在运行的常规线程设置为守护线程。setDaemon(true)必须在thread.start()之前设置，
        // 否则会跑出一个IllegalThreadStateException异常。
        daemon.setDaemon(true);
        daemon.start();
        Thread.sleep(100);
        Assert.assertTrue(daemon.isDaemon());
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("1");
                }
            }
        });
        // 5. 如果没有用户线程，都是守护线程，那么JVM结束
        thread.setDaemon(true);
        // 4. 主线程结束后用户线程还会继续运行，JVM存活。
        // 如果把thread.setDaemon(true);去掉，让thread变为用户线程，main将无法结束
        thread.start();
    }
}
