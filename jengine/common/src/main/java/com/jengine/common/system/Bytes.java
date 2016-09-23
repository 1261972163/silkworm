package com.jengine.common.system;

/**
 * @author nouuid
 * @date 8/5/2016
 * @description byte
 */
public class Bytes {
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
