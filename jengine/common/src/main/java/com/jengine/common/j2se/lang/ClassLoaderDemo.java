package com.jengine.common.j2se.lang;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * content
 *
 * @author bl07637
 * @date 11/9/2016
 * @since 0.1.0
 */
public class ClassLoaderDemo {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        //loader1的父加载器为系统类加载器
        MyClassLoader loader1 = new MyClassLoader("loader1");
        //加载类，得到Class对象
        Class<?> clazz = loader1.loadClass("com.jengine.j2se.lang.Animal");
        //得到类的实例
        Animal animal = (Animal) clazz.newInstance();
        animal.say();

        //loader2的父加载器为loader1
        MyClassLoader loader2 = new MyClassLoader(loader1, "loader2");
        //loader3的父加载器为根类加载器
        MyClassLoader loader3 = new MyClassLoader(null, "loader3");
    }
}

class Animal {
    public void say() {
        System.out.println("hello world!");
    }
}

class MyClassLoader extends ClassLoader {
    //类加载器的名称
    private String name;
    //类存放的路径
    private String path = "/opt/libs/";

    MyClassLoader(String name) {
        this.name = name;
    }

    MyClassLoader(ClassLoader parent, String name) {
        super(parent);
        this.name = name;
    }

    /**
     * 重写findClass方法
     */
    @Override
    public Class<?> findClass(String name) {
        byte[] data = loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }

    public byte[] loadClassData(String name) {
        try {
            name = name.replace(".", "//");
            FileInputStream is = new FileInputStream(new File(path + name + ".class"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;
            while ((b = is.read()) != -1) {
                baos.write(b);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
