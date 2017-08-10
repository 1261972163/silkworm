package com.jengine.common2.j2se.io;

import junit.framework.Assert;
import org.junit.Test;

import java.io.*;
/**
 * Created by nouuid on 2015/4/29.
 */
public class IO02_File {

    @Test
    public void operate() throws IOException {
        String foldPath = IO02_File.class.getResource("/").getPath() + "test";
        String filePath = foldPath + "/test1.md";
        File file1 = new File(foldPath);
        // 1. 检查目录是否存在
        // 2. 创建目录
        if (!file1.exists()) {
            file1.mkdir();
        }
        Assert.assertTrue(file1.exists());
        // 3. 创建文件
        File file2 = new File(filePath);
        file2.createNewFile();
        Assert.assertTrue(file2.exists());
        // 4. 删除文件
        file2.delete();
        Assert.assertFalse(file2.exists());
        file1.delete();
    }


    // 5. FileOutputStream按字节写文件
    // 6. FileInputStream按字节读文件
    @Test
    public void writeReadBytes() throws IOException {
        String filePath = IO02_File.class.getResource("/").getPath() + "test";
        System.out.println(filePath);
        File file = new File(filePath);
        file.deleteOnExit();

        // 5. 按字节写文件
        String content = "abcdefg";
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(content.getBytes());
        fileOutputStream.write(content.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();

        // 6. 按字节读文件
        FileInputStream fileInputStream = new FileInputStream(filePath);
        int c;
        while ((c=fileInputStream.read())!=-1) {
            System.out.print((char) c);
        }
        fileInputStream.close();

        file.deleteOnExit();
    }

    // BufferedOutputStream和BufferedInputStream按字节缓冲读写文件
    @Test
    public void writeReadBufferedBytes() throws IOException {
        String filePath = IO02_File.class.getResource("/").getPath() + "test";
        System.out.println(filePath);
        File file = new File(filePath);
        file.deleteOnExit();

        // 5. 按字节写文件
        String content = "abcdefg";
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(content.getBytes());
        fileOutputStream.write(content.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();

        // 6. 按字节读文件
        FileInputStream fileInputStream = new FileInputStream(filePath);
        int c;
        while ((c=fileInputStream.read())!=-1) {
            System.out.print((char) c);
        }
        fileInputStream.close();

        file.deleteOnExit();
    }


    // 7. FileWriter按字符写文件
    // 8. FileReader按字符读文件
    // 9. FileReader按行读文件，包装到BufferedReader
    @Test
    public void writerReader() throws IOException {
        String filePath = IO02_File.class.getResource("/").getPath() + "test";
        System.out.println(filePath);
        File file = new File(filePath);
        file.deleteOnExit();

        // 7. 按字节写文件
        String content = "abcdefg\nabcdefg";
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();

        // 8. 按字节读文件
        FileReader fileReader = new FileReader(filePath);
        int c;
        while ((c=fileReader.read())!=-1) {
            System.out.print((char) c);
        }
        fileReader.close();

        System.out.println("-----------------------");
        // 9. 按字符读文件，包装到BufferedReader
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        StringBuilder s = new StringBuilder();
        String temp = null;
        while((temp=bufferedReader.readLine())!=null) {
            s.append(temp);
        }
        System.out.println(s);
        bufferedReader.close();

        file.deleteOnExit();
    }

}
