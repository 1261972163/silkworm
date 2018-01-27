package com.jengine.transport.serialize;

import com.jengine.transport.serialize.encoding.EncodingStrategy;
import com.jengine.transport.serialize.encoding.HexCode;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public class EncodingStrategyTest {
    public static final Logger logger = LoggerFactory.getLogger(EncodingStrategyTest.class);

    EncodingStrategy encodingService;

    @Test
    public void hexCodeTest() {
        encodingService = new HexCode();
        String input = new String("[15, 101, -110, 22, -123, -10, 44, 124, -70, 71, 98, 45, 110, 33, 31, 39]");
        String expectedHexRes = "5b31352c203130312c202d3131302c2032322c202d3132332c202d31302c2034342c203132342c202d37302c2037312c2039382c2034352c203131302c2033332c2033312c2033395d";

        String hexRes = encodingService.code(input);
        Assert.assertEquals(hexRes, expectedHexRes);

        String backStr = encodingService.decode(hexRes);
        Assert.assertEquals(backStr, input);
    }

}
