package com.jengine.data.nosql.redis;


import com.jengine.transport.serialize.SerializeStrategy;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public class RedisFileService {

  RedisService redisService = new RedisService();
  SerializeStrategy serializeStrategy = null;

  public RedisFileService(RedisService redisService, SerializeStrategy serializeStrategy) {
    this.redisService = redisService;
    this.serializeStrategy = serializeStrategy;
  }

  public void save(String uuid, Object value) {
    redisService.set(serializeStrategy.toByte(uuid), serializeStrategy.toByte(value));
  }

  public void remove(String uuid) {
    redisService.del(serializeStrategy.toByte(uuid));
  }

  public Object get(String uuid) {
    byte[] bytes = redisService.get(serializeStrategy.toByte(uuid));
    return serializeStrategy.toObject(bytes);
  }


}
