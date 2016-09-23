package com.jengine.store.redis;


import com.jengine.store.file.FileService;
import com.jengine.serializer.SerializeStrategy;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public class RedisFileService implements FileService {

    RedisService      redisService      = new RedisService();
    SerializeStrategy serializeStrategy = null;

    public RedisFileService(RedisService redisService, SerializeStrategy serializeStrategy) {
        this.redisService = redisService;
        this.serializeStrategy = serializeStrategy;
    }

    @Override
    public void save(String uuid, Object value) {
        redisService.set(serializeStrategy.toByte(uuid), serializeStrategy.toByte(value));
    }

    @Override
    public void remove(String uuid) {
        redisService.del(serializeStrategy.toByte(uuid));
    }

    @Override
    public Object get(String uuid) {
        byte[] bytes = redisService.get(serializeStrategy.toByte(uuid));
        return serializeStrategy.toObject(bytes);
    }


}
