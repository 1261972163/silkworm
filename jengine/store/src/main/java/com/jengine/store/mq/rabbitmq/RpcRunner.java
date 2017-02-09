package com.jengine.store.mq.rabbitmq;

import com.rabbitmq.client.*;
import com.rabbitmq.client.AMQP.BasicProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author nouuid
 * @date 4/14/2016
 * @description
 */
public class RpcRunner {

    public void rpcTest() throws InterruptedException {

        Thread clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RpcClient rpcClient = new RpcClient();
                    rpcClient.init();
                    Thread.sleep(2*1000);

                    String[] args1 = {"31"};
                    String[] args2 = {"32"};
                    String[] args3 = {"33"};
                    String[] args4 = {"34"};
                    String[] args5 = {"35"};
                    String res = rpcClient.send(args1,"");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                RpcServer rpcServer = new RpcServer();
                rpcServer.init();
                rpcServer.consume("                                                      ");
            }
        });

        serverThread.start();
        Thread.sleep(2 * 1000);
        clientThread.start();
        Thread.sleep(10*1000);


    }

}

class RpcServer {
    private final static String RPC_QUEUE_NAME = "rpc_hello";

    ConnectionFactory factory = null;
    Connection connection = null;
    Channel channel = null;
    QueueingConsumer consumer = null;

    public void init() {
        try {
            factory = new ConnectionFactory();
            factory.setHost("172.18.9.206");
            factory.setUsername("admin");
            factory.setPassword("abc123");
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            channel.basicQos(1);
            consumer = new QueueingConsumer(channel);
            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void consume(String tab) {
        try {

            System.out.println(tab + "server, [x] Awaiting RPC requests");
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();

                BasicProperties props = delivery.getProperties();
                BasicProperties replyProps = new BasicProperties
                        .Builder()
                        .correlationId(props.getCorrelationId())
                        .build();

                String message = new String(delivery.getBody());
                int n = Integer.parseInt(message);

                System.out.println(tab + "server, [.] fib(" + message + ")");
                String response = "" + fib(n);
                System.out.println(tab + "server, res=" + response);

                channel.basicPublish( "", props.getReplyTo(), replyProps, response.getBytes());

                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int fib(int n) throws Exception {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}

class RpcClient extends WorkQueues {
    ConnectionFactory factory = null;
    Connection connection = null;
    Channel channel = null;
    String requestQueueName = "rpc_hello";
    String replyQueueName;
    QueueingConsumer consumer;

    public void init() {
        try {
            factory = new ConnectionFactory();
            factory.setHost("172.18.9.206");
            factory.setUsername("admin");
            factory.setPassword("abc123");
            connection = factory.newConnection();
            channel = connection.createChannel();

            replyQueueName = channel.queueDeclare().getQueue();
            consumer = new QueueingConsumer(channel);
            channel.basicConsume(replyQueueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public String send(String[] strings, String tab) throws Exception {
        String message = getMessage(strings);
        return call(message, tab);
    }

    public String call(String message, String tab) throws Exception {
        System.out.println("client, send " + message);
        String response = null;
        String corrId = java.util.UUID.randomUUID().toString();

        BasicProperties props = new BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes());

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response = new String(delivery.getBody());
                break;
            }
        }
        System.out.println("client, res=" + response);

        return response;
    }

    public void close() throws Exception {
        connection.close();
    }
}

