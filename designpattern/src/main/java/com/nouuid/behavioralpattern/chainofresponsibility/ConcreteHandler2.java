package com.nouuid.behavioralpattern.chainofresponsibility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by nouuid on 2015/5/14.
 */
public class ConcreteHandler2 extends Handler {

    protected Level getHandlerLevel() {
        return new Level(3);
    }

    public Response response(Request request) {
        logger.info("be processed in ConcreteHandler2.");
        return null;
    }
}
