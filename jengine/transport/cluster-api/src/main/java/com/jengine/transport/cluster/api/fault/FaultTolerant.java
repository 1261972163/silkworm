/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.transport.cluster.api.fault;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nouuid
 */
public interface FaultTolerant {

  void invoke(InvokeInfo invokeInfo) throws Exception;

  public static enum Type {
    FAIL_FAST,
    FAIL_OVER;

    private static Map<String, Type> namesMap = new HashMap<String, Type>(9);

    static {
      namesMap.put("failfast", FAIL_FAST);
      namesMap.put("failover", FAIL_OVER);
    }

    @JsonCreator
    public static FaultTolerant.Type forValue(String value) {
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
