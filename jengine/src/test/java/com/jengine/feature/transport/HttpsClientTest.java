package com.jengine.feature.transport;

import org.junit.Test;

/**
 * @author nouuid
 * @date 3/28/2016
 * @description
 */
public class HttpsClientTest {

    HttpsClient hc = new HttpsClient();

    @Test
    public void testIt(){
        String urlStr = "https://www.baidu.com/";
        hc.connect(urlStr);
        hc.printHttpsCert();
        hc.printContent();
    }

}
