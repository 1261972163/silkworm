/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.data.nosql.redis.ackqueue;

/**
 * @author bl07637
 */
public class RedisAckQueueDemo {

  public static void main(String[] args) {
    String queuename = "ackqueue";
    String host = "10.45.11.84";
    int port = 6379;
    String pwd = null;
    int timeout = 5000;
    RedisAckQueue<String> redisAckQueue = new RedisAckQueue<String>(queuename, host, port , pwd, timeout);
    for (int i= 1; i<=10; i++) {
      redisAckQueue.put(i+"");
    }
    System.out.println("end.");
  }
}
