package com.jengine.java.lang;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ����synchronized��java.util.concurrent.locks.Lock����ͬ ��
 * ��Ҫ��ͬ�㣺Lock�����synchronized��ʵ�ֵ����й��� ��Ҫ��ͬ�㣺Lock�б�synchronized����ȷ���߳�����͸��õ����ܡ�
 * synchronized���Զ��ͷ�������Lockһ��Ҫ�����Ա�ֹ��ͷţ����ұ�����finally�Ӿ����ͷš�
 * Lock���и�ǿ��Ĺ��ܣ����磬����tryLock�������Է�������ʽȥ������
 * 
 * @author bl07637
 *
 */

public class ThreadTest2 {
	
	public static void main(String[] args) {
		Outer2 tt = new Outer2();
		for (int i = 0; i < 2; i++) {
			new Thread(tt.new Adder()).start();
			new Thread(tt.new Subtractor()).start();
		}
	}

//	@Test
//	public void test() {
//		Outer2 tt = new Outer2();
//		for (int i = 0; i < 2; i++) {
//			new Thread(tt.new Adder()).start();
//			new Thread(tt.new Subtractor()).start();
//		}
//	}
}

class Outer2 {
	private int j;
	private Lock lock = new ReentrantLock();

	class Subtractor implements Runnable {
		@Override
		public void run() {
			while (true) {
//				synchronized (Outer2.this) {
//					try {
//						throw new Exception();//�������쳣�ˣ������ͷ���
//					} catch (Exception e) {
//					}
//				}
				
//				lock.lock();
//				try {
//					throw new Exception();//�������쳣�ˣ������ͷ���
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
