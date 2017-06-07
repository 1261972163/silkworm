package com.jengine.transport.protocol.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

/**
 * @author nouuid
 * @description
 * @date 3/12/17
 */
public class NIOClient {

    private static final Logger log = Logger.getLogger(NIOClient.class.getName());
    private InetSocketAddress inetSocketAddress;

    public NIOClient(String hostname, int port) {
        inetSocketAddress = new InetSocketAddress(hostname, port);
    }

    /**
     * 发送请求数据
     * @param requestData
     */
    public String send(String requestData) {
        String res = null;
        try {
            SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
            socketChannel.configureBlocking(false);
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            socketChannel.write(ByteBuffer.wrap(requestData.getBytes()));
            while (true) {
                byteBuffer.clear();
                int readBytes = socketChannel.read(byteBuffer);
                if (readBytes > 0) {
                    byteBuffer.flip();
                    res = new String(byteBuffer.array(), 0, readBytes);
                    socketChannel.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        String hostname = "localhost";
        String requestData = "Admin";
        int port = 1400;
        String res = new NIOClient(hostname, port).send(requestData);
        System.out.println(res);
    }
}