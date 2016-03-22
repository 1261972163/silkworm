package com.jengine.java.net;

import org.junit.Test;

/**
 * Created by nouuid on 2015/6/25.
 */
public class NetworkUtilTest {

    @Test
    public void test() {
//        networkTest();
        socketTest();
    }

    public void networkTest() {
        System.out.println(NetworkUtil.getHostIp());
        StringBuffer content = NetworkUtil.readByURL("http://www.weixueyuan.net/uploads/code/java/rumen/13-2.txt");
        System.out.println(content);
    }

    public void socketTest() {

    	Thread server = new Thread(new Server());
        server.start();

        try {
            Thread.sleep(1000*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread client = new Thread(new Client());
        client.start();
        
        
        try {
            Thread.sleep(1000*30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
