package com.jengine.j2se;

import java.util.Arrays;
import java.util.List;

/**
 * content
 *
 * @author bl07637
 * @date 9/26/2016
 * @since 0.1.0
 */
public class Test {
    @org.junit.Test
    public void test1() {
        int[] x = new int[3];
        x[0] = 1;
        x[1] = 2;
        x[2] = 3;
        List list = Arrays.asList(x);


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int id : x) {
            stringBuilder.append(id).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
    }
}
