package com.jengine.transport.netty.remote;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author nouuid
 * @description
 * @date 10/13/16
 */
public class NettyCodecAdapter {
    private final ChannelHandler encoder = new StringEncoder();

    private final ChannelHandler decoder = new StringDecoder();

    public ChannelHandler getEncoder() {
        return encoder;
    }

    public ChannelHandler getDecoder() {
        return decoder;
    }
}
