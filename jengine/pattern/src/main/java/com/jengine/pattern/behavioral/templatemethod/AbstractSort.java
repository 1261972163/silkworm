package com.jengine.pattern.behavioral.templatemethod;

/**
 * Created by nouuid on 2015/5/22.
 */
public abstract class AbstractSort {
    /**
     * ������array��С��������
     *
     * @param array
     */
    protected abstract void sort(int[] array);

    public void showSortResult(int[] array) {
        this.sort(array);
        System.out.print("��������");
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%3s", array[i]);
        }
    }
}
