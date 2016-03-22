package com.jengine.java.util.concurrent.synchronoustools;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 
 * @Title: SemaphoreTest.java 
 * @Package com.nouuid.j2se.concurrent.synchronoustools 
 * @Description: 使用Semaphore将容器变成有界阻塞容器
 * @author nouuid nouuid.163.com  
 * @date 2015年7月24日 下午4:37:07
 */
public class SemaphoreTest {
	
	class BoundedHashSet<T> {
		private final Set<T> set;
		private final Semaphore sem;
		
		public BoundedHashSet(int bound) {
			set = Collections.synchronizedSet(new HashSet<T>());
			sem = new Semaphore(bound);
		}
		
		public boolean add(T o) throws InterruptedException {
			sem.acquire();
			boolean isAdded = false;
			try {
				isAdded = set.add(o);
				return isAdded;
			} finally {
				if(!isAdded) {
					sem.release();
				}
			}
		}
		
		public boolean remove() {
			if(set!=null && set.size()>0) { //存在线程安全问题
				for(T e : set) {
					return remove(e);
				}
			}
			return true;
		}
		
		public boolean remove(T o) {
			
			boolean isRemoved = false;
			try {
				isRemoved = set.remove(o);
				return isRemoved;
			} finally {
				if(isRemoved) {
					sem.release();
				}
			}
		}
	}
	
	class Info {
		
	}
	
	class Consumer implements Runnable {
		private BoundedHashSet<Info> boundedHashSet;
		
		public Consumer(BoundedHashSet<Info> boundedHashSet) {
			this.boundedHashSet = boundedHashSet;
		}
		
		@Override
		public void run() {
			while(true) {
				boundedHashSet.remove();
			}
		}
	}

	public void doing() {
		BoundedHashSet<Info> boundedHashSet = new BoundedHashSet<Info>(10);
		
		new Thread(new Consumer(boundedHashSet)).start();
		
		for(int i=0; i<100; i++) {
			try {
				boundedHashSet.add(new Info());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("i=" + i);
		}
		
	}
	
	public static void main(String[] args) {
		SemaphoreTest t = new SemaphoreTest();
		t.doing();
	}
	
}
