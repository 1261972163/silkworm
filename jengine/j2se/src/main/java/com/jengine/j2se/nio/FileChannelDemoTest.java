package com.jengine.j2se.nio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author nouuid
 * @description
 * @date 9/24/16
 */
public class FileChannelDemoTest {
    FileChannelDemo fileChannelDemo = new FileChannelDemo();

    @Test
    public void writeRead() throws IOException {
        String filePath = FileChannelDemoTest.class.getResource("/").getPath() + "nio/FileChannelDemo1.txt";
        FileChannel fileChannel = fileChannelDemo.open(filePath);

        String newData = "New String to write to file..." + System.currentTimeMillis();
        fileChannelDemo.write(fileChannel, newData);
        fileChannel = fileChannelDemo.open(filePath);
        fileChannelDemo.read(fileChannel);
        fileChannelDemo.close(fileChannel);
    }

    @Test
    public void transfer() throws Exception {
        String fromFilePath = FileChannelDemo.class.getResource("/").getPath() + "nio/FileChannelDemoFrom.txt";
        String toFilePath1 = FileChannelDemo.class.getResource("/").getPath() + "nio/FileChannelDemoTo1.txt";
        String toFilePath2 = FileChannelDemo.class.getResource("/").getPath() + "nio/FileChannelDemoTo2.txt";

        fileChannelDemo.transferFrom(fromFilePath, toFilePath1);
        fileChannelDemo.transferTo(fromFilePath, toFilePath2);
    }
}
