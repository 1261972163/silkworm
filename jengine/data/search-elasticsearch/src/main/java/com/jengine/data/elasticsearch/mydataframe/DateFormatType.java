package com.jengine.data.elasticsearch.mydataframe;

import com.best.bingo.common.utils.StringUtils;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bl07637
 * @date 10/09/2016
 * @description es field store
 */
public enum DateFormatType {
  DATE;

  private static Map<String, DateFormatType> namesMap = new HashMap<String, DateFormatType>(1);

  static {
    namesMap.put("yyyy-MM-dd HH:mm:ss||yyyy-MM-dd HH:mm:ss.SSS||epoch_millis", DATE);
  }

  @JsonCreator
  public static DateFormatType forValue(String value) {
    return namesMap.get(StringUtils.lowerCase(value));
  }

  @JsonValue
  public String toValue() {
    for (Map.Entry<String, DateFormatType> entry : namesMap.entrySet()) {
      if (entry.getValue() == this)
        return entry.getKey();
    }

    return null; // or fail
  }
}
