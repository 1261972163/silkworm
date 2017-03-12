package com.jengine.j2se.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by nouuid on 2015/6/25.
 */
public class TcpServer {

    public static void main(String[] args) {
        TcpServer server = new TcpServer();
        server.start();
    }

    public void start() {
    	System.out.println("Server is starting...");
        ServerSocket server = null;
        LinkedList<SocketProcessor> socketProcessors = new LinkedList<SocketProcessor>();
        try {
        	server = new ServerSocket(4441);
            System.out.println("Server is started.");
            while (true) {
                Socket socket = server.accept();
                if (socket==null) {
                    continue;
                }
                SocketProcessor socketProcessor = new SocketProcessor(socket);
                socketProcessor.start();

            }
        } catch (IOException e1) {
            System.out.println("ERROR:" + e1);
        } finally {
            for (SocketProcessor socketProcessor : socketProcessors) {
                socketProcessor.stop();
            }
            if (server!=null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("server over");
        }
    }

    private class SocketProcessor {

        private Socket socket;
        private boolean flag = true;

        public SocketProcessor(Socket socket) {
            this.socket = socket;
        }

        public void start() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("SocketProcessor[" + Thread.currentThread().getId() + "]");
                    DataInputStream in = null;
                    DataOutputStream out = null;
                    String inContent = null;
                    String outContent = null;
                    try {
                        while (flag) {
                            in = new DataInputStream(socket.getInputStream());
                            out = new DataOutputStream(socket.getOutputStream());
                            inContent = in.readUTF();
                            if (inContent != null) {
                                System.out.println(inContent);
                                outContent = "Server: My date is " + new Date() + ".";
                                out.writeUTF(outContent);
                            } else {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                out.writeUTF(outContent);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
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
                }
            });
            thread.start();
        }

        public void stop() {
            flag = false;
        }
    }

}
