package com.jengine.java.util.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 简述synchronized和java.util.concurrent.locks.Lock的异同 ？
 * 主要相同点：Lock能完成synchronized所实现的所有功能 主要不同点：Lock有比synchronized更精确的线程语义和更好的性能。
 * synchronized会自动释放锁，而Lock一定要求程序员手工释放，并且必须在finally从句中释放。
 * Lock还有更强大的功能，例如，它的tryLock方法可以非阻塞方式去拿锁。
 * 
 * @author bl07637
 *
 */

public class LockTest {
	
	public static void main(String[] args) {
		LockTestOuter tt = new LockTestOuter();
		for (int i = 0; i < 2; i++) {
			new Thread(tt.new Adder()).start();
			new Thread(tt.new Subtractor()).start();
		}
	}

//	@Test
//	public void test() {
//		Outer tt = new Outer();
//		for (int i = 0; i < 2; i++) {
//			new Thread(tt.new Adder()).start();
//			new Thread(tt.new Subtractor()).start();
//		}
//	}
}

class LockTestOuter {
	private int j;
	private Lock lock = new ReentrantLock();

	class Subtractor implements Runnable {
		@Override
		public void run() {
			while (true) {
//				synchronized (Outer.this) {
//					try {
//						throw new Exception();//这里抛异常了，锁能释放吗？
//					} catch (Exception e) {
//					}
//				}
				
//				lock.lock();
//				try {
//					throw new Exception();//这里抛异常了，锁能释放吗？
//				} catch (Exception e) {
//				} finally {
//				}
				
				lock.lock();
				try {
					System.out.println(Thread.currentThread().getName() + " do j--, j=" + j--);
//					Thread.sleep(5*1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}

	}

	class Adder implements Runnable {

		@Override
		public void run() {
			while (true) {
				/*
				 * synchronized (ThreadTest.this) { System.out.println("j++=" +
				 * j++); }
				 */
				lock.lock();
				try {
					System.out.println(Thread.currentThread().getName() + " do j++, j=" + j++);
//					Thread.sleep(5*1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}

	}

}
