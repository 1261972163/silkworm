package  com.jengine.common.javacommon.sort;

/**
 * @author nouuid
 * @date 3/21/2016
 * @description insertion sorting
 */
public class InsertionSort extends Sort {

  public void sort() {
    int tmp;
    for (int i = 0; i < data.length; i++) {
      tmp = data[i];
      for (int j = i - 1; j >= 0 && tmp < data[j]; j--) {
        data[j + 1] = data[j];
        data[j] = tmp;
      }
    }
  }
}
