package com.jengine.data.elasticsearch.mydataframe;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Map;

/**
 * @author nouuid
 * @date 8/5/2016
 * @description
 */
public class RecordType {
  private DataType type;
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  private AnalyzerType analyzer = AnalyzerType.IK;
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  private Map<String, FieldsVersionType> fields;
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  private DateFormatType format;
  private StoreType store = StoreType.TRUE;
  private FieldDataType fielddata = FieldDataType.TRUE;

  public DataType getType() {
    return type;
  }

  public void setType(DataType type) {
    this.type = type;
  }

  public AnalyzerType getAnalyzer() {
    return analyzer;
  }

  public void setAnalyzer(AnalyzerType analyzer) {
    this.analyzer = analyzer;
  }

  public StoreType getStore() {
    return store;
  }

  public void setStore(StoreType store) {
    this.store = store;
  }

  public DateFormatType getFormat() {
    return format;
  }

  public void setFormat(DateFormatType format) {
    this.format = format;
  }

  public Map<String, FieldsVersionType> getFields() {
    return fields;
  }

  public void setFields(Map<String, FieldsVersionType> fields) {
    this.fields = fields;
  }

  public FieldDataType getFielddata() {
    return fielddata;
  }

  public void setFielddata(FieldDataType fielddata) {
    this.fielddata = fielddata;
  }
}
