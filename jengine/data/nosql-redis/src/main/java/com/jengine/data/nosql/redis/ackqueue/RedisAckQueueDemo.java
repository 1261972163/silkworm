package com.jengine.data.nosql.redis.ackqueue;

import com.jengine.data.nosql.redis.normal.RedisConfig;
import org.junit.Test;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.CountDownLatch;

/**
 * @author nouuid
 */
public class RedisAckQueueDemo {

  @Test
  public void test() throws InterruptedException {
    String queuename = "ackqueue";
    String host = "10.45.11.84";
    int port = 6379;
    String pwd = null;
    int timeout = 5000;
    JedisPool jedisPool = initRedisPool(host, port, pwd, timeout);
    Serializer<String> serializer = new SimpleSerializer<String>();
    RedisAckQueue<String> redisAckQueue = new RedisAckQueue<String>(queuename, jedisPool, serializer);
    produce(redisAckQueue);
    consume(redisAckQueue);
    System.out.println("end.");
  }

  private JedisPool initRedisPool(String host, int port , String pwd, int timeout) {
    JedisPool jedisPool = null;
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(100);
    config.setMaxIdle(100);
    config.setMaxWaitMillis(1000);
    if (pwd == null || pwd.isEmpty()) {
      jedisPool = new JedisPool(config, host, port);
    } else {
      jedisPool = new JedisPool(config, host, port, timeout, pwd);
    }
    return jedisPool;
  }

  @Test
  public void test2() throws InterruptedException {
    String queuename = "ackqueue";
    String host = "10.45.11.84";
    int port = 6379;
    String pwd = null;
    int timeout = 5000;
    JedisPool jedisPool = initRedisPool(host, port, pwd, timeout);
    Serializer<String> serializer = new SimpleSerializer<String>();
    final RedisAckQueue<String> redisAckQueue = new RedisAckQueue<String>(queuename, jedisPool, serializer);
    CountDownLatch countDownLatch = new CountDownLatch(60);
    Thread consumer = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          String value = null;
          while ((value = redisAckQueue.get())!=null) {
            System.out.println("           c-" + value);
            Thread.sleep(1000);
            redisAckQueue.ack();
            countDownLatch.countDown();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    consumer.start();


    Thread producer = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(5000);
          for (int i= 1; i<=60; i++) {
            System.out.println("p-" + i);
            redisAckQueue.put(i+"");
            Thread.sleep(1000);
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    producer.start();
    countDownLatch.await();

    System.out.println("end.");
  }

  private void produce(RedisAckQueue<String> redisAckQueue) throws InterruptedException {
    for (int i= 1; i<=10; i++) {
      System.out.println(i);
      redisAckQueue.put(i+"");
      Thread.sleep(1000);
    }
  }

  private void consume(RedisAckQueue<String> redisAckQueue) throws InterruptedException {
    String value = null;
    int count = 1;
    while (count<=10 && (value = redisAckQueue.get())!=null) {
      System.out.println(value);
      Thread.sleep(1000);
      redisAckQueue.ack();
      count++;
    }
  }

}
