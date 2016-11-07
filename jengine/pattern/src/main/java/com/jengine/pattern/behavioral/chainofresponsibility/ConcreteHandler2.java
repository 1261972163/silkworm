package com.jengine.pattern.behavioral.chainofresponsibility;

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
