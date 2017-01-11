package com.nouuid.mysqlbench.common;

/**
 * @author nouuid
 * @date 7/13/2016
 * @description
 */
public class StringUtils {
    public static boolean isBlank(String s) {
        if (s != null && s.trim().length()>0) {
            return false;
        }
        return true;
    }
}
