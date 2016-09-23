package com.jengine.j2se.nio;

import org.junit.Test;

import java.io.IOException;

/**
 * content
 *
 * @author bl07637
 * @date 9/20/2016
 * @since 0.1.0
 */
public class ChannelDemoTest {

    ChannelDemo channelDemo = new ChannelDemo();

    @Test
    public void allocate() throws IOException {
        String filePath = ChannelDemo.class.getResource("/").getPath() + "java/nio/data.txt";
        channelDemo.fileChannel(filePath);
    }
}
