package com.jengine.data.elasticsearch.mydataframe;

import com.best.bingo.common.utils.StringUtils;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nouuid
 * @date 10/09/2016
 * @description es field store
 */
public enum StoreType {
  TRUE,
  FALSE;

  private static Map<String, StoreType> namesMap = new HashMap<String, StoreType>(3);

  static {
    namesMap.put("true", TRUE);
    namesMap.put("false", FALSE);
  }

  @JsonCreator
  public static StoreType forValue(String value) {
    return namesMap.get(StringUtils.lowerCase(value));
  }

  @JsonValue
  public String toValue() {
    for (Map.Entry<String, StoreType> entry : namesMap.entrySet()) {
      if (entry.getValue() == this)
        return entry.getKey();
    }

    return null; // or fail
  }
}
