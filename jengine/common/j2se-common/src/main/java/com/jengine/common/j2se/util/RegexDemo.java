package com.jengine.common.j2se.util;

import junit.framework.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public class RegexDemo {

    @Test
    public void replaceTest() {
        String inputStr = "this.is.a.test";
        String expected = "this/is/a/test";
        Assert.assertEquals(expected, replaceDotWithSlash(inputStr));
    }

    public String replaceDotWithSlash(String inputStr) {
        String res = inputStr;
        Pattern p = null;
        Matcher m = null;

        while(res.contains(".")) {
            p = Pattern.compile("\\.");
            m = p.matcher(res);
            res = m.replaceFirst("\\/");
        }

        return res;
    }
}
