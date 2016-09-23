package com.jengine.common;

import org.junit.Test;

/**
 * Created by nouuid on 2015/6/25.
 */
public class NetworkUtilTest {

    @Test
    public void test() {
        networkTest();
    }

    public void networkTest() {
        System.out.println(NetworkUtil.getHostIp());
        StringBuffer content = NetworkUtil.readByURL("http://www.weixueyuan.net/uploads/code/java/rumen/13-2.txt");
        System.out.println(content);
    }


}
