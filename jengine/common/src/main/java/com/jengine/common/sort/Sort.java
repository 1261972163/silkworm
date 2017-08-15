package com.jengine.common.sort;

/**
 * @author nouuid
 * @date 3/21/2016
 * @description
 */
public abstract class Sort {

  protected int[] data;

  public abstract void sort();

  public void exchange(int i, int j) {
    if ((i > 0 && i < data.length) && (j > 0 && j < data.length)) {
      int tmp = data[i];
      data[i] = data[j];
      data[j] = tmp;
    }
  }
}
