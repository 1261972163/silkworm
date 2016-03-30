package com.jengine.feature.serialize.compress;

/**
 * @author bl07637
 * @date 3/25/2016
 * @description
 */
public interface CompressStrategy {

    String compress(String str);
    String decompress(String compressedStr);
}
