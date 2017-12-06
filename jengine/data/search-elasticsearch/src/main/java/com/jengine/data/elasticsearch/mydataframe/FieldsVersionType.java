package com.jengine.data.elasticsearch.mydataframe;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * content
 *
 * @author nouuid
 * @date 7/15/2017
 * @since 0.1.0
 */
public class FieldsVersionType {
  private DataType type = DataType.TEXT;
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  private AnalyzerType analyzer = AnalyzerType.IK;

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
}
