package com.jengine.common2.javacommon.sort;

/**
 * @author nouuid
 * @date 3/21/2016
 * @description bubble sort
 */
public class BubbleSort extends Sort {

  @Override
  public void sort() {
    boolean exchanged = false;
    for (int i = 0; i < data.length; i++) {
      exchanged = false;
      for (int j = data.length - 1; j > i; j--) {
        if (data[j - 1] > data[j]) {
          exchange(j - 1, j);
          exchanged = true;
        }
      }
      if (!exchanged) {
        return;
      }

    }
  }
}
