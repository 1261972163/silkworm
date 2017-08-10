package com.jengine.common2.j2se.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author nouuid
 * @description
 * @date 5/17/17
 */
public class NIO07_MappedByteBuffer {

    @Test
    public void byteReadWrite() throws IOException {
        // read
        String src = "/Users/weiyang/Downloads/MySQL_transfer.ppt";;
        ByteBuffer byteBuf = ByteBuffer.allocate(1024 * 14 * 1024);
        FileInputStream fis = new FileInputStream(src);
        FileChannel fc = fis.getChannel();
        long start = System.currentTimeMillis();
        fc.read(byteBuf);
        long end = System.currentTimeMillis();
        System.out.println("size="+fc.size()/1024 + ", Read time :" + (end - start) + "ms");

        // write
        String des = NIO02_FileChannel.class.getResource("/").getPath() + "nio/des";
        byte[] bbb = new byte[14 * 1024 * 1024];
        FileOutputStream fos = new FileOutputStream(des);
        start = System.currentTimeMillis();
        fos.write(bbb);
        end = System.currentTimeMillis();
        System.out.println("size="+fc.size()/1024 + ", Write time :" + (end - start) + "ms");

        fos.flush();
        fc.close();
        fis.close();
    }

    @Test
    public void mappedByteBuffferReadWrite() throws IOException {

        // read
        String src = "/Users/weiyang/Downloads/MySQL_transfer.ppt";;
        RandomAccessFile fis = new RandomAccessFile(src, "rw");
        FileChannel fc = fis.getChannel();
        long start = System.currentTimeMillis();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        long end = System.currentTimeMillis();
        System.out.println("size="+fc.size()/1024 + ", Read time :" + (end - start) + "ms");

        // write
        String des = NIO02_FileChannel.class.getResource("/").getPath() + "nio/des2";
        RandomAccessFile fos = new RandomAccessFile(des, "rw");
        FileChannel fc2 = fos.getChannel();
        MappedByteBuffer mbb2 = fc2.map(FileChannel.MapMode.READ_WRITE, 0, 14 * 1024 * 1024);
        byte[] bbb = new byte[14 * 1024 * 1024];
        start = System.currentTimeMillis();
        mbb2.put(bbb);
        end = System.currentTimeMillis();
        System.out.println("size="+fc.size()/1024 + ", Write time :" + (end - start) + "ms");

        fos.close();
        fc.close();
        fis.close();
    }
}
