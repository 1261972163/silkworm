package com.jengine.j2se.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Created by nouuid on 2015/6/25.
 */
public class Client implements Runnable {
	
    Socket socket = null;
    String inContent = null;
    String outContent = null;
    DataInputStream in = null;
    DataOutputStream out = null;

    public void run() {
    	System.out.println("client start");
        try {
            socket = new Socket("localhost", 4441);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("         Client: My date is " + new Date() + ". What's your date?");
            while (true) {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                inContent = in.readUTF();
                if (inContent != null) {
                    System.out.println(inContent);
                    outContent = "         Client: My date is " + new Date() + ". What's your date?";
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

        } catch (IOException e) {
            System.out.println("can't connect");
        }
        System.out.println("client over");
    }

    public void stop() {

        try {
        	out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
