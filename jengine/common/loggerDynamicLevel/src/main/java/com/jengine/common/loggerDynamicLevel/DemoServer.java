package com.jengine.common.loggerDynamicLevel;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nouuid
 */
public class DemoServer {
  private static final Logger logger = LoggerFactory.getLogger(DemoServer.class);

  public void start() throws Exception {
    logger.info("start server ...");
    Server server = new Server(8888);
    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath("/");
    webapp.setClassLoader(Thread.currentThread().getContextClassLoader());
    Resource baseResource = Resource.newClassPathResource("webapp");
    webapp.setBaseResource(baseResource);
    webapp.setDescriptor("webapp/WEB-INF/web.xml");
    server.setHandler(webapp);
    server.start();
    int i = 1;
    while (i<100) {
      if (i%2==0) {
        logger.debug("######### i=" + i);
      } else {
        logger.info("######### i=" + i);
      }
      Thread.sleep(1000);
      i++;
    }
    Thread.sleep(30*60*1000);
    logger.info("end server...");
  }
}
