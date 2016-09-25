package com.jengine.j2se.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * SocketChannel是用于TCP网络连接的套接字接口，相当于Java网络编程中的Socket套接字接口。
 * <p>
 * 创建SocketChannel主要有两种方式，如下：
 * 1. 打开一个SocketChannel并连接网络上的一台服务器。
 * 2. 当ServerSocketChannel接收到一个连接请求时，会创建一个SocketChannel。
 *
 * @author nouuid
 * @description
 * @date 9/24/16
 */
public class SocketChannelDemo {

    public SocketChannel init() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        return socketChannel;
    }

    public SocketChannel connect(SocketChannel socketChannel, String host, int port) throws IOException {
        socketChannel.connect(new InetSocketAddress(host, port));
        return socketChannel;
    }

    public void close(SocketChannel socketChannel) throws IOException {
        socketChannel.close();
    }

    public void write(SocketChannel socketChannel, String newData) throws IOException {
        int length = newData.getBytes().length;
        int capacity = 48;
        ByteBuffer buf = ByteBuffer.allocate(capacity);
        byte[] bytes;
        for (int i=1; i<= length/capacity + 1; i++) {
            int start = (i-1)*capacity;
            int end = start + capacity;
            if (end>length) {
                end = length;
            }
            bytes = newData.substring(start, end).getBytes();
            buf.clear();
            buf.put(bytes);
            buf.flip();
            while(buf.hasRemaining()) {
                socketChannel.write(buf);
            }
        }
    }

    public void read(SocketChannel socketChannel) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = socketChannel.read(buf);
    }

}
