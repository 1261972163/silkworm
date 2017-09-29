/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.data.nosql.redis.ackqueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author nouuid
 */
public class RedisAckQueue<T> {

  private static final Log logger = LogFactory.getLog(RedisAckQueue.class);

  public static JedisPool redisPool;
  private Jedis jedis;
  private String queuename;


  public RedisAckQueue(String queuename, String host, int port, String pwd, int timeout) {
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(100);
    config.setMaxIdle(100);
    config.setMaxWaitMillis(1000);
    if (pwd == null || pwd.isEmpty()) {
      this.redisPool = new JedisPool(config, host, port);
    } else {
      this.redisPool = new JedisPool(config, host, port, timeout, pwd);
    }
    this.queuename = queuename;
  }

  public void stop() {
    try {
      redisPool.destroy();
    } catch (Exception e) {
      logger.error("set value to redis. Error!", e);
    }
    logger.info(" redis has been shut down!");
  }

  public void put(T value) {
    Jedis jedis = redisPool.getResource();
    try {
      jedis.rpush(queuename.getBytes(), SimpleSerializer.toBytes(value));
    } catch (Exception e) {
      logger.error("set value to redis. Error!", e);
    } finally {
      redisPool.returnResource(jedis);
    }
  }

  public T getNext() {
    Jedis jedis = redisPool.getResource();
    List<byte[]> values = null;
    try {
      values = jedis.lrange(queuename.getBytes(), 0, 0);
    } catch (Exception e) {
      logger.error("get value from redis. Error!", e);
    } finally {
      redisPool.returnResource(jedis);
    }
    return (T)SimpleSerializer.toObject(values.get(0));
  }

  public boolean ack() {
    boolean ok = false;
    Jedis jedis = redisPool.getResource();
    byte[] value = null;
    try {
      value = jedis.lpop(queuename.getBytes());
      ok = (value==null) ? ok : true;
    } catch (Exception e) {
      logger.error("ack redis. Error!", e);
    } finally {
      redisPool.returnResource(jedis);
    }
    return ok;
  }

}
