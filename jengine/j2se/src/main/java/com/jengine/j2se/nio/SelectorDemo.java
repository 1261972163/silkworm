package com.jengine.j2se.nio;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author nouuid
 * @description
 * @date 9/24/16
 */
public class SelectorDemo {

    public void create() throws IOException {
        SocketChannel channel = null;

        Selector selector = Selector.open();

        channel.configureBlocking(false);
        SelectionKey key1 = channel.register(selector, SelectionKey.OP_READ);
        key1.attach("key1");
        while(true) {
            int readyChannels = selector.select();
            if(readyChannels == 0) continue;
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                System.out.println("### " + key.attachment());
                if(key.isAcceptable()) {
                    System.out.println("a connection was accepted by a ServerSocketChannel.");
                } else if (key.isConnectable()) {
                    System.out.println("a connection was established with a remote server.");
                } else if (key.isReadable()) {
                    System.out.println("a channel is ready for reading.");
                } else if (key.isWritable()) {
                    System.out.println("a channel is ready for writing.");
                }
                keyIterator.remove();
            }
        }
    }

    /**
     * select()方法的返回值是一个int整形，代表有多少channel处于就绪了。
     * @param selector
     * @throws IOException
     */
    private void select(Selector selector) throws IOException {
        int readyNum = selector.select();

    }
}
