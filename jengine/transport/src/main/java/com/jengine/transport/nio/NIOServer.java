package com.jengine.transport.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author nouuid
 * @description
 * @date 3/12/17
 */
public class NIOServer extends Thread {

    private static final Logger log = Logger.getLogger(NIOServer.class.getName());

    private InetSocketAddress inetSocketAddress;
    private NIOServerHandler nioServerHandler = null;

    public NIOServer(NIOServerHandler nioServerHandler, String hostname, int port) {
        this.nioServerHandler = nioServerHandler;
        inetSocketAddress = new InetSocketAddress(hostname, port);
    }

    @Override
    public void run() {
        try {
            Selector selector = Selector.open(); // 打开选择器
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); // 打开通道
            serverSocketChannel.configureBlocking(false); // 非阻塞
            serverSocketChannel.socket().bind(inetSocketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 向通道注册选择器和对应事件标识
            log.info("NIOServer is started.");
            while (true) { // 轮询
                int nKeys = selector.select();
                if (nKeys > 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectedKeys.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        if (key.isAcceptable()) {
                            log.info("Server: SelectionKey is acceptable.");
                            nioServerHandler.handleAccept(key);
                        } else if (key.isReadable()) {
                            log.info("Server: SelectionKey is readable.");
                            nioServerHandler.handleRead(key);
                        } else if (key.isWritable()) {
                            log.info("Server: SelectionKey is writable.");
                            nioServerHandler.handleWrite(key);
                        }
                        it.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        NIOServer server = new NIOServer(new NIOServerHandlerImpl(), "localhost", 1400);
        server.start();
    }
}
