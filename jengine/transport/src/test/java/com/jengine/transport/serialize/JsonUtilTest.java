package com.jengine.transport.serialize;

import com.jengine.transport.serialize.json.JsonUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/7.
 */
public class JsonUtilTest {

    @Test
    public void byteTest() {
        JsonUtilTestBody body = new JsonUtilTestBody();
        body.setArg1("1".getBytes());
        body.setArg2(2);
        body.setArg3("3");
        byte[] bytes = JsonUtil.toByteJson(body);

        JsonUtilTestBody body2 = JsonUtil.formByteJson(JsonUtilTestBody.class, bytes);
        Assert.assertArrayEquals(body.getArg1(), body2.getArg1());
        Assert.assertEquals(body.getArg2(), body2.getArg2());
        Assert.assertEquals(body.getArg3(), body2.getArg3());
    }

    @Test
    public void stringTest() {
        JsonUtilTestBody body = new JsonUtilTestBody();
        body.setArg1("1".getBytes());
        body.setArg2(2);
        body.setArg3("3");
        String string = JsonUtil.toJson(body);

        JsonUtilTestBody body2 = JsonUtil.formJson(JsonUtilTestBody.class, string);
        Assert.assertArrayEquals(body.getArg1(), body2.getArg1());
        Assert.assertEquals(body.getArg2(), body2.getArg2());
        Assert.assertEquals(body.getArg3(), body2.getArg3());
    }
}

class JsonUtilTestBody {

    byte arg1[];
    int arg2;
    String arg3;

    public byte[] getArg1() {
        return arg1;
    }

    public void setArg1(byte[] arg1) {
        this.arg1 = arg1;
    }

    public int getArg2() {
        return arg2;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    public String getArg3() {
        return arg3;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }


}
