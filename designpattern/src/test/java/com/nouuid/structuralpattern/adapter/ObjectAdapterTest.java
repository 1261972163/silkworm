package com.nouuid.structuralpattern.adapter;

import com.nouuid.structuralpattern.adapter.objectadapter.Adaptee;
import com.nouuid.structuralpattern.adapter.objectadapter.Adapter;
import com.nouuid.structuralpattern.adapter.objectadapter.Target;
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
