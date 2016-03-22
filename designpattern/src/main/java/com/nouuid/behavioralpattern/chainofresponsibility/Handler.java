package com.nouuid.behavioralpattern.chainofresponsibility;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �����������ж�
 * ģ�⴦��ȼ���ֻ�д�����Ĵ���ȼ�����Request�ĵȼ����ܴ������򽻸���һ�������ߴ���
 * Created by nouuid on 2015/5/14.
 */
public abstract class Handler {

    public static final Log logger = LogFactory.getLog(Handler.class);

    private Handler nextHandler;

    public final Response handleRequest(Request request) {
        Response response = null;

        if (this.getHandlerLevel().compareTo(request.getLevel())) {
            response = this.response(request); //current handler
        } else {
            if (this.nextHandler != null) {
                this.nextHandler.handleRequest(request); //next handler
            } else {
                logger.warn("can't find any appropriate handler."); //no handler
            }
        }
        return response;
    }

    public void setNextHandler(Handler handler) {
        this.nextHandler = handler;
    }

    protected abstract Level getHandlerLevel();

    public abstract Response response(Request request);
}
