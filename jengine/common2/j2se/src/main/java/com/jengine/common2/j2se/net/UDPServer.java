package com.jengine.common2.j2se.net;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;

/**
 * Created by nouuid on 2015/6/25.
 */
public class UDPServer {

    public static void main(String[] args) {
        UDPServer server = new UDPServer();
        server.start();
    }

    public void start() {
        System.out.println("UDPServer is starting...");
        DatagramSocket socket = null;
        LinkedList<Processor> processors = new LinkedList<Processor>();
        try {
            socket = new DatagramSocket(10010);
            System.out.println("Server is started.");
            while (true) {
                byte[] data = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);
                Processor processor = new Processor(socket, packet);
                processors.add(processor);
                processor.start();

            }
        } catch (IOException e1) {
            System.out.println("ERROR:" + e1);
        } finally {
            for (Processor processor : processors) {
                processor.stop();
            }
            if (socket != null) {
                socket.close();
            }
            System.out.println("server over");
        }
    }

    private class Processor {

        private DatagramSocket socket;
        private DatagramPacket packet;
        private boolean flag = true;

        public Processor(DatagramSocket socket, DatagramPacket packet) {
            this.socket = socket;
            this.packet = packet;
        }

        public void start() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Processor[" + Thread.currentThread().getId() + "]");
                    // in
                    String info = new String(packet.getData(), 0, packet.getData().length);
                    System.out.println(info);
                    // out
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    byte[] data2 = ("Welcome " +  info).getBytes();
                    DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
                    try {
                        socket.send(packet2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        public void stop() {
            flag = false;
        }
    }

}
