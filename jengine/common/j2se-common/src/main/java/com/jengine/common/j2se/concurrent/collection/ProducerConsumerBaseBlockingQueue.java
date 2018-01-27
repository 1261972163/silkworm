package com.jengine.common.j2se.concurrent.collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 基于LinkedBlockingQueue实现的 生产者-消费者
 * @author nouuid
 *
 */
public class ProducerConsumerBaseBlockingQueue {

	public static final int QUEUE_BOUND = 10;
	public static final int N_PRODUCER = 10;
	public static final int N_CONSUMER = 10;
	
	class Model {
		private long id;
		private String owner;
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getOwner() {
			return owner;
		}
		public void setOwner(String owner) {
			this.owner = owner;
		}
	}
	
	class Producer implements Runnable {
		
		private BlockingQueue<Model> queue;
		
		public Producer(BlockingQueue<Model> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while(true) {
				Model m = new Model();
				m.setId(System.currentTimeMillis());
				m.setOwner(Thread.currentThread().getName());
				try {
					queue.put(m);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class Consumer implements Runnable{
		private BlockingQueue<Model> queue;
		
		public Consumer(BlockingQueue<Model> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			Model m = null;
			while(true) {
				try {
					m = queue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " consumed model_" + m.getId() + " owned by " + m.getOwner());
			}
		}
	}
	
	public void doing() {
		BlockingQueue<Model> queue = new LinkedBlockingQueue<Model>();
		
		for(int i=0; i<N_CONSUMER; i++) {
			new Thread(this.new Consumer(queue)).start();
		}
		
		for(int i=0; i<N_PRODUCER; i++) {
			new Thread(this.new Producer(queue)).start();
		}
	}
	
	public static void main(String[] args) {
		ProducerConsumerBaseBlockingQueue t = new ProducerConsumerBaseBlockingQueue();
		t.doing();
	}
	
}
