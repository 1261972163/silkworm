package com.jengine.feature.proxy;

/**
 * @author nouuid
 * @description
 * @date 9/11/16
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String say(String s) {
        return s;
    }
}

