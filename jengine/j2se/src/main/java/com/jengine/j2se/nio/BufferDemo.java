package com.jengine.j2se.nio;

import junit.framework.Assert;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Java NIO 中的 Buffer 用于和 NIO 通道进行交互
 * ByteBuffer/MappedByteBuffer/CharBuffer/DoubleBuffer/FloatBuffer/IntBuffer/LongBuffer/ShortBuffer
 *
 * @author bl07637
 * @date 9/20/2016
 * @since 0.1.0
 */
public class BufferDemo {

    @org.junit.Test
    public void demo() throws IOException {
        String src = "this is a test.";
        ByteBuffer buffer = ByteBuffer.allocate(100);
        Assert.assertEquals(100, buffer.capacity());
        Assert.assertEquals(100, buffer.limit()); //写模式下，Buffer 的 limit 表示你最多能往 Buffer 里写多少数据。 写模式下，limit 等于 Buffer 的 capacity。
        if (buffer.limit()<src.length()) {
            buffer = ByteBuffer.allocate(src.length());
        }
        buffer.put(src.getBytes());
        Assert.assertEquals(15, buffer.position());//position 表示当前的位置
        buffer.flip(); //flip 方法将 Buffer 从写模式切换到读模式
        Assert.assertEquals(15, buffer.limit()); //读模式时，limit 表示你最多能读到多少数据。因此，当切换 Buffer 到读模式时，limit 会被设置成写模式下的 position 值。
        Assert.assertEquals(0, buffer.position());
        char tmp = (char) buffer.get();
        Assert.assertEquals('t', tmp);
        Assert.assertEquals(1, buffer.position());
        tmp = (char) buffer.get();
        Assert.assertEquals('h', tmp);
        buffer.rewind();
        Assert.assertEquals(0, buffer.position());
        tmp = (char) buffer.get();
        Assert.assertEquals('t', tmp);
        Assert.assertEquals(1, buffer.position());
        tmp = (char) buffer.get();
        Assert.assertEquals('h', tmp);
        buffer.clear();//clear() 方法，position 将被设回 0，limit 被设置成 capacity 的值。换句话说，Buffer 被清空了。
        Assert.assertEquals(0, buffer.position());
        Assert.assertEquals(100, buffer.limit());
        //clear() 方法, Buffer 中的数据并未清除，只是这些标记告诉我们可以从哪里开始往 Buffer 里写数据。
        tmp = (char) buffer.get();
        Assert.assertEquals('t', tmp);
        Assert.assertEquals(1, buffer.position());
        tmp = (char) buffer.get();
        Assert.assertEquals('h', tmp);
        buffer.compact();//compact() 方法将所有未读的数据拷贝到 Buffer 起始处。
        Assert.assertEquals(98, buffer.position()); // compact() 方法将 position 设到最后一个未读元素正后面。
        Assert.assertEquals(100, buffer.limit()); // compact() 方法将limit设置成 capacity
        buffer.position(0);
        tmp = (char) buffer.get();
        Assert.assertEquals('i', tmp);
        buffer.compact();
        buffer.position(0);
        buffer.mark(); // mark()标记Buffer中的一个特定position
        tmp = (char) buffer.get();
        Assert.assertEquals('s', tmp);
        buffer.reset(); // reset() 方法恢复到mark()标记的position
        tmp = (char) buffer.get();
        Assert.assertEquals('s', tmp);

        ByteBuffer buffer1 = ByteBuffer.allocate(100);
        ByteBuffer buffer2 = ByteBuffer.allocate(100);
        buffer1.put(src.getBytes());
        buffer2.put(src.getBytes());
        Assert.assertTrue(buffer1.equals(buffer2));
    }
}
