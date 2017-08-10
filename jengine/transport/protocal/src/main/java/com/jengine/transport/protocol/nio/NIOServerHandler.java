package com.jengine.transport.protocol.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @author nouuid
 * @description
 * @date 3/12/17
 */
public interface NIOServerHandler {
    /**
     * 处理{@link SelectionKey#OP_ACCEPT}事件
     * @param key
     * @throws IOException
     */
    void handleAccept(SelectionKey key) throws IOException;
    /**
     * 处理{@link SelectionKey#OP_READ}事件
     * @param key
     * @throws IOException
     */
    void handleRead(SelectionKey key) throws IOException;
    /**
     * 处理{@link SelectionKey#OP_WRITE}事件
     * @param key
     * @throws IOException
     */
    void handleWrite(SelectionKey key) throws IOException;
}
