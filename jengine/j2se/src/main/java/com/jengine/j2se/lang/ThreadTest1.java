package com.jengine.j2se.lang;

import org.junit.Test;

/**
 * ���4���̣߳����������߳�ÿ�ζ�j����1�����������̶߳�jÿ�μ���1��д������ ���³���ʹ���ڲ���ʵ���̣߳���j������ʱ��û�п���˳�����⡣
 * @author nouuid
 *
 */
public class ThreadTest1 {

	@Test
	public void test() {
		Outer tt = new Outer();
		Outer.Inc inc = tt.new Inc();
		Outer.Dec dec = tt.new Dec();
		for (int i = 0; i < 2; i++) {
			Thread t = new Thread(inc);
			t.start();
			t = new Thread(dec);
			t.start();
		}
	}
}

class Outer {
	private int j;

//	public static void main(String args[]) {
//		ThreadTest1 tt = new ThreadTest1();
//		Inc inc = tt.new Inc();
//		Dec dec = tt.new Dec();
//		for (int i = 0; i < 2; i++) {
//			Thread t = new Thread(inc);
//			t.start();
//			t = new Thread(dec);
//			t.start();
//		}
//	}

	private synchronized void inc() {
		j++;
		System.out.println(Thread.currentThread().getName() + "-inc:" + j);
	}

	private synchronized void dec() {
		j--;
		System.out.println(Thread.currentThread().getName() + "-dec:" + j);
	}

	class Inc implements Runnable {
		public void run() {
			for (int i = 0; i < 100; i++) {
				inc();
			}
		}
	}

	class Dec implements Runnable {
		public void run() {
			for (int i = 0; i < 100; i++) {
				dec();
			}
		}
	}
}
