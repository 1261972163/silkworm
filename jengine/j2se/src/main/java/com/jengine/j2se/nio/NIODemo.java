package com.jengine.j2se.nio;

import junit.framework.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * IO面临的问题：
 * （1）IO面向流的，每次只能从流中读取一个或多个字节，需要自己对读取的字节进行后续加工。
 * （2）IO是阻塞的，线程一旦调用read或write，就被阻塞住不能再进行任何操作，直到该read或write结束。
 * <p>
 * NIO解决的问题：
 * （1）NIO面向缓存区，读取字节后放入缓存。
 * （2）NIO非阻塞模式，可以让一个线程监听多个channel，调用时从中选择空闲的通道进行操作，防止线程阻塞。
 * <p>
 * NIO核心部分：
 * （1）Channel通道类似于流，区别在于：（a）通道可读可写，流都是单向的；（b）通道可异步读写，流是同步读写；（c）通道基于缓存区，流是字节。
 * （2）Buffer实质是一块内存，提供一系列的读写方便开发的接口。
 * （3）Selector是Channel的管理器，实现单线程操作多个Channel。
 * <p>
 * Buffer的使用：
 * （1）flip() 方法，position归0，并设置limit为之前的position的值。
 * （2）rewind() 方法，position归0，limit不变。
 * （3）clear() 方法，position归0，limit 被设置成 capacity 的值。换句话说，Buffer 被清空了。
 * （4）compact() 方法，将所有未读的数据拷贝到 Buffer 起始处。
 * （5）mark()方法，标记当前的position。
 * （6）reset() 方法，恢复到mark的位置。
 * <p>
 * Channel的使用：
 * （1）FileChannel用于文件的数据读写。
 * （2）DatagramChannel用于UDP的数据读写。
 * （3）SocketChannel用于TCP的数据读写。
 * （4）ServerSocketChannel允许我们监听TCP链接请求，每个请求会创建会一个SocketChannel.
 * <p>
 * DatagramChannel的使用：
 * （1）DatagramChannel是nio中处理UDP的类,udp是无连接的。
 * （2）DatagramChannel.receive(ByteBuffer dst)
 * （3）DatagramChannel.send(ByteBuffer src, SocketAddress target)。
 *
 * @author bl07637
 * @date 2/10/2017
 * @since 0.1.0
 */
public class NIODemo {

    @Test
    public void buffer() {
        String src = "this is a test.";
        ByteBuffer buffer = ByteBuffer.allocate(100);
        Assert.assertEquals(100, buffer.capacity());
        Assert.assertEquals(100, buffer.limit()); //写模式下，Buffer 的 limit 表示你最多能往 Buffer 里写多少数据。 写模式下，limit 等于 Buffer 的 capacity。
        if (buffer.limit() < src.length()) {
            buffer = ByteBuffer.allocate(src.length());
        }
        buffer.put(src.getBytes());
        Assert.assertEquals(15, buffer.position());//position 表示当前的位置
        buffer.flip(); //flip 方法将 Buffer 从写模式切换到读模式
        Assert.assertEquals(15, buffer.limit()); //读模式时，limit 表示你最多能读到多少数据。因此，当切换 Buffer 到读模式时，limit 会被设置成写模式下的 position 值。
        Assert.assertEquals(0, buffer.position());
        char tmp = (char) buffer.get();
        Assert.assertEquals('t', tmp);
        Assert.assertEquals(1, buffer.position());
        tmp = (char) buffer.get();
        Assert.assertEquals('h', tmp);
        buffer.rewind();
        Assert.assertEquals(0, buffer.position());
        tmp = (char) buffer.get();
        Assert.assertEquals('t', tmp);
        Assert.assertEquals(1, buffer.position());
        tmp = (char) buffer.get();
        Assert.assertEquals('h', tmp);
        buffer.clear();//clear() 方法，position 将被设回 0，limit 被设置成 capacity 的值。换句话说，Buffer 被清空了。
        Assert.assertEquals(0, buffer.position());
        Assert.assertEquals(100, buffer.limit());
        //clear() 方法, Buffer 中的数据并未清除，只是这些标记告诉我们可以从哪里开始往 Buffer 里写数据。
        tmp = (char) buffer.get();
        Assert.assertEquals('t', tmp);
        Assert.assertEquals(1, buffer.position());
        tmp = (char) buffer.get();
        Assert.assertEquals('h', tmp);
        buffer.compact();//compact() 方法将所有未读的数据拷贝到 Buffer 起始处。
        Assert.assertEquals(98, buffer.position()); // compact() 方法将 position 设到最后一个未读元素正后面。
        Assert.assertEquals(100, buffer.limit()); // compact() 方法将limit设置成 capacity
        buffer.position(0);
        tmp = (char) buffer.get();
        Assert.assertEquals('i', tmp);
        buffer.compact();
        buffer.position(0);
        buffer.mark(); // mark()标记Buffer中的一个特定position
        tmp = (char) buffer.get();
        Assert.assertEquals('s', tmp);
        buffer.reset(); // reset() 方法恢复到mark()标记的position
        tmp = (char) buffer.get();
        Assert.assertEquals('s', tmp);

        ByteBuffer buffer1 = ByteBuffer.allocate(100);
        ByteBuffer buffer2 = ByteBuffer.allocate(100);
        buffer1.put(src.getBytes());
        buffer2.put(src.getBytes());
        Assert.assertTrue(buffer1.equals(buffer2));
    }

