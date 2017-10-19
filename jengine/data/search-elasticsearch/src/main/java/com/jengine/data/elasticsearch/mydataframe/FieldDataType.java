package com.jengine.data.elasticsearch.mydataframe;

import com.best.bingo.common.utils.StringUtils;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bl07637
 * @date 10/09/2016
 * @description es fielddata
 */
public enum FieldDataType {
  TRUE,
  FALSE;

  private static Map<String, FieldDataType> namesMap = new HashMap<String, FieldDataType>(3);

  static {
    namesMap.put("true", TRUE);
    namesMap.put("false", FALSE);
  }

  @JsonCreator
  public static FieldDataType forValue(String value) {
    return namesMap.get(StringUtils.lowerCase(value));
  }

  @JsonValue
  public String toValue() {
    for (Map.Entry<String, FieldDataType> entry : namesMap.entrySet()) {
      if (entry.getValue() == this) {
        return entry.getKey();
      }
    }

    return null; // or fail
  }
}
