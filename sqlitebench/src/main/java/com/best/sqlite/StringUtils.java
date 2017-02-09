package com.best.sqlite;

/**
 * content
 *
 * @author nouuid
 * @date 2/8/2017
 * @since 0.1.0
 */
public class StringUtils {
    public static boolean isBlank(String s) {
        if (s != null && s.trim().length() > 0) {
            return false;
        }
        return true;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }
}
