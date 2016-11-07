package com.jengine.pattern.behavioral.chainofresponsibility;

/**
 * 请求
 * Created by nouuid on 2015/5/14.
 */
public class Request {
    Level level;

    public Request(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }
}
