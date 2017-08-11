package com.jengine.transport.serialize;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public interface SerializeStrategy {
    byte[] toByte(Object string);
    Object toObject(byte[] bytes);
}