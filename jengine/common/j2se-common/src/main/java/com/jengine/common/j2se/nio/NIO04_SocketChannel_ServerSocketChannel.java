package com.jengine.common.j2se.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 *
 * 1. SocketChannel是用于TCP网络连接的套接字接口，相当于Java网络编程中的Socket套接字接口。
 *  创建SocketChannel主要有两种方式，如下：
 *      (1) 打开一个SocketChannel并连接网络上的一台服务器。
 *      (2) 当ServerSocketChannel接收到一个连接请求时，会创建一个SocketChannel。
 *
 * 2. ServerSocketChannel允许我们监听TCP链接请求，每个请求会创建会一个SocketChannel.
 *
 * @author nouuid
 * @description
 * @date 5/11/17
 */
public class NIO04_SocketChannel_ServerSocketChannel {
    Server server = new Server();
    Client client = new Client();

    @Test
    public void server() throws IOException, InterruptedException {
        server.open();
        server.listen();
    }

    @Test
    public void client() throws IOException {
        SocketChannel socketChannel = null;
        socketChannel = client.init();
        client.connect(socketChannel, "127.0.0.1", 9999);
        client.write(socketChannel, new String("New String to write to file..." + System.currentTimeMillis()));
    }

    /**
     * ServerSocketChannel
     */
    class Server {

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

    /**
     * SocketChannel
     */
    class Client {

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
}
