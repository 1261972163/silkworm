package com.jengine.data.elasticsearch.mydataframe.so;

/**
 * content
 *
 * @author nouuid
 * @date 10/20/2016
 * @since 0.1.0
 */
public class FieldCondition {
  private String field;                 // 数据域名
  private String condition;             // 查询内容
  private boolean wildcardActive = false;   // isWildcard表达查询内容是否包含通配符

  public FieldCondition(String field, String condition) {
    this.field = field;
    this.condition = condition;
  }

  public FieldCondition(String field, String condition, boolean isWildcard) {
    this.field = field;
    this.condition = condition;
    this.wildcardActive = isWildcard;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public boolean isWildcardActive() {
    return wildcardActive;
  }

  public void setWildcardActive(boolean wildcardActive) {
    this.wildcardActive = wildcardActive;
  }
}
