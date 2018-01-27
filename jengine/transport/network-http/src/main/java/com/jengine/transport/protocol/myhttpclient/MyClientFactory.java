package com.jengine.transport.protocol.myhttpclient;

/**
 * content
 *
 * @author nouuid
 * @date 8/10/2017
 * @since 0.1.0
 */
public class MyClientFactory {

    public MyClient getObject() {
        MyClient client = new MyClient();
        return client;
    }
}
