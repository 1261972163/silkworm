package com.jengine.common.pattern.structural.adapter;


import com.jengine.common.pattern.structural.adapter.objectadapter.Adaptee;
import com.jengine.common.pattern.structural.adapter.objectadapter.Adapter;
import com.jengine.pattern.structural.adapter.objectadapter.Target;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/25.
 */
public class ObjectAdapterTest {

    @Test
    public void test() {
        Target adapter = new Adapter(new Adaptee());
        adapter.request();
    }
}
