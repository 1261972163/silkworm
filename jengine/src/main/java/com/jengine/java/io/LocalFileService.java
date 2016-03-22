package com.jengine.java.io;

import java.io.*;

/**
 * Created by nouuid on 2015/5/7.
 */
public class LocalFileService implements FileService {

    private String rootPath;

    public LocalFileService(String rootPath) {
        this.rootPath = rootPath == null ? "/opt/ftp/" : rootPath;
    }

    @Override
    public boolean save(String filename, byte[] fileData) {
        boolean flag = true;
        String filePath = rootPath + filename; //文件地址
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {  // 路径为文件且不为空则进行删除
            flag = false;
        } else {
            FileOutputStream fs = null;
            try {
                fs = new FileOutputStream(filePath);
                fs.write(fileData, 0, fileData.length); //写文件
                fs.flush();
                fs.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                flag = false;
            } catch (IOException e2) {
                e2.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public boolean remove(String filename) {
        boolean flag = false;
        String filePath = rootPath + filename; //文件地址
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {  // 路径为文件且不为空则进行删除
            file.delete();
            flag = true;
        }
        return flag;
    }

    @Override
    public ByteArrayOutputStream get(String filename) {
        String filePath = rootPath + filename;
        BufferedInputStream in = null;
        ByteArrayOutputStream baos = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            baos = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
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
}
