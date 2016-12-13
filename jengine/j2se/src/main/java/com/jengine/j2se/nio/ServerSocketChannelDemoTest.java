package com.jengine.j2se.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @author nouuid
 * @description
 * @date 9/25/16
 */
public class ServerSocketChannelDemoTest {
    ServerSocketChannelDemo serverSocketChannelDemo = new ServerSocketChannelDemo();
    SocketChannelDemo socketChannelDemo = new SocketChannelDemo();

    @Test
    public void server() throws IOException, InterruptedException {
        serverSocketChannelDemo.open();
        serverSocketChannelDemo.listen();
    }

    @Test
    public void client() throws IOException {
        SocketChannel socketChannel = null;
        socketChannel = socketChannelDemo.init();
        socketChannelDemo.connect(socketChannel, "127.0.0.1", 9999);
        socketChannelDemo.write(socketChannel, new String("New String to write to file..." + System.currentTimeMillis()));
    }
}
