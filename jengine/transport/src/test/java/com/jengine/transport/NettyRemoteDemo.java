package com.jengine.transport;

import com.jengine.transport.protocol.netty.remote.NettyClient;
import com.jengine.transport.protocol.netty.remote.NettyServer;
import org.junit.Test;

/**
 * content
 *
 * @author bl07637
 * @date 12/15/2016
 * @since 0.1.0
 */
public class NettyRemoteDemo {

    @Test
    public void server() throws Throwable {
        NettyServer nettyServer = new NettyServer(2000);
        nettyServer.open();
    }

    @Test
    public void client() throws Throwable {
        NettyClient nettyClient = new NettyClient("bl07637", 2000);
        nettyClient.open();
        nettyClient.doConnect();
    }
}
