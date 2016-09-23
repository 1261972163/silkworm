package com.jengine.common.sort;

/**
 * @author nouuid
 * @date 3/21/2016
 * @description selection sort
 */
public class SelectionSort extends Sort {

    public void sort() {
        for(int i=0; i<data.length; i++) {
            int pos = i;
            for(int j=i+1; j<data.length; j++) {
                if(data[j]<data[pos]) {
                    pos = j;
                }
            }

            if(pos!=i) {
                exchange(i, pos);
            }
        }

    }
}
