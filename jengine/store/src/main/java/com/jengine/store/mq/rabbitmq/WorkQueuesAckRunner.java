package com.jengine.store.mq.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author nouuid
 * @date 4/12/2016
 * @description
 * If a consumer dies (its channel is closed, connection is closed, or TCP connection is lost) without sending an ack,
 * RabbitMQ will understand that a message wasn't processed fully and will re-queue it.
 */
public class WorkQueuesAckRunner {
    /**
     * If a consumer dies (its channel is closed, connection is closed, or TCP connection is lost) without sending an ack,
     * RabbitMQ will understand that a message wasn't processed fully and will re-queue it.
     * If there are other consumers online at the same time,
     * it will then quickly redeliver it to another consumer.
     */
    public void oneReceiveWithoutAckThenStopTest() throws InterruptedException {
        Thread receiveThread1 = new Thread(new Runnable() {
            public void run() {
                WorkQueuesAck workQueuesAck = new WorkQueuesAck();
                workQueuesAck.init();
                try {
                    workQueuesAck.receiveWithoutAckThenStop("");
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
                WorkQueuesAck workQueuesAck = new WorkQueuesAck();
                workQueuesAck.init();
                try {
                    workQueuesAck.receiveAckOne("                                                      ");
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
                    WorkQueuesAck workQueuesAck = new WorkQueuesAck();
                    workQueuesAck.init();
                    Thread.sleep(2*1000);
                    String[] args1 = {"1 message."};
                    String[] args2 = {"2 message.."};
                    String[] args3 = {"3 message..."};
                    String[] args4 = {"4 message...."};
                    String[] args5 = {"5 message....."};
                    workQueuesAck.send(args1, "                                                                                                            ");
                    workQueuesAck.send(args2, "                                                                                                            ");
                    workQueuesAck.send(args3, "                                                                                                            ");
                    workQueuesAck.send(args4, "                                                                                                            ");
                    workQueuesAck.send(args5, "                                                                                                            ");

                    Thread.sleep(30*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        });
        sendThread.setName("ST1");
        sendThread.start();
    }

    /**
     * If a consumer dies (its channel is closed, connection is closed, or TCP connection is lost) without sending an ack,
     * RabbitMQ will understand that a message wasn't processed fully and will re-queue it.
     * If there are not other consumers online at the same time, messages will be marked as ready
     */
    public void allReceiveWithoutAckThenStopTest() throws InterruptedException {
        Thread receiveThread1 = new Thread(new Runnable() {
            public void run() {
                WorkQueuesAck workQueuesAck = new WorkQueuesAck();
                workQueuesAck.init();
                try {
                    workQueuesAck.receiveWithoutAckThenStop("");
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
                WorkQueuesAck workQueuesAck = new WorkQueuesAck();
                workQueuesAck.init();
                try {
                    workQueuesAck.receiveWithoutAckThenStop("                                                      ");
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
                    WorkQueuesAck workQueuesAck = new WorkQueuesAck();
                    workQueuesAck.init();
                    Thread.sleep(2*1000);
                    String[] args1 = {"1 message."};
                    String[] args2 = {"2 message.."};
                    String[] args3 = {"3 message..."};
                    String[] args4 = {"4 message...."};
                    String[] args5 = {"5 message....."};
                    workQueuesAck.send(args1, "                                                                                                            ");
                    workQueuesAck.send(args2, "                                                                                                            ");
                    workQueuesAck.send(args3, "                                                                                                            ");
                    workQueuesAck.send(args4, "                                                                                                            ");
                    workQueuesAck.send(args5, "                                                                                                            ");

                    Thread.sleep(30*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        });
        sendThread.setName("ST1");
        sendThread.start();
    }

    /**
     * If a consumer dies (its channel is closed, connection is closed, or TCP connection is lost) without sending an ack,
     * at the same time, other consumers acknowledge all messages up,
     * RabbitMQ will regard the message was acked.
     */
    public void oneReceiveWithoutAckThenOtherReceiveAckAllTest() throws InterruptedException {
        Thread receiveThread1 = new Thread(new Runnable() {
            public void run() {
                WorkQueuesAck workQueuesAck = new WorkQueuesAck();
                workQueuesAck.init();
                try {
                    workQueuesAck.receiveWithoutAck("");
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
                WorkQueuesAck workQueuesAck = new WorkQueuesAck();
                workQueuesAck.init();
                try {
                    workQueuesAck.receiveAckAll("                                                      ");
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
                    WorkQueuesAck workQueuesAck = new WorkQueuesAck();
                    workQueuesAck.init();
                    Thread.sleep(2*1000);
                    String[] args1 = {"1 message."};
                    String[] args2 = {"2 message.."};
                    String[] args3 = {"3 message..."};
                    String[] args4 = {"4 message...."};
                    String[] args5 = {"5 message....."};
                    workQueuesAck.send(args1, "                                                                                                            ");
                    workQueuesAck.send(args2, "                                                                                                            ");
                    workQueuesAck.send(args3, "                                                                                                            ");
                    workQueuesAck.send(args4, "                                                                                                            ");
                    workQueuesAck.send(args5, "                                                                                                            ");

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

class WorkQueuesAck extends WorkQueues {

    private final static String TASK_QUEUE_NAME = "task_hello2";

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
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
            channel.basicQos(1);
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

    public void receiveAutoAck(String tab) throws IOException {
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
        channel.basicConsume(TASK_QUEUE_NAME, true, consumer);
    }

    public void receiveAckOne(String tab) throws IOException {
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
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }

    public void receiveAckAll(String tab) throws IOException {
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
                    channel.basicAck(envelope.getDeliveryTag(), true);
                }
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }

    public void receiveWithoutAckThenStop(String tab) throws IOException {
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
                    Thread.currentThread().stop();
                }
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
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

