package com.jengine.transport.nioserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author nouuid
 * @description
 * @date 9/28/16
 */
public interface IMessageReader {

    public void init(MessageBuffer readMessageBuffer);

    public void read(Socket socket, ByteBuffer byteBuffer) throws IOException;

    public List<Message> getMessages();



}
