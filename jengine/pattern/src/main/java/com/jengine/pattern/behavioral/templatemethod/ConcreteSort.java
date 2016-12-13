package com.jengine.pattern.behavioral.templatemethod;

/**
 * Created by nouuid on 2015/5/22.
 */
public class ConcreteSort extends AbstractSort {

    @Override
    protected void sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            selectSort(array, i);
        }
    }

    private void selectSort(int[] array, int index) {
        int MinValue = 32767; // ��Сֵ����
        int indexMin = 0; // ��Сֵ��������
        int Temp; // �ݴ����
        for (int i = index; i < array.length; i++) {
            if (array[i] < MinValue) { // �ҵ���Сֵ
                MinValue = array[i]; // ������Сֵ
                indexMin = i;
            }
        }
        Temp = array[index]; // ��������ֵ
        array[index] = array[indexMin];
        array[indexMin] = Temp;
    }
}
