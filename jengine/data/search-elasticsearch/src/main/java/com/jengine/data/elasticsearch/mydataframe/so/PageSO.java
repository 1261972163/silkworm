package com.jengine.data.elasticsearch.mydataframe.so;

/**
 * @author nouuid
 * @date 8/11/2016
 * @description
 */
public class PageSO {
  protected int pageSize = 50;  // 页大小
  protected int pageNumber = 1; // 页码

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }
}
