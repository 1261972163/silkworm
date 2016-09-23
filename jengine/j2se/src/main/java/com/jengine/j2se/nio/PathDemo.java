package com.jengine.j2se.nio;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Path & Paths
 * Paths：将path字符串或字符串序列转换成一下Path对象
 * Path：Path对象
 *
 * @author bl07637
 * @date 9/20/2016
 * @since 0.1.0
 */
public class PathDemo {

    public void test(String pathStr) {
        Path p1 = Paths.get(pathStr);
        System.out.println("p1 : " + p1);

        System.out.println("user home : " + System.getProperty("user.home"));
        Path p2 = Paths.get(System.getProperty("user.home"), "download", "pdf");
        System.out.println("p2 : " + p2);
    }
}
