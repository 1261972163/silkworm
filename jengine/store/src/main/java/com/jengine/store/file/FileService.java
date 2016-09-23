package com.jengine.store.file;

/**
 * Created by nouuid on 2015/5/7.
 */
public interface FileService {

    public void save(String uuid, Object value);
    public void remove(String uuid);
    public Object get(String uuid);
}
