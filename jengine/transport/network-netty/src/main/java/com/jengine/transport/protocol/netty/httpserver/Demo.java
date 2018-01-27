package com.jengine.transport.protocol.netty.httpserver;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * content
 *
 * @author nouuid
 * @date 12/15/2016
 * @since 0.1.0
 */
public class Demo {
  public static final Logger logger = LoggerFactory.getLogger(Demo.class);

  @Test
  public void server() throws Exception {
    HttpServer server = new HttpServer();
    logger.info("Http Server listening on 8844 ...");
    server.start(8844);
  }

  @Test
  public void client() throws Exception {
    HttpClient client = new HttpClient();
    client.connect("127.0.0.1", 8844);
    client.send("1");
    client.send("2");

    Thread.sleep(5 * 60 * 1000);

  }

}
