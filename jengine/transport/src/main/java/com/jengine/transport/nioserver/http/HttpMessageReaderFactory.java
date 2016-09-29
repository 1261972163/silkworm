package com.jengine.transport.nioserver.http;


import com.jengine.transport.nioserver.IMessageReader;
import com.jengine.transport.nioserver.IMessageReaderFactory;

/**
 * @author nouuid
 * @description
 * @date 9/28/16
 */
public class HttpMessageReaderFactory implements IMessageReaderFactory {

    public HttpMessageReaderFactory() {
    }

    @Override
    public IMessageReader createMessageReader() {
        return new HttpMessageReader();
    }
}
