package com.jengine.common.pattern.structural.adapter;


import com.jengine.common.pattern.structural.adapter.objectadapter.Adaptee;
import com.jengine.common.pattern.structural.adapter.objectadapter.Adapter;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/25.
 */
public class ObjectAdapterTest {

    @Test
    public void test() {
        AdapterTarget adapter = new Adapter(new Adaptee());
        adapter.request();
    }
}
