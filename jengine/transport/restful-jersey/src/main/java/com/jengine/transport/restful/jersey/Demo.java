package com.jengine.transport.restful.jersey;

import com.jengine.transport.protocol.myhttpclient.MyClient;
import org.junit.Test;

import java.io.IOException;

/**
 * content
 *
 * @author nouuid
 * @date 10/25/2016
 * @since 0.1.0
 */
public class Demo {

    @Test
    public void server() throws InterruptedException, IOException {
        JerseyGrizzlyServer jerseyGrizzlyServer = new JerseyGrizzlyServer("localhost", 9998);
        jerseyGrizzlyServer.start();
        Thread.sleep(30 * 60 * 1000);
        jerseyGrizzlyServer.stop();
    }

    @Test
    public void clientGet() throws IOException {
        MyClient myClient = new MyClient();
        String res = myClient.execute("GET", "http://localhost:9998/helloworld", "");
        System.out.println(res);
    }

    @Test
    public void clientPut() throws IOException {
        MyClient myClient = new MyClient();
        String res = myClient.execute("PUT", "http://localhost:9998/helloworld/put", "this is a test");
        System.out.println(res);
    }


}
