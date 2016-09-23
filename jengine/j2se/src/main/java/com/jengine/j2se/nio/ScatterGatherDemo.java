package com.jengine.j2se.nio;

import java.nio.ByteBuffer;

/**
 * content
 *
 * @author bl07637
 * @date 9/22/2016
 * @since 0.1.0
 */
public class ScatterGatherDemo {

    public void demo() {
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArray = { header, body };
//        channel.read(bufferArray);
    }
}
