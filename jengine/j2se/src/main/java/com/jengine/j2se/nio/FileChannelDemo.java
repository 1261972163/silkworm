package com.jengine.j2se.nio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * FileChannel是用于连接文件的通道。通过文件通道可以读、写文件的数据。
 * FileChannel不可以设置为非阻塞模式，只能在阻塞模式下运行。
 *
 * @author bl07637
 * @date 9/23/2016
 * @since 0.1.0
 */
public class FileChannelDemo {

    public FileChannel open(String filePath) throws FileNotFoundException {
        // 在使用FileChannel前必须打开通道，打开一个文件通道需要通过输入/输出流或者RandomAccessFile
        RandomAccessFile aFile = new RandomAccessFile(filePath, "rw");
        FileChannel channel = aFile.getChannel();
        return channel;
    }

    public void read(FileChannel channel) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = channel.read(buf);
        while (bytesRead != -1) {
            buf.flip(); //反转Buffer，将 Buffer 从写模式切换到读模式。
            while (buf.hasRemaining()) {//判断是否有空间
                System.out.print((char) buf.get()); //从buffer中读取字节
            }
            buf.clear();//清空
            bytesRead = channel.read(buf);
        }
    }

    public void write(FileChannel channel, String newData) throws IOException {
        int length = newData.getBytes().length;
        int capacity = 10;
        ByteBuffer buf = ByteBuffer.allocate(capacity);
        byte[] bytes;
        for (int i=1; i<= length/capacity + 1; i++) {
            int start = (i-1)*capacity;
            int end = start + capacity;
            if (end>length) {
                end = length;
            }
            bytes = newData.substring(start, end).getBytes();
            buf.clear();
            buf.put(bytes);
            buf.flip();
            while(buf.hasRemaining()) {
                channel.write(buf);
            }
        }
    }

    public void close(FileChannel channel) throws IOException {
        channel.close();
    }

    public void transferFrom(String fromFilePath, String toFilePath1) throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile(fromFilePath, "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile(toFilePath1, "rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, count);
    }

    public void transferTo(String fromFilePath, String toFilePath2) throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile(fromFilePath, "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile(toFilePath2, "rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        fromChannel.transferTo(position, count, toChannel);
    }
}
