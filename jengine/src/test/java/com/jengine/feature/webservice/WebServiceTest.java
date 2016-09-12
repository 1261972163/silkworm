package com.jengine.feature.webservice;

import org.junit.Test;

/**
 * content
 *
 * @author bl07637
 * @date 9/12/2016
 * @since 0.1.0
 */
public class WebServiceTest {

    @Test
    public void test() throws InterruptedException {
        MyServer myServer = new MyServer();
        MyClient myClient = new MyClient();

        myServer.start();
        Thread.sleep(5*1000);
        myClient.start();
    }
}
