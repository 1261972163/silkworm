package com.jengine.common.j2se.lang;

import junit.framework.Assert;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * 【Java引用】 Java包含4种引用，级别由高到低依次为：
 * 强引用  >  软引用  >  弱引用  >  虚引用
 * <p>
 * 【强引用】 强引用的对象只在其生命周期结束时或者显示通过设置null进行弱化后，才能被GC回收。
 * 是对象的一般状态。
 * <p>
 * 【软引用】 【只具有软引用】的对象，内存空间足够时，GC不回收；内存空间不足时，GC回收。
 * 常用于内存敏感的高速缓存，如网页缓存、图片缓存等。
 * <p>
 * 【弱引用】 【只具有弱引用】的对象，GC一旦发现就会对其进行回收，不管当前内存空间是否足够。
 * 当弱引用的指向对象变得弱引用可到达，该弱引用就会加入到引用队列。
 * 这一操作发生在对象析构或者垃圾回收真正发生之前。
 * 理论上，这个即将被回收的对象是可以在一个不符合规范的析构方法里面重新复活。但是这个弱引用会销毁。
 * <p>
 * 【虚引用】 如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。
 * （1）虚引用必须和引用队列 （ReferenceQueue）联合使用。
 * （2）当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。
 * （3）【和弱引用的区别】：虚引用只有在其指向的对象从内存中移除掉之后才会加入到引用队列中。
 * 其get方法一直返回null就是为了阻止其指向的几乎被销毁的对象重新复活。
 * （4）作用：唯一作用就是当其指向的对象被回收之后，自己被加入到引用队列，用作记录该引用指向的对象已被销毁。
 * 程序可以通过检测与虚引用关联的虚引用队列是否已经包含了指定的虚引用，从而了解虚引用的对象是否即将被回收。
 * <p>
 * 【总结】
 * <p>
 * 利用软引用、弱引用、虚引用可以让GC释放对象，有效避免OOM。
 * 如果希望尽可能减小程序在其声明周期中所占用的内存大小，可以灵活使用这些引用。
 * 需要注意的是，如果使用了这些引用就不能保留这些对象的强引用（强引用应该置null）。
 *
 * @author nouuid
 * @date 1/22/2017
 * @since 0.1.0
 */
public class ReferenceDemo {

    /**
     * @强引用
     * @测试 强引用不被回收导致内存溢出，通过设置null后被回收
     * @jvm测试情况设置 -Xms1m -Xmx1m
     */
    @Test
    public void strongReference() throws InterruptedException {
        List<Object> list = new LinkedList<Object>();
        try {
            int i = 1;
            while (i < 1000000) {
                System.out.println(i);
                Object o = new Object();   //  强引用
                list.add(o);
                i++;
            }
        } catch (OutOfMemoryError e) {
            System.out.println(e);
            Assert.assertFalse(false);
            for (Object o : list) {
                o = null;     // 帮助垃圾收集器回收此对象
            }
            Thread.sleep(5000); // 停顿一下，让jvm回收对象
            try {
                for (int i = 1; i < 11; i++) {
                    System.out.println("-" + i);
                    Object o = new Object();   //  强引用
                    list.add(o);
                }
            } catch (Exception e2) {
                Assert.assertFalse(true);
            }
            Assert.assertTrue(true);
            return;
        }
        Assert.assertFalse(true);

    }

    @Test
    public void strongReference2() {
        int i = 1;
        while (i < 1000000) {
            System.out.println(i);
            Object o = new Object();   //  强引用
            o = null;     // 帮助垃圾收集器回收此对象
            i++;
        }
        Assert.assertTrue(true);
    }

    /**
     * @软引用
     * @测试 测试内存不足时GC回收软引用
     * @jvm测试设置 -Xms1m -Xmx1m
     */
    @Test
    public void softReference() {
        // SoftReference在内存不足的时候自动被回收
        List<SoftReference> list2 = new LinkedList<SoftReference>();
        int i = 1;
        int errorCount = 0;
        while (i < 1000000) {
            try {
                System.out.println(i);
                SoftReference<String> softRef = new SoftReference<String>("" + i);
                list2.add(softRef);
                i++;
            } catch (OutOfMemoryError e) {
                System.out.println(e);
                try {
                    Thread.sleep(5000); // 停顿一下，让jvm回收对象
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                errorCount++;
                if (errorCount >= 2) {
                    Assert.assertTrue(true);
                    return;
                }
                continue;
            }
        }
        Assert.assertTrue(false);
    }


    /**
     * @弱引用
     * @测试 测试GC时回收弱引用
     */
    @Test
    public void weakReference() throws InterruptedException {
        // 用法一
        String s = new String(new byte[2 * 1024]);
        WeakReference<String> stringWeakReference = new WeakReference<String>(s);
        s = null; // 让s只具有弱引用
        System.gc();
        Thread.currentThread().sleep(2000);
        Assert.assertEquals(null, stringWeakReference.get());

        // 用法二
        WeakReference<String> stringWeakReference2 = new WeakReference<String>(new String(new byte[2 * 1024]));
        System.gc();
        Thread.currentThread().sleep(2000);
        Assert.assertEquals(null, stringWeakReference2.get());
    }

    /**
     * @虚引用
     * @测试 虚引用get一直返回null；虚引用回收后会被加入指定的引用队列
     */
    @Test
    public void phantomReference() {
        ReferenceQueue<String> rq = new ReferenceQueue<String>();
        String s = new String("1");
        PhantomReference pr = new PhantomReference(s, rq); //虚引用不能单独使用，必须关联引用队列。
        s = null;//切断强引用
        Assert.assertEquals(null, pr.get()); //试图取得虚引用对象
        System.gc();
        System.runFinalization();
        Assert.assertEquals(pr, rq.poll()); //取出引队列中的最先进入队列的引用与pr进行比较
    }
}
