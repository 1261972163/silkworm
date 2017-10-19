/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.data.nosql.redis.ackqueue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * @author bl07637
 */
public class SimpleSerializer<T> implements Serializer<T>  {
  public byte[] toBytes(T object) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutput out = null;
    byte[] bytes = null;
    try {
      out = new ObjectOutputStream(bos);
      out.writeObject(object);
      bytes = bos.toByteArray();
    } finally {
      if (out != null) {
        out.close();
      }
      bos.close();
    }
    return bytes;
  }

  public T toObject(byte[] bytes) throws Exception {
    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    ObjectInput in = null;
    Object object = null;
    try {
      in = new ObjectInputStream(bis);
      object = in.readObject();
    } finally {
      bis.close();
      if (in != null) {
        in.close();
      }
    }
    return (T)object;
  }
}
