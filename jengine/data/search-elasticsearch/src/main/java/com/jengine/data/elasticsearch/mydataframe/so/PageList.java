package com.jengine.data.elasticsearch.mydataframe.so;

import java.util.List;

/**
 * content
 *
 * @author bl07637
 * @date 9/28/2016
 * @since 0.1.0
 */
public class PageList<T> {
  public static int defaultPageSize = 50;

  /**
   * 每页的列表
   */
  private List<T> list;

  /**
   * 总记录数
   */
  private long fullSize = 0;

  /**
   * 当前页码
   */
  private int pageNumber = 1;

  /**
   * 每页记录数 page size
   */
  private int pageSize = defaultPageSize;

  public PageList() {

  }

  public PageList(SearcherSO so) {
    if (so != null) {
      this.pageNumber = so.getPageSO().getPageNumber();
      this.pageSize = so.getPageSO().getPageSize();
    }
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public long getFullSize() {
    return fullSize;
  }

  public void setFullSize(long fullSize) {
    this.fullSize = fullSize;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
}
