package com.jengine.transport.protocol.myhttpclient;

import org.junit.Test;

import java.io.IOException;

/**
 * @author nouuid
 * @description
 * @date 8/11/17
 */
public class MyClientNoRunTest {

    @Test
    public void get() throws IOException {
        MyClient myClient = new MyClient();
        String res = myClient.execute("GET", "http://localhost:9998/helloworld", "");
        System.out.println(res);
    }

    @Test
    public void put() throws IOException {
        MyClient myClient = new MyClient();
        String res = myClient.execute("PUT", "http://localhost:9998/helloworld/put", "this is a test");
        System.out.println(res);
    }
}
