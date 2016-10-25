package com.jengine.common.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;

/**
 * content
 *
 * @author bl07637
 * @date 9/28/2016
 * @since 0.1.0
 */
public class LogbackDemo {

    private final static Logger logger = LoggerFactory.getLogger(LogbackDemo.class);

    @Test
    public void test() {
        logger.info("logback 成功了");
        logger.error("logback 成功了");
    }
}
