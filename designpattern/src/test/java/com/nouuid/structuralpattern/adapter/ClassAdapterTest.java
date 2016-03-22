package com.nouuid.structuralpattern.adapter;

import com.nouuid.structuralpattern.adapter.classadapter.Adapter;
import com.nouuid.structuralpattern.adapter.classadapter.Target;
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
