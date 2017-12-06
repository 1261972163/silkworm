package com.jengine.data.nosql.redis.ackqueue;

/**
 * @author nouuid
 */
public interface Serializer<T> {
  byte[] toBytes(T string) throws Exception;
  T toObject(byte[] bytes) throws Exception;
}