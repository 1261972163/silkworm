package com.jengine.common.webSpider;

import com.jengine.common.utils.NetworkUtil;
import org.junit.Test;

/**
 * content
 *
 * @author bl07637
 * @date 6/6/2017
 * @since 0.1.0
 */
public class WebSpiderDemo {
    WebSpider webSpider = new WebSpider();

    @Test
    public void readByURLTest() {
        System.out.println(NetworkUtil.getHostIp());
        StringBuffer content = webSpider.readByURL("http://www.weixueyuan.net/uploads/code/java/rumen/13-2.txt");
        System.out.println(content);
    }
}
