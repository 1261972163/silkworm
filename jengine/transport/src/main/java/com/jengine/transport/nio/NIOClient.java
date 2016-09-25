package com.jengine.transport.nio;

import org.apache.commons.lang.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author nouuid
 * @description
 * @date 9/24/16
 */
public class NIOClient implements Closeable {
    //通道管理器
    private Selector selector;
    private String serverIP;
    private int port;

    public NIOClient(String serverIP, int port) {
        this.serverIP = serverIP;
        this.port = port;
    }

    public void start() throws IOException {
        initClient(serverIP, port);
        listen();
    }

    @Override
    public void close() throws IOException {
        selector.close();
    }

    /**
     * 获得一个Socket通道，并对该通道做一些初始化的工作
     *
     * @param ip   连接的服务器的ip
     * @param port 连接的服务器的端口号
     * @throws IOException
     */
    public void initClient(String ip, int port) throws IOException {
        this.selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(ip, port)); //初始连接，执行finishConnect()才能完成连接
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
     *
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public void listen() throws IOException {
        while (true) {
            // 选择一组可以进行I/O操作的事件，放在selector中,客户端的该方法不会阻塞，
            //这里和服务端的方法不一样，查看api注释可以知道，当至少一个通道被选中时，
            //selector的wakeup方法被调用，方法返回，而对于客户端来说，通道一直是被选中的
            selector.select();
            Iterator ite = this.selector.selectedKeys().iterator();// 获得selector中选中的项的迭代器
            while (ite.hasNext()) {
                SelectionKey key = (SelectionKey) ite.next();
                ite.remove(); // 删除已选的key,以防重复处理
                if (key.isConnectable()) { //连接事件
                    SocketChannel serverChannel = (SocketChannel) key.channel();
                    if (serverChannel.isConnectionPending()) { // 如果正在连接，则完成连接
                        serverChannel.finishConnect();
                    }
                    serverChannel.configureBlocking(false);
                    serverChannel.write(ByteBuffer.wrap(new String("this is client.").getBytes()));//向服务端发送信息
                    //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
                    serverChannel.register(this.selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {// 可读的事件
                    read(key);
                }
            }
        }
    }

    /**
     * 处理读取服务端发来的信息 的事件
     *
     * @param key
     * @throws IOException
     */
    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        if (StringUtils.isBlank(msg)) {
            System.out.println("empty");
        }
        System.out.println("客户端收到信息："+msg);
//        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
//        channel.write(outBuffer);
    }
}
