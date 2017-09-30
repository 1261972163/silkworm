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

  public void destory() {
    try {
      redisPool.destroy();
    } catch (Exception e) {
      logger.error("Could not destory RedisAckQueue.", e);
    }
    logger.info("RedisAckQueue has been shut down!");
  }

  public boolean put(T value) throws InterruptedException {
    boolean ok = false;
    Jedis jedis = getJedis();
    try {
      int retry = 0;
      while (true) {
        try {
          jedis.rpush(queuename.getBytes(), SimpleSerializer.toBytes(value));
          ok = true;
          break;
        } catch (Exception e) {
          retry++;
          retry = (retry<=3) ? retry : 0;
          logger.error("Could not put value to RedisAckQueue. " + retry*1000 + "s later retry.", e);
          Thread.sleep(1000*retry);
        }
      }
    } finally {
      jedisRelease(jedis);
    }
    return ok;
  }

  public T get() throws InterruptedException {
    T nextValue = null;
    Jedis jedis = getJedis();
    int retry = 0;
    List<byte[]> values = null;
    try {
      while (true) {
        try {
          values = jedis.lrange(queuename.getBytes(), 0, 0);
          if (values!=null && !values.isEmpty()) {
            nextValue = (T)SimpleSerializer.toObject(values.get(0));
            break;
          }
        } catch (Exception e) {
          retry++;
          retry = (retry <= 3) ? retry : 0;
          logger.error("Could not get next value from RedisAckQueue. " + retry * 1000 + "s later retry.", e);
          Thread.sleep(1000 * retry);
        }
      }
    } finally {
      jedisRelease(jedis);
    }

    return nextValue;
  }

  public boolean ack() throws InterruptedException {
    boolean ok = false;
    Jedis jedis = getJedis();
    byte[] value = null;
    try {
      int retry = 0;
      while (true) {
        try {
          value = jedis.lpop(queuename.getBytes());
          ok = (value==null) ? ok : true;
          break;
        } catch (Exception e) {
          retry++;
          retry = (retry <= 3) ? retry : 0;
          logger.error("Could not ack RedisAckQueue. " + retry * 1000 + "s later retry.", e);
          Thread.sleep(1000 * retry);
        }
      }
    } finally {
      jedisRelease(jedis);
    }
    return ok;
  }

  private Jedis getJedis() throws InterruptedException {
    int retry = 0;
    Jedis jedis = null;
    while (true) {
      try {
        jedis = redisPool.getResource();
        break;
      } catch (Exception e) {
        retry++;
        retry = (retry <= 3) ? retry : 0;
        logger.error("Could not get redis resource. " + retry * 1000 + "s later retry.", e);
        Thread.sleep(1000 * retry);
      }
    }
    return jedis;
  }

  private void jedisRelease(Jedis jedis) {
    if (jedis!=null) {
      redisPool.returnResourceObject(jedis);
    }
  }

}
