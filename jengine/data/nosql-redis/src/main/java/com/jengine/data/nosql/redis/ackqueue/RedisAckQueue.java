package com.jengine.data.nosql.redis.ackqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 使用redis实现阻塞queue.
 * 需要借助redis的一个配置，设置过期不删除策略：maxmemory-policy noeviction
 * @author nouuid
 */
public class RedisAckQueue<T> {

  private static final Logger logger = LoggerFactory.getLogger(RedisAckQueue.class);

  public JedisPool redisPool;
  private String queuename;
  private Serializer<T> serializer;


  public RedisAckQueue(String queuename, JedisPool redisPool, Serializer<T> serializer) {
    this.queuename = queuename;
    this.redisPool = redisPool;
    this.serializer = serializer;
  }

  public String getQueuename() {
    return queuename;
  }

  public void destory() {
    try {
      redisPool.destroy();
    } catch (Exception e) {
      logger.error("Could not destory RedisAckQueue.", e);
    }
    logger.info("RedisAckQueue has been shut down!");
  }

  public boolean put(T value) {
    boolean ok = false;
    try {
      int retry = 0;
      Jedis jedis = null;
      while (true) {
        boolean hasError = false;
        try {
          jedis = getJedis();
          jedis.rpush(queuename.getBytes(), serializer.toBytes(value));
          ok = true;
          break;
        } catch (Exception e) {
          if (e instanceof InterruptedException) {
            throw (InterruptedException) e;
          }
          hasError = true;
          retry++;
          retry = (retry <= 3) ? retry : 0;
          logger.error("Could not put value to RedisAckQueue. " + retry * 1000 + "s later retry.", e);
          Thread.sleep(1000 * retry);
        } finally {
          if (jedis != null) {
            if (hasError) {
              jedis.close();
            } else {
              jedisRelease(jedis);
            }
          }
        }
      }
    } catch (InterruptedException sleepInteruptedExp) {
      logger.info("put sleep is interrupted.");
    }
    return ok;
  }

  public T get() throws InterruptedException {
    T nextValue = null;
    Jedis jedis = null;
    int retry = 0;
    List<byte[]> values = null;
    while (true) {
      boolean hasError = false;
      try {
        jedis = getJedis();
        values = jedis.lrange(queuename.getBytes(), 0, 0);
        if (values != null && !values.isEmpty()) {
          nextValue = serializer.toObject(values.get(0));
          break;
        }
      } catch (Exception e) {
        if (e instanceof InterruptedException) {
          throw (InterruptedException) e;
        }
        hasError = true;
        retry++;
        retry = (retry <= 3) ? retry : 0;
        logger.error("Could not get next value from RedisAckQueue. " + retry * 1000 + "s later retry.", e);
        Thread.sleep(1000 * retry);
      } finally {
        if (jedis != null) {
          if (hasError) {
            jedis.close();
          } else {
            jedisRelease(jedis);
          }
        }
      }
    }
    return nextValue;
  }

  public boolean ack() throws InterruptedException {
    boolean ok = false;
    Jedis jedis = null;
    byte[] value = null;
    int retry = 0;
    while (true) {
      boolean hasError = false;
      try {
        jedis = getJedis();
        value = jedis.lpop(queuename.getBytes());
        ok = (value == null) ? ok : true;
        break;
      } catch (Exception e) {
        if (e instanceof InterruptedException) {
          throw (InterruptedException) e;
        }
        hasError = true;
        retry++;
        retry = (retry <= 3) ? retry : 0;
        logger.error("Could not ack RedisAckQueue. " + retry * 1000 + "s later retry.", e);
        Thread.sleep(1000 * retry);
      } finally {
        if (jedis != null) {
          if (hasError) {
            jedis.close();
          } else {
            jedisRelease(jedis);
          }
        }
      }
    }
    return ok;
  }

  private Jedis getJedis() throws InterruptedException {
    int retry = 0;
    Jedis jedis = null;
    while (true) {
      boolean hasError = false;
      try {
        jedis = redisPool.getResource();
        break;
      } catch (Exception e) {
        hasError = true;
        retry++;
        retry = (retry <= 3) ? retry : 0;
        logger.error("Could not get redis resource. " + retry * 1000 + "s later retry.", e);
        Thread.sleep(1000 * retry);
      } finally {
        if (jedis != null) {
          if (hasError) {
            jedis.close();
          }
        }
      }
    }
    return jedis;
  }

  private void jedisRelease(Jedis jedis) {
    if (jedis != null) {
      redisPool.returnResourceObject(jedis);
    }
  }

}
