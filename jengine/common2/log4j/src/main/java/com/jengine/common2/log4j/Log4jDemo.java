package com.jengine.common2.log4j;

/**
 * 【Log4j组件】主要有三个：
 * <p>
 * Logger：负责供客户端代码调用，执行debug(Object msg)、info(Object msg)、warn(Object msg)、error(Object msg)等方法。
 * Appender：负责日志的输出，Log4j已经实现了多种不同目标的输出方式，可以向文件输出日志、向控制台输出日志、向Socket输出日志等。
 * Layout：负责日志信息的格式化。
 * <p>
 * 【Log4j执行顺序及关系】。调用Log4j输出日志时，调用各个组件的顺序:
 * <p>
 * 1、日志信息传入 Logger。
 * 2、将日志信息封装成 LoggingEvent 对象并传入 Appender。
 * 3、在 Appender 中调用 Filter 对日志信息进行过滤，调用 Layout 对日志信息进行格式化，然后输出。
 * <p>
 * 图示见http://www.cnblogs.com/grh946/p/5977046.html
 * <p>
 * <p>
 * 【Logger的层级】是logger名字指定的，如x.y 表示两层，x层和y层，x是y的父层级，x.y所在层级是y层级
 * <p>
 * log4j.additivity.* = false : 表示当前logger不需要打到父层级所指定的appender，只打到当前的appender；
 * 默认true：表示当前logger将打印日志到当前的appender及所有的父层级所指定的appender
 *
 * @author nouuid
 * @date 8/10/2017
 * @since 0.1.0
 */
public class Log4jDemo {
}
