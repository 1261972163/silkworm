package com.jengine.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by nouuid on 2015/4/28.
 */
public class SystemUtil {

    /**
     * 获取InetAddress
     * @return
     */
    public static InetAddress getInetAddress() {
        try{
            return InetAddress.getLocalHost();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取主机ip
     * @return
     */
    public static String getHostIp() {
        InetAddress netAddress = getInetAddress();
        if(null == netAddress){
            return null;
        }
        return netAddress.getHostAddress();
    }

    /**
     * 获取主机名
     * @return
     */
    public static String getHostName() {
        InetAddress netAddress = getInetAddress();
        if (null == netAddress) {
            return null;
        }
        return netAddress.getHostName();
    }
}
