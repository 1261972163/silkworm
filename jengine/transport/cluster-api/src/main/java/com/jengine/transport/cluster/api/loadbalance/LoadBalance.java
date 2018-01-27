/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.transport.cluster.api.loadbalance;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nouuid
 */
public interface LoadBalance<T> {

  T select(List<T> nodes);

  public static enum Type {
    RANDOM,
    ROUND_ROBIN;

    private static Map<String, Type> namesMap = new HashMap<String, Type>(9);

    static {
      namesMap.put("random", RANDOM);
      namesMap.put("round_robin", ROUND_ROBIN);
    }

    @JsonCreator
    public static LoadBalance.Type forValue(String value) {
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