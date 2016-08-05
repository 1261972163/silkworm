package com.jengine.feature.sort;

/**
 * @author nouuid
 * @date 3/21/2016
 * @description
 *
 * heap sort has tow processes:
 * 1. build heap
 * 2. exchange top element and the last element of heap
 *
 */
public class HeapSort extends Sort {
    @Override
    public void sort() {
        init();
        for (int i=data.length-1; i>0; --i) {
            int temp = data[i]; data[i] = data[0]; data[0] = temp;
        }
    }

    private void exchange() {

    }

    /*
     * init heap
     */
    public void init() {
        for (int i=(data.length-1)/2; i>-0; --i) {
            adjust(i);
        }
    }

    public void adjust(int s) {
        int child = 2*s+1;
    }



}
