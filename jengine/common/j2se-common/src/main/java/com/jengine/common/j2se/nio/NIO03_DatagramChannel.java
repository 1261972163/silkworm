package com.jengine.common.j2se.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.CountDownLatch;

/**
 *
 * DatagramChannel用于UDP的数据读写。
 *
 * DatagramChannel的使用：
 * （1）DatagramChannel是nio中处理UDP的类,udp是无连接的。
 * （2）DatagramChannel.receive(ByteBuffer dst)
 * （3）DatagramChannel.send(ByteBuffer src, SocketAddress target)。
 *
 * @author nouuid
 * @description
 * @date 5/11/17
 */
public class NIO03_DatagramChannel {
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
}
