package com.jengine.j2se.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * 1. IO面临的问题：
 * （1）IO面向流的，每次只能从流中读取一个或多个字节，需要自己对读取的字节进行后续加工。
 * （2）IO是阻塞的，线程一旦调用read或write，就被阻塞住不能再进行任何操作，直到该read或write结束。
 *
 * 2. NIO解决的问题：
 * （1）NIO面向缓存区，读取字节后放入缓存。
 * （2）NIO非阻塞模式，可以让一个线程监听多个channel，调用时从中选择空闲的通道进行操作，防止线程阻塞。
 *
 * 3. NIO核心部分：
 * （1）Channel通道类似于流，区别在于：
 *      （a）通道可读可写，流都是单向的；
 *      （b）通道可异步读写，流是同步读写；
 *      （c）通道基于缓存区，流是字节。
 * （2）Buffer实质是一块内存，提供一系列的读写方便开发的接口。
 * （3）Selector是Channel的管理器，实现单线程操作多个Channel。
 *
 *
 * @author bl07637
 * @date 2/10/2017
 * @since 0.1.0
 */
public class NIO00_Demo {

    /**
     * Paths：将path字符串或字符串序列转换成一下Path对象
     * Path：Path对象
     */
    @Test
    public void path() throws IOException {
        System.out.println("user home : " + System.getProperty("user.home"));
        Path p2 = Paths.get(System.getProperty("user.home"), "download", "pdf");
        System.out.println("p2 : " + p2);
    }


}
