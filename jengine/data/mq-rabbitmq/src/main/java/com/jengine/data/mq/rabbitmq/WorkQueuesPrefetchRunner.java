package com.jengine.data.mq.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author nouuid
 * @date 4/13/2016
 * @description
 */
public class WorkQueuesPrefetchRunner {

    public void prefetchOneReceiveWithoutAckTest() throws InterruptedException {
        Thread receiveThread1 = new Thread(new Runnable() {
            public void run() {
                WorkQueuesPrefetch workQueuesPrefetch = new WorkQueuesPrefetch();
                workQueuesPrefetch.init(1);
                try {
                    workQueuesPrefetch.receiveWithoutAck("");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
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
                WorkQueuesPrefetch workQueuesPrefetch = new WorkQueuesPrefetch();
                workQueuesPrefetch.init(1);
                try {
                    workQueuesPrefetch.receiveWithoutAck("                                                      ");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
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
                    WorkQueuesPrefetch workQueuesPrefetch = new WorkQueuesPrefetch();
                    workQueuesPrefetch.init(1);
                    Thread.sleep(2*1000);
                    String[] args1 = {"1 message."};
                    String[] args2 = {"2 message.."};
                    String[] args3 = {"3 message..."};
                    String[] args4 = {"4 message...."};
                    String[] args5 = {"5 message....."};
                    workQueuesPrefetch.send(args1, "                                                                                                            ");
                    workQueuesPrefetch.send(args2, "                                                                                                            ");
                    workQueuesPrefetch.send(args3, "                                                                                                            ");
                    workQueuesPrefetch.send(args4, "                                                                                                            ");
                    workQueuesPrefetch.send(args5, "                                                                                                            ");

                    Thread.sleep(30*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        });
        sendThread.setName("ST1");
        sendThread.start();
    }

    public void prefetchTwoReceiveWithoutAckTest() throws InterruptedException {
        Thread receiveThread1 = new Thread(new Runnable() {
            public void run() {
                WorkQueuesPrefetch workQueuesPrefetch = new WorkQueuesPrefetch();
                workQueuesPrefetch.init(2);
                try {
                    workQueuesPrefetch.receiveWithoutAck("");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
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
                WorkQueuesPrefetch workQueuesPrefetch = new WorkQueuesPrefetch();
                workQueuesPrefetch.init(2);
                try {
                    workQueuesPrefetch.receiveWithoutAck("                                                      ");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
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
                    WorkQueuesPrefetch workQueuesPrefetch = new WorkQueuesPrefetch();
                    workQueuesPrefetch.init(2);
                    Thread.sleep(2*1000);
                    String[] args1 = {"1 message."};
                    String[] args2 = {"2 message.."};
                    String[] args3 = {"3 message..."};
                    String[] args4 = {"4 message...."};
                    String[] args5 = {"5 message....."};
                    workQueuesPrefetch.send(args1, "                                                                                                            ");
                    workQueuesPrefetch.send(args2, "                                                                                                            ");
                    workQueuesPrefetch.send(args3, "                                                                                                            ");
                    workQueuesPrefetch.send(args4, "                                                                                                            ");
                    workQueuesPrefetch.send(args5, "                                                                                                            ");

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

class WorkQueuesPrefetch extends WorkQueues {

    private final static String TASK_QUEUE_NAME = "task_hello3";

    ConnectionFactory factory = null;
    Connection connection = null;
    Channel channel = null;

    public void init(int prefetch) {
        try {
            factory = new ConnectionFactory();
            factory.setHost("172.18.9.206");
            factory.setUsername("admin");
            factory.setPassword("abc123");
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
            channel.basicQos(prefetch);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void send(String[] strings, String tab) {
        try {
            String message = getMessage(strings);
            channel.basicPublish("", TASK_QUEUE_NAME, null, message.getBytes());
            System.out.println(tab + Thread.currentThread().getName() + " [x] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveWithoutAck(String tab) throws IOException {
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(tab + Thread.currentThread().getName() + " [x] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(tab + Thread.currentThread().getName() + " [x] Done");
                }
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }
}
