package com.jengine.data.elasticsearch.mydataframe.so;

/**
 * 指定数据域进行排序
 *
 * @author nouuid
 * @date 8/11/2016
 * @description
 */
public class SortSO {
  // sort
  protected boolean active = false;                  // 开启排序
  protected String field;                        // 指定排序的字段
  protected SortOrder order = SortOrder.DESC;    // 指定排序类型

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public SortOrder getOrder() {
    return order;
  }

  public void setOrder(SortOrder order) {
    this.order = order;
  }
}
