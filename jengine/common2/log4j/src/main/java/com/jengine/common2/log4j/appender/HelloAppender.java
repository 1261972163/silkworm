package com.jengine.common2.log4j.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 实现自定义log4j Appender：
 * 继承log4j公共的基类：AppenderSkeleton
 * 打印日志核心方法：abstract protected void append(LoggingEvent event);
 * 初始化加载资源：public void activateOptions()，默认实现为空
 * 释放资源：public void close()
 * 是否需要按格式输出文本：public boolean requiresLayout()
 * 正常情况下我们只需要覆盖append方法即可。然后就可以在log4j中使用了
 *
 * @author bl07637
 * @date 8/10/2017
 * @since 0.1.0
 */
public class HelloAppender extends AppenderSkeleton {
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    protected void append(LoggingEvent event) {
        System.out.println("Hello, " + account + " : " + event.getMessage());
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
