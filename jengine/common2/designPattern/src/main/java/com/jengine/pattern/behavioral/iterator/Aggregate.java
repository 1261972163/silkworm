package com.jengine.pattern.behavioral.iterator;

/**
 * Created by nouuid on 2015/5/15.
 */
public interface Aggregate {
    void add(Object obj);
    void remove(Object obj);
    Iterator iterator();
}
