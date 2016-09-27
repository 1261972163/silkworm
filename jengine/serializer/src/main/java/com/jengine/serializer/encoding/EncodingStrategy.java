package com.jengine.serializer.encoding;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public interface EncodingStrategy {

    public String code(String s);
    public String decode(String s);
}