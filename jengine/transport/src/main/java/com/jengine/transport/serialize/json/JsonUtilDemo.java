package com.jengine.transport.serialize.json;

import org.junit.Test;

import java.util.HashMap;

/**
 * content
 *
 * @author nouuid
 * @date 12/14/2016
 * @since 0.1.0
 */
public class JsonUtilDemo {
    @Test
    public void test() {
        HashMap<String, Object> mdMap = new HashMap<String, Object>();
        mdMap.put("weight", 10);
        System.out.println(JsonUtil.toJson(mdMap));
    }
}
