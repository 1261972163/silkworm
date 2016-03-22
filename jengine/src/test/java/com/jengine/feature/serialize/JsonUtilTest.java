package com.jengine.feature.serialize;

import org.junit.Assert;
import org.junit.Test;

import com.jengine.feature.serialize.json.JsonUtil;

/**
 * Created by nouuid on 2015/5/7.
 */
public class JsonUtilTest {

    @Test
    public void formJson() {
        String intput = "IFt7InJlZkNvZGUiOiIyMDE1MDMxMTEwMjYiLCJjcmVhdGVUaW1lIjoxNDI2MDQwODEyMDAwLCJwYXJjZWxUb3RhbE51bSI6MH0seyJyZWZDb2RlIjoiMjAxNTAzMzExNDI0IiwiY3JlYXRlVGltZSI6MTQyNzc4MzA1NDAwMCwicGFyY2VsVG90YWxOdW0iOjB9XQ==";
        String output = "[{\"refCode\":\"201503111026\",\"createTime\":1426040812000,\"parcelTotalNum\":0},{\"refCode\":\"201503311424\",\"createTime\":1427783054000,\"parcelTotalNum\":0}]";
        String s = " {\"arg1\":\"" + intput + "\"}";
        Body body = JsonUtil.formJson(Body.class, s);
        String result = new String(body.getArg1());

        Assert.assertEquals(result.trim(), output.trim());
    }
}
