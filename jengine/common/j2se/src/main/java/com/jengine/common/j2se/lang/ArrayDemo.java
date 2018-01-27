package com.jengine.common.j2se.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author nouuid
 * @description
 * @date 9/3/16
 */
public class ArrayDemo {
    public static void main(String[] args) {
        Object[] objectArray = null;

        List<String> list = Arrays.asList("string1","string2");
        objectArray = list.toArray();
        System.out.println(list.getClass());
        System.out.println(list.toArray().getClass());
        System.out.println(objectArray.getClass());

        for (int i=0; i<2; i++) {
            System.out.println(objectArray[i]);
        }

        System.out.println("---------");
        System.out.println(objectArray);
        Object[] x = Arrays.copyOf(objectArray, 1);
        System.out.println(x);
        System.out.println("---------");

        ArrayList<Integer> list2 = new ArrayList<Integer>();
        for (int i=0; i<2; i++) {
            list2.add(new Integer(i));
        }
        System.out.println("objectArray.getClass(): " + objectArray.getClass());
        System.out.println("list2.toArray().getClass(): " + list2.toArray().getClass());
        objectArray = list2.toArray();
        System.out.println(objectArray.getClass());
        for (int i=0; i<2; i++) {
            System.out.println(objectArray[i]);
        }


    }
}
