package com.nouuid.behavioralpattern.chainofresponsibility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by nouuid on 2015/5/14.
 */
public class ConcreteHandler3 extends Handler {

    protected Level getHandlerLevel() {
        return new Level(5);
    }

    public Response response(Request request) {
        logger.info("be processed in ConcreteHandler3.");
        return null;
    }
}
