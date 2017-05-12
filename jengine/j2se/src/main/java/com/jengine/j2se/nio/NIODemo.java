package com.jengine.j2se.nio;

import junit.framework.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;

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
 * 4. Buffer的使用：
 * （1）flip() 方法，position归0，并设置limit为之前的position的值。
 * （2）rewind() 方法，position归0，limit不变。
 * （3）clear() 方法，position归0，limit 被设置成 capacity 的值。换句话说，Buffer 被清空了。
 * （4）compact() 方法，将所有未读的数据拷贝到 Buffer 起始处。
 * （5）mark()方法，标记当前的position。
 * （6）reset() 方法，恢复到mark的位置。
 *
 * 5. Channel的使用：
 * （1）
 * （2）
 * （3）SocketChannel用于TCP的数据读写。
 * （4）ServerSocketChannel允许我们监听TCP链接请求，每个请求会创建会一个SocketChannel.
 *
 * 6.
 *
 * @author bl07637
 * @date 2/10/2017
 * @since 0.1.0
 */
public class NIODemo {










}
