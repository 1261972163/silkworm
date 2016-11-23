package com.jengine.j2se.lang;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * UrlClasLoader加载指定类或者jar
 *
 * 本实例需要在e:/test下准备com.jengine.Test类，里面有个方法printString，参数为两个String，输出结果为两个String的连接
 *
 * @author bl07637
 * @date 11/22/2016
 * @since 0.1.0
 */
public class UrlClasLoaderDemo {
    public static void main(String[] args) throws Exception {
        /*动态加载指定类*/
        File file = new File("e:/test");//类路径(包文件上一层)
        URL url = file.toURI().toURL();
        ClassLoader loader = new URLClassLoader(new URL[]{url});//创建类加载器
        Class<?> cls = loader.loadClass("com.jengine.Test");//加载指定类，注意一定要带上类的包名
        Object obj = cls.newInstance();//初始化一个实例
        Method method = cls.getMethod("printString", String.class, String.class);//方法名和对应的参数类型
        Object o = method.invoke(obj, "123", "456");//调用得到的上边的方法method
        System.out.println(String.valueOf(o));//输出"123456"

        /*动态加载指定jar包调用其中某个类的方法*/
        file = new File("D:/test/commons-lang3.jar");//jar包的路径
        url = file.toURI().toURL();
        loader = new URLClassLoader(new URL[]{url});//创建类加载器
        cls = loader.loadClass("org.apache.commons.lang3.StringUtils");//加载指定类，注意一定要带上类的包名
        method = cls.getMethod("center", String.class, int.class, String.class);//方法名和对应的各个参数的类型
        o = method.invoke(null, "123", Integer.valueOf(10), "0");//调用得到的上边的方法method(静态方法，第一个参数可以为null)
        System.out.println(String.valueOf(o));//输出"000123000","123"字符串两边各加3个"0"字符串
    }
}
