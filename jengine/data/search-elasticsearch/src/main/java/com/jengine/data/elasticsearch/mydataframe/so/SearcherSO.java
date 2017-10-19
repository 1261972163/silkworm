package com.jengine.data.elasticsearch.mydataframe.so;

/**
 * @author bl07637
 * @date 8/4/2016
 * @description
 */
public class SearcherSO {

  private BooleanSO booleanSO = new BooleanSO();     // 封装具体查询条件
  private RangeSO rangeSO = new RangeSO();       // 范围查询
  private SortSO sortSO = new SortSO();          // 排序
  private PageSO pageSO = new PageSO();        // 分页

  public BooleanSO getBooleanSO() {
    return booleanSO;
  }

  public void setBooleanSO(BooleanSO booleanSO) {
    this.booleanSO = booleanSO;
  }

  public RangeSO getRangeSO() {
    return rangeSO;
  }

  public void setRangeSO(RangeSO rangeSO) {
    this.rangeSO = rangeSO;
  }

  public SortSO getSortSO() {
    return sortSO;
  }

  public void setSortSO(SortSO sortSO) {
    this.sortSO = sortSO;
  }

  public PageSO getPageSO() {
    return pageSO;
  }

  public void setPageSO(PageSO pageSO) {
    this.pageSO = pageSO;
  }
}
