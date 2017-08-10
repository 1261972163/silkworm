package com.jengine.transport.protocol.myhttpclient;

import com.jengine.transport.protocol.http.MyClient;

/**
 * content
 *
 * @author bl07637
 * @date 8/10/2017
 * @since 0.1.0
 */
public class MyClientFactory {

    public MyClient getObject() {
        MyClient client = new MyClient();
        return client;
    }
}
