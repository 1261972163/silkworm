package com.jengine.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel/DatagramChannel/SocketChannel/ServerSocketChannel
 *
 * @author bl07637
 * @date 9/20/2016
 * @since 0.1.0
 */
public class ChannelDemo {

    /**
     * 从文件中读写数据
     */
    public void fileChannel(String filePath) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile(filePath, "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48); //分配一个新的bytebuffer
        int bytesRead = inChannel.read(buf); //读文件内容到buffer
        while (bytesRead != -1) {
            System.out.print("read " + bytesRead + ": ");
            buf.flip(); //反转Buffer，将 Buffer 从写模式切换到读模式。
            while (buf.hasRemaining()) {//判断是否有空间
                System.out.print((char) buf.get()); //从buffer中读取字节
            }
            System.out.println();
            buf.clear();//清空
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

    /**
     * 能通过 UDP 读写网络中的数据。
     */
    public void DatagramChannel() {

    }

    /**
     * 能通过 TCP 读写网络中的数据。
     */
    public void SocketChannel() {

    }

    /**
     * 可以监听新进来的 TCP 连接，像 Web 服务器那样。对每一个新进来的连接都会创建一个 SocketChannel。
     */
    public void ServerSocketChannel() {

    }

}
