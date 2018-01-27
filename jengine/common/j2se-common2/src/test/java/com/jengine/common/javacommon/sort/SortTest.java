package  com.jengine.common.javacommon.sort;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/28.
 */
public class SortTest {

  private int[] data = {1, 8, 3, 6, 5, 4, 7, 2, 10, 9};

  private Sort sortInstance = null;

  public void sort() {
    System.out.print("before:");
    print();
    System.out.println();
    sortInstance.sort();
    System.out.print("after:");
    print();
    System.out.println();
  }

  public void print() {
    for (int i : sortInstance.data) {
      System.out.print(i + ", ");
    }
  }

  @Test
  public void insertionSortTest() {
    sortInstance = new InsertionSort();
    sortInstance.data = data;
    sort();
  }

  @Test
  public void bubbleSort() {

  }

  @Test
  public void selectionSort() {

  }

}
