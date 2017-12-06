package com.jengine.data.elasticsearch.mydataframe;

import com.jengine.data.elasticsearch.tools.StringUtils;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nouuid
 * @date 8/8/2016
 * @description es field types
 */
public enum DataType {
  TEXT, //String: foo bar
  BYTE, //
  SHORT, //
  INTEGER, //
  LONG, //Whole number: 123
  FLOAT, //
  DOUBLE, //Floating point: 123.45
  BOOLEAN, //Boolean: true or false
  DATE; //String, valid date: 2014-09-15

  private static Map<String, DataType> namesMap = new HashMap<String, DataType>(9);

  static {
    namesMap.put("text", TEXT);
    namesMap.put("byte", BYTE);
    namesMap.put("short", SHORT);
    namesMap.put("integer", INTEGER);
    namesMap.put("long", LONG);
    namesMap.put("float", FLOAT);
    namesMap.put("double", DOUBLE);
    namesMap.put("boolean", BOOLEAN);
    namesMap.put("date", DATE);
  }

  @JsonCreator
  public static DataType forValue(String value) {
    return namesMap.get(StringUtils.lowerCase(value));
  }

  @JsonValue
  public String toValue() {
    for (Map.Entry<String, DataType> entry : namesMap.entrySet()) {
      if (entry.getValue() == this)
        return entry.getKey();
    }

    return null; // or fail
  }
}
