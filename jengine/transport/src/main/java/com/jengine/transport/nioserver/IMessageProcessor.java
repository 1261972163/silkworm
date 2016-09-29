package com.jengine.transport.nioserver;

/**
 * @author nouuid
 * @description
 * @date 9/28/16
 */
public interface IMessageProcessor {

    public void process(Message message, WriteProxy writeProxy);

}
