package com.jengine.common.sort;

/**
 * @author nouuid
 * @date 3/21/2016
 * @description quick sort
 */
public class QuickSort extends Sort {

    @Override
    public void sort() {
        quickSort(data, 0, data.length-1);
    }

    private void quickSort(int[] data, int low, int high) {
        if(low<high) {
            int pivotKey = partition(data, low, high);
            quickSort(data, low, pivotKey - 1);
            quickSort(data, pivotKey+1, high);
        }
    }

    private int partition(int[] data, int low, int high) {
        int pivotkey = data[low];
        while (low<high) {
            while (low<high && data[high]>=pivotkey) {
                --high;
            }
            data[low] = data[high];
            while (low<high && data[low]<=pivotkey) {
                ++low;
            }
            data[high] = data[low];
        }
        data[low] = pivotkey;
        return low;
    }
}
