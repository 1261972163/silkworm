package com.jengine.pattern.behavioral.chainofresponsibility;

/**
 * 模拟判定条件
 * Created by nouuid on 2015/5/14.
 */
public class Level {
    private int level = 0;

    public Level(int level) {
        this.level = level;
    }

    public boolean compareTo(Level level) {
        return (this.level >= level.level);
    }
}
