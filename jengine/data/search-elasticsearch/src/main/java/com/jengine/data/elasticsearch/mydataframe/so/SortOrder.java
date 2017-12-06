package com.jengine.data.elasticsearch.mydataframe.so;

/**
 * @author nouuid
 * @date 8/11/2016
 * @description A sorting order.
 */

public enum SortOrder {
  /**
   * Ascending order.
   */
  ASC {
    @Override
    public String toString() {
      return "asc";
    }
  },
  /**
   * Descending order.
   */
  DESC {
    @Override
    public String toString() {
      return "desc";
    }
  }
}
