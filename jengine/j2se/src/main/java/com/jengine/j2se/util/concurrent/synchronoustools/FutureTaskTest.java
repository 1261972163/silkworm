package com.jengine.j2se.util.concurrent.synchronoustools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask作闭锁，用于数据预加载
 * @author nouuid
 *
 */
public class FutureTaskTest {
	
	class Info {
		
	}
	
	private final FutureTask<Info> future = new FutureTask<Info>(new Callable<Info>() {
		public Info call() throws Exception {
			return loadInfo();
		}
	});
	
	private final Thread thread = new Thread(future);
	
	public Info loadInfo() {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + 5000;
		while((startTime=System.currentTimeMillis())<endTime) {
			
		}
		return new Info();
	}
	
	public void doing() {
		thread.start();
		System.out.println(System.currentTimeMillis());
		try {
			future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			System.out.println(System.currentTimeMillis());
		}
	}
	
	public static void main(String[] args) {
		FutureTaskTest t = new FutureTaskTest();
		t.doing();
	}
}
