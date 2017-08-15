package com.jengine.data.mq.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.junit.Test;

/**
 * content
 *
 * @author nouuid
 * @date 6/23/2017
 * @since 0.1.0
 */
public class ProducerDemo {

  // 默认连接用户名
  private static final String USERNAME = "admin";
  // 默认连接密码
  private static final String PASSWORD = "admin";
  // 默认连接地址
  private static final String BROKEURL = "tcp://10.45.11.85:61616";
  // 发送的消息数量
  private static final int SENDNUM = 10;

  /**
   * 发送消息
   *
   * @param messageProducer 消息生产者
   */
  public static void sendMessage(Session session, MessageProducer messageProducer)
      throws Exception {
    for (int i = 0; i < ProducerDemo.SENDNUM; i++) {
      //创建一条文本消息
      TextMessage message = session.createTextMessage("ActiveMQ " + i + "@#$");
      System.out.println("发送消息：Activemq 发送消息" + i);
      //通过消息生产者发出消息
      messageProducer.send(message);
    }
  }

  @Test
  public void test1() {
    ConnectionFactory connectionFactory;//连接工厂
    Connection connection = null;//连接
    Session session;//会话 接受或者发送消息的线程
    Destination destination;//消息的目的地
    MessageProducer messageProducer;//消息生产者
    connectionFactory = new ActiveMQConnectionFactory(ProducerDemo.USERNAME, ProducerDemo.PASSWORD,
        ProducerDemo.BROKEURL);//实例化连接工厂
    try {
      connection = connectionFactory.createConnection();//通过连接工厂获取连接
      connection.start();//启动连接
      session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);//创建session
      destination = session.createQueue("HelloWorld");//创建一个名称为HelloWorld的消息队列
      messageProducer = session.createProducer(destination);//创建消息生产者
      sendMessage(session, messageProducer);//发送消息
      session.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (JMSException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Test
  public void pooledConnectionFactory() throws Exception {
    PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(
        ProducerDemo.BROKEURL);
    pooledConnectionFactory.setMaxConnections(10);
    pooledConnectionFactory.setIdleTimeout(60);
    pooledConnectionFactory.start();

    Connection connection = pooledConnectionFactory
        .createConnection(ProducerDemo.USERNAME, ProducerDemo.PASSWORD);
    connection.start();
    Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
    Destination destination = session.createQueue("HelloWorld");
    MessageProducer messageProducer = session.createProducer(destination);
    sendMessage(session, messageProducer);//发送消息
    session.commit();

    System.out.println("sleeping1...");
    Thread.sleep(5 * 1000);
    pooledConnectionFactory.stop();
    System.out.println("end1.");
    Thread.sleep(5 * 1000);

    System.out.println("start2...");
    pooledConnectionFactory = new PooledConnectionFactory(ProducerDemo.BROKEURL);
    pooledConnectionFactory.setMaxConnections(10);
    pooledConnectionFactory.setIdleTimeout(60);
    pooledConnectionFactory.start();
    connection = pooledConnectionFactory
        .createConnection(ProducerDemo.USERNAME, ProducerDemo.PASSWORD);
    connection.start();
    session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
    destination = session.createQueue("HelloWorld");
    messageProducer = session.createProducer(destination);
    sendMessage(session, messageProducer);//发送消息
    session.commit();
    System.out.println("sleeping2...");
    Thread.sleep(5 * 1000);
    System.out.println("end2.");
  }


}
