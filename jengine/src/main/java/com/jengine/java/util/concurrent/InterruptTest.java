package com.jengine.java.util.concurrent;

import java.io.IOException;

/**
 * 单独调用interrupt方法可以使得处于阻塞状态的线程抛出一个异常，也就说，它可以用来中断一个正处于阻塞状态的线程；
 * 另外，通过interrupt方法和isInterrupted()方法来停止正在运行的线程。
 * @author bl07637
 *
 */
public class InterruptTest {
	public static void main(String[] args) throws IOException  {
		InterruptTest test = new InterruptTest();
//        MyThread thread = test.new MyThread();
        MyThread2 thread = test.new MyThread2();
        thread.start();
        try {
        	System.out.println(Thread.currentThread().getName() + "进入睡眠状态");
//            Thread.currentThread().sleep(1000*2);
            Thread.currentThread().sleep(10);
            System.out.println(Thread.currentThread().getName() + "睡眠完毕");
        } catch (InterruptedException e) {
        	System.out.println(Thread.currentThread().getName() + "中断异常");
        }
        thread.interrupt();
	
    } 
     
    class MyThread extends Thread{
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "进入睡眠状态");
                Thread.currentThread().sleep(1000*10);
                System.out.println(Thread.currentThread().getName() + "睡眠完毕");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "中断异常");
            }
            System.out.println(Thread.currentThread().getName() + "的run方法执行完毕");
        }
    }

    class MyThread2 extends Thread{
    	@Override
    	public void run() {
    		int i = 0;
            while(!isInterrupted() && i<Integer.MAX_VALUE){
                System.out.println(i+" while循环");
                i++;
            }
    	}
    }
}
