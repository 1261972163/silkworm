package com.jengine.store.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public class RedisService {
    private static final Log logger = LogFactory.getLog(RedisService.class);

    private RedisConfig redisConfig;
    public static JedisPool redisPool;

    private Jedis jedis;

    public RedisService() {
        redisConfig = new RedisConfig();
        init();
    }

    public RedisService(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
        init();
    }

    public void init() {
        String[] strings = redisConfig.getHost().split(":");
        String host = strings[0];
        int port = Integer.parseInt(strings[1]);

        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxActive(100);
        config.setMaxIdle(100);
//        config.setMaxWaitMillis(1000);
        if (redisConfig.getPwd() == null || redisConfig.getPwd().isEmpty()) {
            this.redisPool = new JedisPool(config, host, port);
        } else {
            this.redisPool = new JedisPool(config, host, port, redisConfig.getTimeout(), redisConfig.getPwd());
        }
    }

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
        } catch(Exception e) {
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
        } catch(Exception e) {
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
        } catch(Exception e) {
            logger.error("delete value from redis. Error!", e);
        } finally {
            redisPool.returnResourceObject(jedis);
        }
    }

}
