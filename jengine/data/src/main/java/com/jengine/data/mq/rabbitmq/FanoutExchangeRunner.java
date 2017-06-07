package com.jengine.data.mq.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author nouuid
 * @date 4/13/2016
 * @description
 * deliver a message to multiple consumers.
 */
public class FanoutExchangeRunner {

    public void exchangeTest() throws InterruptedException {
        Thread receiveThread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    FanoutExchange publishSubscribe = new FanoutExchange();
                    publishSubscribe.init();
                    publishSubscribe.consume("");
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
                    FanoutExchange publishSubscribe = new FanoutExchange();
                    publishSubscribe.init();
                    publishSubscribe.consume("                                                      ");
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
                    FanoutExchange publishSubscribe = new FanoutExchange();
                    publishSubscribe.init();
                    Thread.sleep(2*1000);
                    String[] args1 = {"1 message."};
                    String[] args2 = {"2 message.."};
                    String[] args3 = {"3 message..."};
                    String[] args4 = {"4 message...."};
                    String[] args5 = {"5 message....."};
                    publishSubscribe.send(args1, "                                                                                                            ");
                    publishSubscribe.send(args2, "                                                                                                            ");
                    publishSubscribe.send(args3, "                                                                                                            ");
                    publishSubscribe.send(args4, "                                                                                                            ");
                    publishSubscribe.send(args5, "                                                                                                            ");
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

class FanoutExchange extends WorkQueues {
    private final static String EXCHANGE_NAME = "exchange_hello";

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
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void send(String[] strings, String tab) {
        try {
            String message = getMessage(strings);
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(tab + Thread.currentThread().getName() + " [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void consume(String tab) {
        try {
//            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");

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
