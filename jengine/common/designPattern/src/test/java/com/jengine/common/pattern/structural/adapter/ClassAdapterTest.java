package com.jengine.common.pattern.structural.adapter;

import com.jengine.common.pattern.structural.adapter.classadapter.Adapter;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/25.
 */
public class ClassAdapterTest {

    @Test
    public void test() {
        AdapterTarget target = new Adapter();
        target.request();
    }
}