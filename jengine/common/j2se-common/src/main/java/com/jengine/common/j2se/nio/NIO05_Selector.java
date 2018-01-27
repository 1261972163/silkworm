package com.jengine.common.j2se.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * 1. Selector的作用是让一个线程处理多个通道，好处是减少线程数量，同时可实现非阻塞。
 * 2. 与Selector一起使用时，Channel必须处于非阻塞模式下。
 *      这意味着不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式。而套接字通道都可以。
 * 3. 向Selector注册通道，可以监听四种事件类型：
 *      SelectionKey.OP_CONNECT
 *      SelectionKey.OP_ACCEPT
 *      SelectionKey.OP_READ
 *      SelectionKey.OP_WRITE
 *
 * @author nouuid
 * @description
 * @date 9/24/16
 */
public class NIO05_Selector {

    @Test
    public void server() throws IOException, InterruptedException {

        // 1. 创建Selector
        Selector selector = Selector.open();
        // 2. 创建Channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        // 3. 向Selector注册通道
        SelectionKey key1 = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        key1.attach("key1");
        while(true) {
            // 4. select()方法的返回值是一个int整形，代表有多少channel处于就绪了。
            int readyChannels = selector.select();
            if(readyChannels == 0) continue;
            // 5. 获得selectedKeys
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            // 6. 迭代
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                System.out.println("### " + key.attachment());
                // 7. 处理
                if(key.isAcceptable()) {
                    System.out.println("a connection was accepted by a ServerSocketChannel.");
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                } else if (key.isConnectable()) {
                    System.out.println("a connection was established with a remote server.");
                } else if (key.isReadable() || key.isWritable()) {
                    System.out.println("a channel is ready for reading/writing.");
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                    while(true) {
                        byteBuffer.clear();
                        int r = socketChannel.read(byteBuffer);
                        if(r == 0)
                            break;
                        if(r == -1) {
                            socketChannel.close();
                            key.cancel();
                            break;
                        }
                        System.out.println(new String(byteBuffer.array()));
                    }
                }
                // 8. remove掉
                keyIterator.remove();
                Thread.sleep(1000);
            }
            System.out.println("round.");
        }
    }

    @Test
    public void client() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

        String data = new String("New String to write to file..." + System.currentTimeMillis());
        ByteBuffer buf = ByteBuffer.allocate(256);
        buf.clear();
        buf.put(data.getBytes());
        buf.flip();
        socketChannel.write(buf);
    }

    private void write(SocketChannel socketChannel, String newData) throws IOException {
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

}
