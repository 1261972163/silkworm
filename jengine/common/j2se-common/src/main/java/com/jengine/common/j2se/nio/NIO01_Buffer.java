package com.jengine.common.j2se.nio;

import junit.framework.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Buffer有3个值很重要：
 *  capacity    容量大小
 *  limit       写模式下等于capacity，读模式下等于当前position的值
 *  position    当前位置
 *
 * Buffer的使用：
 * （1）flip() 方法，position归0，并设置limit为之前的position的值。
 * （2）rewind() 方法，position归0，limit不变。
 * （3）clear() 方法，position归0，limit 被设置成 capacity 的值。换句话说，Buffer 被清空了。
 * （4）compact() 方法，将所有未读的数据拷贝到 Buffer 起始处。
 * （5）mark()方法，标记当前的position。
 * （6）reset() 方法，恢复到mark的位置。
 * @author nouuid
 * @description
 * @date 5/11/17
 */
public class NIO01_Buffer {
    @Test
    public void buffer() {
        String src = "this is a test.";
        ByteBuffer buffer = ByteBuffer.allocate(100);
        Assert.assertEquals(100, buffer.capacity());
        //写模式下，Buffer 的 limit 表示你最多能往 Buffer 里写多少数据。 写模式下，limit 等于 Buffer 的 capacity。
        Assert.assertEquals(100, buffer.limit());
        if (buffer.limit() < src.length()) {
            buffer = ByteBuffer.allocate(src.length());
        }
        buffer.put(src.getBytes());
        Assert.assertEquals(15, buffer.position());//position 表示当前的位置
        buffer.flip(); //flip 方法将 Buffer 从写模式切换到读模式
        //读模式时，limit 表示你最多能读到多少数据。因此，当切换 Buffer 到读模式时，limit 会被设置成写模式下的 position 值。
        Assert.assertEquals(15, buffer.limit());
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
