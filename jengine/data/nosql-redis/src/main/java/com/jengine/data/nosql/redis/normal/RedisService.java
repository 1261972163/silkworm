package com.jengine.data.nosql.redis.normal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public class RedisService {

  private static final Log logger = LogFactory.getLog(RedisService.class);
  public static JedisPool redisPool;

  @Before
  public void before() {
    RedisConfig redisConfig = new RedisConfig();
    redisConfig.setHost("127.0.0.1");
    redisConfig.setPort(6379);
    redisConfig.setPwd("");
    redisConfig.setTimeout(5000);
    initRedisPool(redisConfig);
  }

  private void initRedisPool(RedisConfig redisConfig) {
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(100);
    config.setMaxIdle(100);
    config.setMaxWaitMillis(1000);
    if (redisConfig.getPwd() == null || redisConfig.getPwd().isEmpty()) {
      this.redisPool = new JedisPool(config, redisConfig.getHost(), redisConfig.getPort());
    } else {
      this.redisPool = new JedisPool(config, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout(), redisConfig.getPwd());
    }
  }

  /**
   * 事务操作
   */
  @Test
  public void transaction() throws Exception {
    Transaction tx = null;
    Jedis jedis = null;
    boolean hasError = true;
    while (true) {
      try {
        jedis = redisPool.getResource();
        tx = jedis.multi();
        tx.rpush("list", "item");
        List<Object> results = tx.exec();
        if (results == null || results.isEmpty()) {
          throw new Exception("jedis multi exec exception");
        }
        hasError = false;
      } catch (JedisConnectionException jce) {
        logger.error("", jce);
        if (jedis != null) {
          // 连接错误需要丢弃这个有问题的jedis实例
          jedis.close();
        }
      } catch (Exception e){
        // 出问题时，transaction有自动丢弃事务内所有操作的特性，不需要手动回滚或discard，
        // 直接忽略错误，进入重试
        logger.error("", e);
      } finally {
        if (jedis != null && !hasError) {
          // 正常操作，需要回收jedis资源
          redisPool.returnResourceObject(jedis);
        }
      }
    }
  }

  @After
  public void stop() {
    try {
      redisPool.destroy();
    } catch (Exception e) {
      logger.error("set value to redis. Error!", e);
    }
    logger.info(" redis has been shut down!");
  }

  public void set(byte[] key, byte[] value) {
    Jedis jedis = redisPool.getResource();
    try {
      jedis.set(key, value);
    } catch (Exception e) {
      logger.error("set value to redis. Error!", e);
    } finally {
      redisPool.returnResource(jedis);
    }
  }

  public byte[] get(byte[] key) {
    byte[] res = null;
    Jedis jedis = redisPool.getResource();
    try {
      res = jedis.get(key);
    } catch (Exception e) {
      logger.error("get value from redis. Error!", e);
    } finally {
      redisPool.returnResourceObject(jedis);
    }
    return res;
  }

  public void del(byte[] key) {
    Jedis jedis = redisPool.getResource();
    try {
      jedis.del(key);
    } catch (Exception e) {
      logger.error("delete value from redis. Error!", e);
    } finally {
      redisPool.returnResourceObject(jedis);
    }
  }

}
