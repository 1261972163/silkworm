package com.jengine.common.log4j;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * content
 *
 * @author bl07637
 * @date 9/27/2016
 * @since 0.1.0
 */
public class Log4jDemo {

    private static Logger logger = Logger.getLogger(Log4jDemo.class.getName());

    @Test
    public void test() {
        //可指定log4j配置路径
//        String log4jPath = Log4jDemo.class.getResource("/").getPath() + "log4j.properties";
//        PropertyConfigurator.configure(log4jPath);
        logger.debug("test1");
    }
}
