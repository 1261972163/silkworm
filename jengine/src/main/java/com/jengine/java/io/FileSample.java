package com.jengine.java.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by nouuid on 2015/4/29.
 */
public class FileSample {
    private static final Logger logger = LoggerFactory.getLogger(FileSample.class);

    /**
     * create a new folder
     * @param path
     */
    public static void mkdir(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            System.out.println("error in creating a new folder");
            e.printStackTrace();
        }
    }

    /**
     * create a new file
     * @param path
     */
    public static void touch(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
//            FileWriter fileWriter = new FileWriter(file);
//            PrintWriter printWriter = new PrintWriter(fileWriter);
//            printWriter.println(str2);
//            fileWriter.close();
        } catch (Exception e) {
            System.out.println("error in creating a new file");
            e.printStackTrace();
        }
    }

    /**
     * delete a file
     * @param path
     */
    public static void rm(String path) {
        File file = new File(path);
        try {
            file.delete();
        } catch (Exception e) {
            System.out.println("error in deleting a file");
            e.printStackTrace();
        }
    }

    /**
     * delete file and it's children
     * @param path
     */
    public static void rmf(String path) { //TODO
        File folder = new File(path);
        try {
            folder.delete();
        } catch (Exception e) {
            System.out.println("ɾ���ļ��в�������");
            e.printStackTrace();
        }
    }

    /**
     * clean folder
     * @param path
     */
    public static void clean(String path) { //TODO
        File folder = new File(path);
        try {
            if(!folder.exists()) {
                folder.delete();
            }
            folder.mkdir();
        }
        catch (Exception e) {
            System.out.println("error in cleanning a folder");
            e.printStackTrace();
        }
    }

    /**
     * read file content by bytes
     * @param path
     * @return
     */
    public ByteArrayOutputStream readFileByBytes(String path) {
        BufferedInputStream in = null;
        ByteArrayOutputStream baos = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            baos = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            int size = 0;
            while((size = in.read(temp)) != -1) {
                baos.write(temp, 0, size);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return baos;
    }

    /**
     * read file by lines
     * @param path
     * @return
     */
    public static StringBuffer readFileByLines(String path) {
        StringBuffer s = null;
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            s = new StringBuffer();
            String temp = null;
            while((temp=reader.readLine())!=null) {
                s.append(temp);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return s;
    }
}
