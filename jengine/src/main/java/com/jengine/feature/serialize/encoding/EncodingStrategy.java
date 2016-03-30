package com.jengine.feature.serialize.encoding;

/**
 * @author bl07637
 * @date 3/25/2016
 * @description
 */
public interface EncodingStrategy {

    public String code(String s);
    public String decode(String s);
}
