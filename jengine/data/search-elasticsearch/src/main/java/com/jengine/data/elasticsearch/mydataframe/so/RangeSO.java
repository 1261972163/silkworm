package com.jengine.data.elasticsearch.mydataframe.so;

/**
 * 指定数据域进行范围查询
 *
 * @author bl07637
 * @date 8/11/2016
 * @description
 */
public class RangeSO {
  protected boolean active = false;  // 开启范围查询
  protected String rangeField;        // 数据域名
  protected String rangeFrom;         // 上区间
  protected String rangeTo;           // 下区间

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getRangeField() {
    return rangeField;
  }

  public void setRangeField(String rangeField) {
    this.rangeField = rangeField;
  }

  public String getRangeFrom() {
    return rangeFrom;
  }

  public void setRangeFrom(String rangeFrom) {
    this.rangeFrom = rangeFrom;
  }

  public String getRangeTo() {
    return rangeTo;
  }

  public void setRangeTo(String rangeTo) {
    this.rangeTo = rangeTo;
  }
}
