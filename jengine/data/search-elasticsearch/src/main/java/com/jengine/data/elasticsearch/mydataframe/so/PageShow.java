package com.jengine.data.elasticsearch.mydataframe.so;

import java.util.List;

/**
 * content
 *
 * @author bl07637
 * @date 5/10/2017
 * @since 0.1.0
 */
public class PageShow<T> {
  /**
   * 每页的列表
   */
  private List<T> list;

  /**
   * 总记录数
   */
  private long fullSize = 0;


  public PageShow() {

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

}
