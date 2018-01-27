package com.jengine.transport.cluster.api.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nouuid on 13/01/2018.
 */
public abstract class AbstractTransportConnection implements TransportConnection {

    private static final Logger logger = LoggerFactory.getLogger(AbstractTransportConnection.class);

    protected volatile boolean active = false;
    protected AtomicLong totalInvokeCount = new AtomicLong(); // 执行invoke的总次数
    protected AtomicLong activeInvokeCount = new AtomicLong(); // 当前正在执行的invoke数目
    protected AtomicLong totalInvokeLastCount = new AtomicLong(); // 执行invoke的总次数(上1分钟的统计)
    protected float rate = 0f; // 执行invoke的速率
    protected Timer rateUpdateTimer = null;
    protected Timer reconnectTimer = null;

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean setActive(boolean active) {
        this.active = active;
        return active;
    }

    @Override
    public void start() throws Exception {
        connect();
        rateUpdateTimer();
        reconnectTimer();
    }

    protected abstract void instanceConnect() throws Exception;

    protected abstract void instanceClose() throws Exception;

    protected abstract void instanceInvoke(Message message) throws Exception;

    protected abstract void instanceInvoke(List<Message> messages) throws Exception;

//    protected abstract boolean instanceActive() throws Exception;

    private void connect() throws Exception {
        try {
            instanceConnect();
        } catch (Exception e) {
            setActive(false);
            throw e;
        }
        setActive(true);
    }

    private void reconnect()  throws Exception {
        if (!isActive()) {
            connect();
        }
    }

    /**
     * 更新rate
     */
    private void rateUpdateTimer() {
        rateUpdateTimer = new Timer();
        rateUpdateTimer.schedule(new TimerTask() {
            public void run() {
                long currentCount = totalInvokeCount.get();
                rate = (currentCount - totalInvokeLastCount.get()) / 60f;
                totalInvokeLastCount.set(currentCount);
            }
        }, 60 * 1000, 60 * 1000);
    }

    /**
     * 检查连接，若未连接则重连
     */
    private void reconnectTimer() {
        reconnectTimer = new Timer();
        reconnectTimer.schedule(new TimerTask() {
            public void run() {
                try {
                    reconnect();
                } catch (Exception e) {
                    logger.error("Timer reconnect error, ", e);
                }
            }
        }, 60 * 1000, 10 * 60 * 1000);
    }

    @Override
    public void close() throws Exception {
        // 写入操作完成后才能关闭连接
        while (true) {
            if (activeInvokeCount.get() <= 0) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
        }
        try {
            instanceClose();
            setActive(false);
        } catch (Exception e) {
            logger.error("", e);
        }
        rateUpdateTimer.cancel();
        reconnectTimer.cancel();
    }

    @Override
    public void invoke(Message message) throws Exception {
        totalInvokeCount.incrementAndGet();
        activeInvokeCount.incrementAndGet();
        try {
            instanceInvoke(message);
        } catch (Exception e) {
            Exception exception = new Exception(Thread.currentThread().getName() +
                " invoke(Message) failed.", e);
            throw exception;
        } finally {
            activeInvokeCount.decrementAndGet();
        }
    }

    @Override
    public void invoke(List<Message> messages) throws Exception {
        totalInvokeCount.incrementAndGet();
        activeInvokeCount.incrementAndGet();
        try {
            instanceInvoke(messages);
        } catch (Exception e){
            Exception exception = new Exception(Thread.currentThread().getName() +
                " invoke(Messages) failed.", e);
            throw exception;
        }finally {
            activeInvokeCount.decrementAndGet();
        }
    }
}
