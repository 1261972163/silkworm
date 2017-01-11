package com.jengine.transport.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.resource.Resource;

/**
 * content
 *
 * @author bl07637
 * @date 1/10/2017
 * @since 0.1.0
 */
public class JettyServerDemo {

    @org.junit.Test
    public void demo() throws Exception {
        Server server = new Server(8888);
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setClassLoader(Thread.currentThread().getContextClassLoader());
        Resource baseResource = Resource.newClassPathResource("webapp");
        webapp.setBaseResource(baseResource);
        webapp.setDescriptor("webapp/WEB-INF/web.xml");
        server.setHandler(webapp);
        server.start();
        Thread.sleep(30*60*1000);

    }
}
