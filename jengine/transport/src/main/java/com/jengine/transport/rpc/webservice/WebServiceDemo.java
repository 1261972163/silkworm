package com.jengine.transport.rpc.webservice;

import junit.framework.Assert;
import org.junit.Test;

/**
 * content
 *
 * @author bl07637
 * @date 9/12/2016
 * @since 0.1.0
 */
public class WebServiceDemo {

    @Test
    public void test() throws InterruptedException {
        MyServer myServer = new MyServer();
        MyClient myClient = new MyClient();

        myServer.start();
        Thread.sleep(3*1000);
        int result = myClient.invoke();
        Thread.sleep(3*1000);
        Assert.assertEquals(25, result);
    }
}
