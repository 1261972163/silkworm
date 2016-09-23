package com.jengine.transport;

import com.jengine.transport.https.HttpsClient;
import org.junit.Test;

/**
 * @author nouuid
 * @date 3/28/2016
 * @description
 */
public class HttpsClientTest {

    HttpsClient hc = new HttpsClient();

    @Test
    public void testIt() {
        String urlStr = "https://www.baidu.com/";
        hc.connect(urlStr);
        hc.printHttpsCert();
        hc.printContent();
    }

}
