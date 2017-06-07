package com.jengine.transport.serialize;

import junit.framework.Assert;
import org.junit.Test;

import java.io.Serializable;

/**
 * @author nouuid
 * @date 3/28/2016
 * @description
 */
public class SimpleSerializeTest {
    SimpleSerialize ss = new SimpleSerialize();

    @Test
    public void testString() {
        String s = "this is a test";
        byte[] bytes = ss.toByte(s);

        String res = (String)ss.toObject(bytes);
        Assert.assertEquals(s, res);
    }

    @Test
    public void testObject() {
        SimpleSerializeTestObject ssto = new SimpleSerializeTestObject();
        ssto.setId(123);
        ssto.setName("test");

        byte[] bytes = ss.toByte(ssto);
        SimpleSerializeTestObject res = (SimpleSerializeTestObject)ss.toObject(bytes);
        Assert.assertEquals(ssto.getId(), res.getId());
        Assert.assertEquals(ssto.getName(), res.getName());
    }
}

class SimpleSerializeTestObject implements Serializable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

