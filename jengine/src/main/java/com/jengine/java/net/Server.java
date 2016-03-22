package com.jengine.java.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by nouuid on 2015/6/25.
 */
public class Server implements Runnable {
	
    ServerSocket server = null;
    Socket socket = null;
    String inContent = null;
    String outContent = null;
    DataOutputStream out = null;
    DataInputStream in = null;

    public void run() {
    	System.out.println("server start");
        try {
        	server = new ServerSocket(4441);
        	socket = server.accept();
            while (true) {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                inContent = in.readUTF();
                if (inContent != null) {
                    System.out.println(inContent);
                    outContent = "Server: My date is " + new Date() + ". What's your date?";
                    out.writeUTF(outContent);
                }else {
                	out.writeUTF(outContent);
                }
                try {
					Thread.sleep(1000*1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        } catch (IOException e1) {
            System.out.println("ERROR:" + e1);
        }
        System.out.println("server over");
    }

    public void stop() {
        try {
            out.close();
            in.close();
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
