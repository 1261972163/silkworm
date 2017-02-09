package com.jengine.store.mq.rabbitmq;

import org.junit.Test;

/**
 * @author nouuid
 * @date 4/13/2016
 * @description
 */
public class RabbitmqTest {

    @Test
    public void simpleQueueTest() throws InterruptedException {
        SimpleQueueRunner simpleQueueRunner = new SimpleQueueRunner();
        simpleQueueRunner.simpleQueueTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void workQueueTest() throws InterruptedException {
        WorkQueuesAutoAckRunner workQueuesRunner = new WorkQueuesAutoAckRunner();
        workQueuesRunner.workQueueTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void oneReceiveWithoutAckThenStopTest() throws InterruptedException {
        WorkQueuesAckRunner workQueuesRunner2 = new WorkQueuesAckRunner();
        workQueuesRunner2.oneReceiveWithoutAckThenStopTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void allReceiveWithoutAckThenStopTest() throws InterruptedException {
        WorkQueuesAckRunner workQueuesRunner2 = new WorkQueuesAckRunner();
        workQueuesRunner2.allReceiveWithoutAckThenStopTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void oneReceiveWithoutAckThenOtherReceiveAckAllTest() throws InterruptedException {
        WorkQueuesAckRunner workQueuesRunner2 = new WorkQueuesAckRunner();
        workQueuesRunner2.oneReceiveWithoutAckThenOtherReceiveAckAllTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void prefetchOneReceiveWithoutAckTest() throws InterruptedException {
        WorkQueuesPrefetchRunner workQueuesPrefetchRunner = new WorkQueuesPrefetchRunner();
        workQueuesPrefetchRunner.prefetchOneReceiveWithoutAckTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void prefetchTwoReceiveWithoutAckTest() throws InterruptedException {
        WorkQueuesPrefetchRunner workQueuesPrefetchRunner = new WorkQueuesPrefetchRunner();
        workQueuesPrefetchRunner.prefetchTwoReceiveWithoutAckTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void exchangeTest() throws InterruptedException {
        FanoutExchangeRunner publishSubscribeRunner = new FanoutExchangeRunner();
        publishSubscribeRunner.exchangeTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void directExchangeTest() throws InterruptedException {
        DirectExchangeRoutingRunner routingRunner = new DirectExchangeRoutingRunner();
        routingRunner.directExchangeTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void directExchangeMultipleBindingsTest() throws InterruptedException {
        DirectExchangeRoutingRunner routingRunner = new DirectExchangeRoutingRunner();
        routingRunner.directExchangeMultipleBindingsTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void topicExchangeRoutingTest() throws InterruptedException {
        TopicExchangeRoutingRunner topicExchangeRoutingRunner = new TopicExchangeRoutingRunner();
        topicExchangeRoutingRunner.topicExchangeRoutingTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void rpcTest() throws InterruptedException {
        RpcRunner rpcRunner = new RpcRunner();
        rpcRunner.rpcTest();
        Thread.sleep(30 * 1000);
    }


}
