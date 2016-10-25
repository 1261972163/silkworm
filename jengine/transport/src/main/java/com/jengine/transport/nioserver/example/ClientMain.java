package com.jengine.transport.nioserver.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * content
 *
 * @author bl07637
 * @date 9/29/2016
 * @since 0.1.0
 */
public class ClientMain {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("127.0.0.1", 9999)); //初始连接，执行finishConnect()才能完成连接
//        channel.register(selector, SelectionKey.OP_CONNECT);

//        channel.write(ByteBuffer.wrap(new Message()));//向服务端发送信息
    }
}
