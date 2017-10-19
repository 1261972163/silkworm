package com.jengine.data.elasticsearch.tools;


import com.jengine.data.elasticsearch.mydataframe.AnalyzerType;
import com.jengine.data.elasticsearch.mydataframe.DataType;
import com.jengine.data.elasticsearch.mydataframe.DateFormatType;
import com.jengine.data.elasticsearch.mydataframe.FieldDataType;
import com.jengine.data.elasticsearch.mydataframe.FieldsVersionType;
import com.jengine.data.elasticsearch.mydataframe.RecordType;
import com.jengine.data.elasticsearch.mydataframe.StoreType;

import java.util.Map;

/**
 * @author bl07637
 * @date 8/10/2016
 * @description
 */
public class RecordTypeBuilder {
  private DataType dateType;
  private AnalyzerType analyzer;
  private Map<String, FieldsVersionType> fields;
  private StoreType storeType;
  private DateFormatType format;
  private FieldDataType fieldDataType;

  public RecordTypeBuilder setDateType(DataType dateType) {
    this.dateType = dateType;
    return this;
  }

  public RecordTypeBuilder setAnalyzerType(AnalyzerType analyzerType) {
    this.analyzer = analyzerType;
    return this;
  }

  public RecordTypeBuilder setFields(Map<String, FieldsVersionType> fields) {
    this.fields = fields;
    return this;
  }

  public RecordTypeBuilder setStoreType(StoreType storeType) {
    this.storeType = storeType;
    return this;
  }

  public RecordTypeBuilder setDateFormatType(DateFormatType dateFormatType) {
    this.format = dateFormatType;
    return this;
  }

  public RecordTypeBuilder setFieldDataType(FieldDataType fieldDataType) {
    this.fieldDataType = fieldDataType;
    return this;
  }

  public RecordType build() {
    RecordType dataField = new RecordType();
    dataField.setAnalyzer(analyzer);
    dataField.setFields(fields);
    dataField.setStore(storeType);
    dataField.setType(dateType);
    dataField.setFormat(format);
    dataField.setFielddata(fieldDataType);
    return dataField;
  }


}
