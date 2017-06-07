package com.jengine.transport.server.grizzly;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.threadpool.GrizzlyExecutorService;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

import java.util.concurrent.ExecutorService;

/**
 * content
 *
 * @author bl07637
 * @date 10/25/2016
 * @since 0.1.0
 */
public class GrizzlyHttpHandler extends HttpHandler {

    final ExecutorService complexAppExecutorService = GrizzlyExecutorService.createInstance(
            ThreadPoolConfig.defaultConfig()
                    .copy()
                    .setCorePoolSize(5)
                    .setMaxPoolSize(5));

    public void service(final Request request, final Response response) throws Exception {
        response.suspend(); // Instruct Grizzly to not flush response, once we exit the service(...) method
        complexAppExecutorService.execute(new Runnable() {   // Execute long-lasting task in the custom thread
            public void run() {
                try {
                    response.setContentType("text/plain");
                    // Simulate long lasting task
                    Thread.sleep(1000);
                    response.getWriter().write("Complex task is done!");
                } catch (Exception e) {
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
                } finally {
                    response.resume();
                }
            }
        });
    }
}