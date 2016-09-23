package com.jengine.transport.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author nouuid
 * @date 4/13/2016
 * @description
 */
public class TopicExchangeRoutingRunner {

    public void topicExchangeRoutingTest() throws InterruptedException {
        Thread receiveThread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    TopicExchangeRouting topicExchangeRouting = new TopicExchangeRouting();
                    topicExchangeRouting.init();
                    topicExchangeRouting.consume("*.orange.*", "");
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        receiveThread1.setName("RT1");
        receiveThread1.start();

        Thread receiveThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TopicExchangeRouting topicExchangeRouting = new TopicExchangeRouting();
                    topicExchangeRouting.init();
                    topicExchangeRouting.consume("*.*.rabbit", "                                                      ");
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        receiveThread2.setName("RT2");
        receiveThread2.start();

        Thread.sleep(2*1000);

        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TopicExchangeRouting topicExchangeRouting = new TopicExchangeRouting();
                    topicExchangeRouting.init();
                    Thread.sleep(2*1000);
                    String[] args1 = {"1 message."};
                    String[] args2 = {"2 message.."};
                    String[] args3 = {"3 message..."};
                    String[] args4 = {"4 message...."};
                    String[] args5 = {"5 message....."};
                    topicExchangeRouting.send(args1, "quick.orange.rabbit", "                                                                                                            ");
                    topicExchangeRouting.send(args2, "quick.orange.rabbit", "                                                                                                            ");
                    topicExchangeRouting.send(args3, "quick.orange.rabbit", "                                                                                                            ");
                    topicExchangeRouting.send(args4, "lazy.pink.rabbit", "                                                                                                            ");
                    topicExchangeRouting.send(args5, "lazy.pink.rabbit1", "                                                                                                            ");
                    Thread.sleep(30*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        });
        sendThread.setName("ST1");
        sendThread.start();
    }
}

class TopicExchangeRouting extends WorkQueues {
    private final static String EXCHANGE_NAME = "routing_topic_hello";

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
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void send(String[] strings, String routingKey, String tab) {
        try {
            String message = getMessage(strings);
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println(tab + Thread.currentThread().getName() + " [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void consume(String routingKey, String tab) {
        try {
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(tab + Thread.currentThread().getName() + " [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