    @Test
    public void fileChannel() {
        String filePath = NIODemo.class.getResource("/").getPath() + "nio/File1.txt";
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile(filePath, "rw");
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(48); //分配一个新的bytebuffer
            int bytesRead = 0;
            while ((bytesRead = inChannel.read(buf)) != -1) {
                buf.flip(); //反转Buffer，将 Buffer 从写模式切换到读模式。
                while (buf.hasRemaining()) {//判断是否有空间
                    System.out.print((char) buf.get()); //从buffer中读取字节
                }
                buf.clear();//清空
            }
            aFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void datagramChannelReceiveSend() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                int port = 9999;
                DatagramChannel channel = null;
                try {
                    channel = DatagramChannel.open();
                    channel.socket().bind(new InetSocketAddress(port));
                    String content = "";
                    ByteBuffer buf = ByteBuffer.allocate(48);
                    buf.clear();
                    countDownLatch.countDown();
                    channel.receive(buf);
                    buf.flip();
                    while (buf.hasRemaining()) {
                        buf.get(new byte[buf.limit()]);// read 1 byte at a time
                        content += new String(buf.array());
                    }
                    System.out.println(content.trim());
                    buf.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                String newData = "New String to write to file..." + System.currentTimeMillis();
                String serverIp = "127.0.0.1";
                int port = 9999;
                DatagramChannel channel = null;
                try {
                    countDownLatch.await();
                    Thread.sleep(5 * 1000);
                    channel = DatagramChannel.open();
                    channel.send(ByteBuffer.wrap(newData.getBytes()), new InetSocketAddress(serverIp, port));
//                    // connect之后，可使用write
//                    channel.connect(new InetSocketAddress(serverIp, port));
//                    channel.write(ByteBuffer.wrap(newData.getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        server.start();
        client.start();

        Thread.sleep(10 * 1000);
    }

    @Test
    public void socketChannel() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                int port = 9999;
                ServerSocketChannel serverSocketChannel = null;
                try {
                    serverSocketChannel = ServerSocketChannel.open();
                    serverSocketChannel.socket().bind(new InetSocketAddress(port));
                    String content = "";
                    ByteBuffer buf = ByteBuffer.allocate(48);
                    buf.clear();
                    countDownLatch.countDown();
                    SocketChannel socketChannel = null;
                    try {
                        while (socketChannel == null) {
                            socketChannel = serverSocketChannel.accept();
                            if (socketChannel != null) {
                                while (true) {
                                    socketChannel.read(buf);
                                    buf.flip();
                                    while (buf.hasRemaining()) {
                                        buf.get(new byte[buf.limit()]);
                                        content += new String(buf.array());
                                    }
                                    if (!(content == null || "".equals(content.trim()))) {
                                        System.out.println(content.trim());
                                    }
                                    buf.clear();
                                    System.out.println("-------");
                                }
                            }
                        }
                    } finally {
                        if (socketChannel != null) {
                            socketChannel.close();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (serverSocketChannel != null) {
                        try {
                            serverSocketChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        serverSocketChannel = null;
                    }
                }
            }
        });

        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                String newData = "New String to write to file..." + System.currentTimeMillis();
                String serverIp = "127.0.0.1";
                int port = 9999;
                SocketChannel socketChannel = null;
                try {
                    countDownLatch.await();
                    Thread.sleep(5 * 1000);
                    socketChannel = SocketChannel.open();
                    socketChannel.connect(new InetSocketAddress(serverIp, port));
                    System.out.println("--------1");
//                    while(!socketChannel.finishConnect()) {
                    socketChannel.write(ByteBuffer.wrap(newData.getBytes()));
                    System.out.println("--------2");
//                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (socketChannel != null) {
                        try {
                            socketChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        socketChannel = null;
                    }
                }

            }
        });

        server.start();
        client.start();

        Thread.sleep(10 * 1000);
    }


}
