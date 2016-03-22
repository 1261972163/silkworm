package com.jengine.java.util.concurrent;

import java.io.IOException;

/**
 * 在main线程中，调用thread.join方法，则main方法会等待thread线程执行完毕或者等待一定的时间。
 * 如果调用的是无参join方法，则等待thread执行完毕； 
 * 如果调用的是指定了时间参数的join方法，则等待一定的时间。
 * 
 * @author bl07637
 *
 */
public class JoinTest {

	public static void main(String[] args) throws IOException {
		System.out.println("进入线程" + Thread.currentThread().getName());
		JoinTest test = new JoinTest();
		MyThread thread1 = test.new MyThread();
		thread1.start();
		try {
			System.out.println("线程" + Thread.currentThread().getName() + "等待");
//			thread1.join();
			thread1.join(1000*3);
			System.out.println("线程" + Thread.currentThread().getName() + "继续执行");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			System.out.println("进入线程" + Thread.currentThread().getName());
			try {
				Thread.currentThread().sleep(1000*5);
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
			System.out
					.println("线程" + Thread.currentThread().getName() + "执行完毕");
		}
	}
}
