package com.jengine.j2se.net;

/**
 * content
 *
 * @author bl07637
 * @date 9/23/2016
 * @since 0.1.0
 */
public class SocketDemo {
    public void demo() {

        Thread server = new Thread(new Server());
        server.start();

        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread client = new Thread(new Client());
        client.start();


        try {
            Thread.sleep(1000 * 30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
