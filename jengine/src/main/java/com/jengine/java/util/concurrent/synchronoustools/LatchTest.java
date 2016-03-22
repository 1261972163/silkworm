package com.jengine.java.util.concurrent.synchronoustools;

import java.util.concurrent.CountDownLatch;

/**
 * 测试n个线程并发执行某个任务时需要的时间
 * @author nouuid
 *
 */
public class LatchTest {
	public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
		
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		
		for(int i=0; i<nThreads; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						startGate.await();
						try {
							task.run();
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		long startTime = System.nanoTime();
		startGate.countDown();
		endGate.await();
		long endTime = System.nanoTime();
		
		return endTime-startTime;
	}
	
	public static void main(String[] args) throws InterruptedException {
		LatchTest t = new LatchTest();
		long cost = t.timeTasks(10, new Runnable(){
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				
			}});
		System.out.println("costs=" + cost);
	}
}
