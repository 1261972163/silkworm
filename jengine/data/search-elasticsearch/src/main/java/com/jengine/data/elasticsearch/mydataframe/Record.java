package com.jengine.data.elasticsearch.mydataframe;

import java.util.Map;

/**
 * @author nouuid
 * @date 8/9/2016
 * @description
 */
public class Record {
  private Map<String, String> fieldValues; // <field name, field value>

  public Map<String, String> getFieldValues() {
    return fieldValues;
  }

  public void setFieldValues(Map<String, String> fieldValues) {
    this.fieldValues = fieldValues;
  }
}
