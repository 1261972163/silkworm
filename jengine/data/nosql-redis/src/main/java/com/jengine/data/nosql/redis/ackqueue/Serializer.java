/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.data.nosql.redis.ackqueue;

import java.io.IOException;

/**
 * @author bl07637
 */
public interface Serializer<T> {
  byte[] toBytes(T string) throws Exception;
  T toObject(byte[] bytes) throws Exception;
}