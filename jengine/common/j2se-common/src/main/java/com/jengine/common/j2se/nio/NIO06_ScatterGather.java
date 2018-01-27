package com.jengine.common.j2se.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Scattering Reads 是指数据从一个 channel 读取到多个 buffer 中。
 * Gathering Writes 是指数据从多个 buffer 写入到同一个 channel。
 *
 * @author nouuid
 * @date 9/22/2016
 * @since 0.1.0
 */
public class NIO06_ScatterGather {

    @Test
    public void scatter() throws IOException {
        String filePath = NIO06_ScatterGather.class.getResource("/").getPath() + "nio/data2.txt";
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
        FileChannel channel = randomAccessFile.getChannel();

        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);
        // 数据从一个 channel 读取到多个 buffer 中
        ByteBuffer[] bufferArray = { header, body };
        long n = channel.read(bufferArray);
        System.out.println("total:" + n);

        header.flip();
        //head缓冲区中的数据:qw
        System.out.println("header buffer:" + new String(header.array()));

        body.flip();
        //body缓冲区中的数据:ertyuiop
        System.out.println("body buffer:" + new String(body.array()));
        randomAccessFile.close();
        channel.close();
    }

    @Test
    public void gather() throws IOException {
        String filePath = NIO06_ScatterGather.class.getResource("/").getPath() + "nio/data3.txt";
        RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw");
        FileChannel channel = randomAccessFile.getChannel();

        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);
        header.put("header".getBytes());
        body.put("body".getBytes());
        ByteBuffer[] bufferArray = { header, body };
        header.flip();
        body.flip();
        // 数据从多个 buffer 写入到同一个 channel。
        long n = channel.write(bufferArray);
        System.out.println("total:" + n);
        randomAccessFile.close();
        channel.close();
    }
}
