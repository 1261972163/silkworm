package com.jengine.common.j2se.io;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 1. 一个流可以理解为一个数据的序列。输入流表示把数据从源读到内存，输出流表示将内存数据写到目标存储上。
 * 2. Java.io 包几乎包含了所有操作输入、输出需要的类。大概有将近 80 个类，大概可以分成四组，分别是：
 *      字节操作：【InputStream】 和 【OutputStream】
 *      字符操作：【Writer】 和 【Reader】
 *      磁盘操作：【File】
 *      网络操作：【Socket】
 * 3. 读写文件
 *
 * @author bl07637
 * @date 5/11/2017
 * @since 0.1.0
 */
public class IO01_Demo {

    @Test
    public void byteArrayInputOutputStream() throws IOException {
        byte[] source = "abcdefg".getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(source);
        int c;
        // 1. 读出来的是0~255的ASCII值，-1表示没有byte数据了
        while (( c= bais.read())!= -1) {
            System.out.print((char) c);
        }
        System.out.println();

        // 创建一个大小为n字节的缓冲区
        ByteArrayOutputStream baos = new ByteArrayOutputStream(32);
        baos.write(source);
        byte b [] = baos.toByteArray();
        System.out.println(new String(b));
    }

    @Test
    public void bufferdInputOutputStream() throws IOException, InterruptedException {
        byte[] source = "abcdefg".getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(source);

        BufferedInputStream bis = new BufferedInputStream(bais);
        // bis已经拷贝了bais，所以bais删除后仍然没有影响
        int c;
        while (( c= bis.read())!= -1) {
            System.out.print((char) c);
        }


//        byte[] source = "abcdefg".getBytes();
//        ByteArrayInputStream bais = new ByteArrayInputStream(source);
//        int c;
//        // 1. 读出来的是0~255的ASCII值，-1表示没有byte数据了
//        while (( c= bais.read())!= -1) {
//            System.out.print((char) c);
//        }
//        System.out.println();
//
//        // 创建一个大小为n字节的缓冲区
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(32);
//        baos.write(source);
//        byte b [] = baos.toByteArray();
//        System.out.println(new String(b));
    }
}
