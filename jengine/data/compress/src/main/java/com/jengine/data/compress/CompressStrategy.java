package com.jengine.data.compress;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public interface CompressStrategy {

    String compress(String str);
    String decompress(String compressedStr);
}
