package com.jengine.transport.restful;

import java.io.IOException;

/**
 * @author nouuid
 * @description
 * @date 8/13/17
 */
public interface RestfulServer {

    void start() throws IOException;
    void stop() throws IOException;
}
