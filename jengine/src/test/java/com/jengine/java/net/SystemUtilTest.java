package com.jengine.java.net;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/7.
 */
public class SystemUtilTest {

    @Test
    public void test() {

        String hostip = SystemUtil.getHostIp();
        String hostname = SystemUtil.getHostName();

        System.out.println(hostip);
        System.out.println(hostname);

    }
}
