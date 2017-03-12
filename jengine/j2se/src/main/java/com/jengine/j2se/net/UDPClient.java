package com.jengine.j2se.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Date;

/**
 * Created by nouuid on 2015/6/25.
 */
public class UDPClient {

    public static void main(String[] args) {
        UDPClient client = new UDPClient();
        client.start();
    }

    public void start() {
        System.out.println("TCPClient is starting...");
        InetAddress address = null;
        try {
            address = InetAddress.getByName("localhost");
            int port = 10010;
            int i = 1;
            while (true) {
                // out
                byte[] data = ("[" + 2 + "]admin" + i).getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                DatagramSocket socket = new DatagramSocket();
                socket.send(packet);

                // in
                byte[] data2 = new byte[1024];
                DatagramPacket packet2 = new DatagramPacket(data2,data2.length);
                socket.receive(packet2);
                String reply = new String(data2,0, packet2.getLength());
                System.out.println(reply);

                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("client over");
    }
}
