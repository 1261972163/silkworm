package com.jengine.java.util;

import com.jengine.java.util.regex.RegexSample;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author bl07637
 * @date 3/25/2016
 * @description
 */
public class RegexSampleTest {
    RegexSample regexSample = new RegexSample();

    @Test
    public void replaceTest() {
        String inputStr = "this.is.a.test";
        String expected = "this/is/a/test";
        Assert.assertEquals(expected, regexSample.replaceDotWithSlash(inputStr));
    }

}
