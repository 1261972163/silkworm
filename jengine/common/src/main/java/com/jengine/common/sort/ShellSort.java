package com.jengine.common.sort;

/**
 * @author nouuid
 * @date 3/21/2016
 * @description shell sort
 */
public class ShellSort extends Sort {

  @Override
  public void sort() {
    int tmp;
    for (int h = data.length / 2; h > 0; h = h / 2) {
      for (int i = h; i < data.length; i++) {
        tmp = data[i];
        for (int j = i - h; j >= 0 && tmp < data[j]; j = j - h) {
          data[j + h] = data[j];
          data[j] = tmp;
        }
      }

    }
  }
}
