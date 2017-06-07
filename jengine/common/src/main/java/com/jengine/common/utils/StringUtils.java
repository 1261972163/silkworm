package com.jengine.common.utils;

/**
 * content
 *
 * @author bl07637
 * @date 6/5/2017
 * @since 0.1.0
 */
public class StringUtils {
    public static boolean isBlank(String s) {
        if (s==null || "".equals(s.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    /**
     * String.subString has wrong in dealing with chinese
     *
     * @param src
     * @param start_idx
     * @param end_idx
     * @return
     */
    public static String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        String tgt = "";
        for (int i = start_idx; i <= end_idx; i++) {
            tgt += (char) b[i];
        }
        return tgt;
    }
}
