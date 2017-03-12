package com.jengine.j2se.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Created by nouuid on 2015/6/25.
 */
public class TcpClient {

    public static void main(String[] args) {
        TcpClient client = new TcpClient();
        client.start();
    }

    public void start() {
    	System.out.println("Client is starting...");
        Socket socket = null;
        String inContent = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            socket = new Socket("localhost", 4441);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                out.writeUTF("         Client: My date is " + new Date() + ". What's your date?");
                inContent = in.readUTF();
                if (inContent != null) {
                    System.out.println(inContent);
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


//                if (inContent != null) {
//                    System.out.println(inContent);
//                    outContent = "         Client: My date is " + new Date() + ". What's your date?";
//                    out.writeUTF(outContent);
//                }else {
//                	out.writeUTF(outContent);
//                }
//                try {
//					Thread.sleep(1000*1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
            }

        } catch (IOException e) {
            System.out.println("can't connect");
        } finally {
            if (out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("client over");
    }
}
