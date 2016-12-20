package com.jengine.transport.netty.httpserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * content
 *
 * @author bl07637
 * @date 12/15/2016
 * @since 0.1.0
 */
public class Demo {
    private static Log log = LogFactory.getLog(HttpServer.class);

    @Test
    public void server() throws Exception {
        HttpServer server = new HttpServer();
        log.info("Http Server listening on 8844 ...");
        server.start(8844);
    }

    @Test
    public void client() throws Exception {
        HttpClient client = new HttpClient();
        client.connect("127.0.0.1", 8844);
        client.send("1");
        client.send("2");

        Thread.sleep(5*60*1000);

    }
}
