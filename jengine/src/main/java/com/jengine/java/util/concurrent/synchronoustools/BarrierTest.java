package com.jengine.java.util.concurrent.synchronoustools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * @Title: BarrierTest.java 
 * @Package com.nouuid.j2se.concurrent.synchronoustools 
 * @Description: 测试n个线程并发执行某个任务时需要的时间
 * @author nouuid nouuid.163.com  
 * @date 2015年7月24日 下午7:45:29
 */
public class BarrierTest {
	
	private final CyclicBarrier barrier;
	private static final int N_THREAD = 5; 
	
	private long startTime;
	private long endTime;
	
	public BarrierTest() {
		startTime = System.currentTimeMillis();
		barrier = new CyclicBarrier(N_THREAD, new Runnable() {
			@Override
			public void run() {
				endTime = System.currentTimeMillis();
				System.out.println("costs=" + (endTime-startTime));
			}
		});
	}
	
	class Task implements Runnable {
		
		private CyclicBarrier barrier;
		
		public Task(CyclicBarrier barrier) {
			this.barrier = barrier;
		}
		
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " is running");
			
			long start = System.currentTimeMillis();
			long end = start + 10*1000;
			while((start = System.currentTimeMillis())<end) {
				
			}
			
			try {
				System.out.println(Thread.currentThread().getName() + " is waiting");
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}

	public void doing() {
		for(int i=0; i<N_THREAD; i++) {
			new Thread(new Task(barrier)).start();
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		BarrierTest t = new BarrierTest();
		t.doing();
	}

}
