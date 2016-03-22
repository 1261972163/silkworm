package com.jengine.java.io;

import java.io.ByteArrayOutputStream;

/**
 * Created by nouuid on 2015/5/7.
 */
public interface FileService {

    /**
     * 添加
     * @param filename 文件唯一标识
     * @param fileData 文件流
     * @return
     */
    public boolean save(String filename, byte[] fileData);

    /**
     * 删除
     * @param filename
     * @return
     */
    public boolean remove(String filename); //删

    /**
     * 查询
     * @param filename
     * @return
     */
    public ByteArrayOutputStream get(String filename); //查
}
