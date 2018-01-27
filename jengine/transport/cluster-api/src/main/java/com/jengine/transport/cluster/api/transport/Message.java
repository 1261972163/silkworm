/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.transport.cluster.api.transport;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * message.
 *
 * @author nouuid
 */
public interface Message {

  String MSG_TYPE_METRIC = "METRIC";
  String MSG_TYPE_HEARTHEAT = "HEARTBEAT";
  String MSG_TYPE_EVENT = "EVENT";
  String MSG_TYPE_TRACE = "TRACE";

  public static enum Type {
    METRIC,
    HEARTBEAT,
    EVENT,
    TRACE;

    private static Map<String, Type> namesMap = new HashMap<String, Type>(9);

    static {
      namesMap.put("metric", METRIC);
      namesMap.put("heartbeat", HEARTBEAT);
      namesMap.put("event", EVENT);
      namesMap.put("trace", TRACE);
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

  Header getHeader();

  String getBody();
}