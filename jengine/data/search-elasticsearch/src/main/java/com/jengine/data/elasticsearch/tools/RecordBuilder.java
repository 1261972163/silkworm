package com.jengine.data.elasticsearch.tools;


import com.jengine.data.elasticsearch.mydataframe.Record;

import java.util.Map;

/**
 * @author bl07637
 * @date 8/9/2016
 * @description
 */
public class RecordBuilder {
  private Map<String, String> fieldValues; // <field name, field value>

  public RecordBuilder(Map<String, String> fieldValues) {
    this.fieldValues = fieldValues;
  }

  public Record build() {
    Record record = new Record();
    record.setFieldValues(fieldValues);
    return record;
  }

  public Map<String, String> getFieldValues() {
    return fieldValues;
  }

  public void setFieldValues(Map<String, String> fieldValues) {
    this.fieldValues = fieldValues;
  }
}
