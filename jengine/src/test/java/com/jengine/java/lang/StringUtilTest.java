package com.jengine.java.lang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/7.
 */
public class StringUtilTest {

    public static final Log logger = LogFactory.getLog(StringUtilTest.class);

    @Test
    public void test() {
        String input = new String("[15, 101, -110, 22, -123, -10, 44, 124, -70, 71, 98, 45, 110, 33, 31, 39]");
        String expectedHexRes = "5b31352c203130312c202d3131302c2032322c202d3132332c202d31302c2034342c203132342c202d37302c2037312c2039382c2034352c203131302c2033332c2033312c2033395d";

        logger.info("------------hexCode() start-------------");
        String hexRes = StringUtil.hexCode(input);
        Assert.assertEquals(hexRes, expectedHexRes);
        logger.info("------------hexCode() end-------------");

        logger.info("------------hexDecode() start-------------");
        String backStr = StringUtil.hexDecode(hexRes);
        Assert.assertEquals(backStr, input);
        logger.info("------------hexDecode() end-------------");


    }




}
