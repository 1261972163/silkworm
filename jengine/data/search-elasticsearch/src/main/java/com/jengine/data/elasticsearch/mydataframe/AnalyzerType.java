package com.jengine.data.elasticsearch.mydataframe;

import com.best.bingo.common.utils.StringUtils;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nouuid
 * @date 8/4/2016
 * @description es analyzer types
 */
public enum AnalyzerType {
  KEYWORD, //keyword tokenizer of es
  IK, //IK tokenizer
  PINYIN; //PINYIN tokenizer

  private static Map<String, AnalyzerType> namesMap = new HashMap<String, AnalyzerType>(3);

  static {
    namesMap.put("keyword", KEYWORD);
    namesMap.put("ik", IK);
    namesMap.put("pinyin", PINYIN);
  }

  @JsonCreator
  public static AnalyzerType forValue(String value) {
    return namesMap.get(StringUtils.lowerCase(value));
  }

  @JsonValue
  public String toValue() {
    for (Map.Entry<String, AnalyzerType> entry : namesMap.entrySet()) {
      if (entry.getValue() == this)
        return entry.getKey();
    }

    return null; // or fail
  }
}
