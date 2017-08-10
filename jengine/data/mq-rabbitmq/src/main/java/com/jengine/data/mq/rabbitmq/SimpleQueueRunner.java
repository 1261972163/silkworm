package com.jengine.data.mq.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author nouuid
 * @date 4/12/2016
 * @description
 * one producer, one consumer
 */
public class SimpleQueueRunner {

    public void simpleQueueTest() throws InterruptedException {
        SimpleQueue simpleQueue = new SimpleQueue();
        simpleQueue.init();
        Thread.sleep(5*1000);

        simpleQueue.send();
        Thread.sleep(2*1000);
        simpleQueue.receive();
        Thread.sleep(30*1000);
    }

}

class SimpleQueue {
    private final static String QUEUE_NAME = "hello";

    ConnectionFactory factory = null;
    Connection connection = null;
    Channel channel = null;

    public void init() {
        try {
            factory = new ConnectionFactory();
            factory.setHost("172.18.9.206");
            factory.setUsername("admin");
            factory.setPassword("abc123");
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        try {
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
