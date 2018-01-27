package com.jengine.common.pattern.structural.adapter;

import com.jengine.common.pattern.structural.adapter.classadapter.Adapter;
import com.jengine.pattern.structural.adapter.classadapter.Target;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/25.
 */
public class ClassAdapterTest {

    @Test
    public void test() {
        Target target = new Adapter();
        target.request();
    }
}
