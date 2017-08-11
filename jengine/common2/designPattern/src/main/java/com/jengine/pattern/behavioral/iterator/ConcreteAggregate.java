package com.jengine.pattern.behavioral.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nouuid on 2015/5/15.
 */
public class ConcreteAggregate implements Aggregate {
    private List<Object> list = new ArrayList<Object>();

    public void add(Object obj) {
        list.add(obj);
    }

    public Iterator iterator() {
        return new ConcreteIterator(list);
    }

    public void remove(Object obj) {
        list.remove(obj);
    }
}
