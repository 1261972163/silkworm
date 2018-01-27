/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.transport.cluster.api.transport;


import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * transport.
 *
 * @author nouuid
 */
public interface Transport {

  public void start() throws Exception;

  public void close() throws Exception;

  public void send(Message message) throws Exception;

  public void send(List<Message> messages) throws Exception;

  public static enum Type {
    FLUME,
    ELASTICSEARCH,
    LOG;

    private static Map<String, Type> namesMap = new HashMap<String, Type>(9);

    static {
      namesMap.put("flume", FLUME);
      namesMap.put("elasticsearch", ELASTICSEARCH);
      namesMap.put("log", LOG);
    }

    @JsonCreator
    public static Type forValue(String value) {
      return namesMap.get(value.toLowerCase());
    }

    /**
     * enum string.
     *
     * @return string
     */
    @JsonValue
    public String toValue() {
      for (Map.Entry<String, Type> entry : namesMap.entrySet()) {
        if (entry.getValue() == this) {
          return entry.getKey();
        }
      }
      return null; // or fail
    }

  }
}
