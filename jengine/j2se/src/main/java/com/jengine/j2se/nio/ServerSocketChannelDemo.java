package com.jengine.j2se.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author nouuid
 * @description
 * @date 9/25/16
 */
public class ServerSocketChannelDemo {

    ServerSocketChannel serverSocketChannel = null;

    public void open() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
    }

    public void close() throws IOException {
        serverSocketChannel.close();
    }

    public void listen() throws IOException, InterruptedException {
        System.out.println("listening...");
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null){
                read(socketChannel);
            } else {
                Thread.sleep(1000);
            }
        }
    }

    private void read(SocketChannel socketChannel) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = socketChannel.read(buf);
        while (bytesRead != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            buf.clear();//清空
            bytesRead = socketChannel.read(buf);
        }
    }
}
