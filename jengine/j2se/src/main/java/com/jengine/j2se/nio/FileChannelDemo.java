package com.jengine.j2se.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * content
 *
 * @author bl07637
 * @date 9/23/2016
 * @since 0.1.0
 */
public class FileChannelDemo {

    String fromFilePath = FileChannelDemo.class.getResource("/").getPath() + "nio/FileChannelDemoFrom.txt";
    String toFilePath1 = FileChannelDemo.class.getResource("/").getPath() + "nio/FileChannelDemoTo1.txt";
    String toFilePath2 = FileChannelDemo.class.getResource("/").getPath() + "nio/FileChannelDemoTo2.txt";

    @Test
    public void transferFrom() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile(fromFilePath, "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile(toFilePath1, "rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, count);
    }

    @Test
    public void transferTo() throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile(fromFilePath, "rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile(toFilePath2, "rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        fromChannel.transferTo(position, count, toChannel);
    }
}
