package com.jengine.j2se.util;

import com.jengine.j2se.util.regex.RegexSample;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author nouuid
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
