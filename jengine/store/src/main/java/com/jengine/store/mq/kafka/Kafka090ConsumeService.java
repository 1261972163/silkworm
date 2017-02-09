package com.jengine.store.mq.kafka;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author nouuid
 * @date 5/27/2016
 * @description
 */
public class Kafka090ConsumeService {
    protected final Log logger = LogFactory.getLog(getClass());

    private CopyOnWriteArraySet<KafkaConsumer<byte[], byte[]>> consumers = new CopyOnWriteArraySet<KafkaConsumer<byte[], byte[]>>();

    public Kafka090ConsumeService(Properties properties, String topic, int consumerNum) throws Exception {
        if (properties==null && properties.get("bootstrap.servers")==null
                && properties.get("key.serializer")==null
                && properties.get("value.serializer")==null
                && consumerNum<=0
                && StringUtils.isNotBlank(topic)) {
            throw new Exception("lack of properties");
        } else {
            for (int i=0; i< consumerNum; i++) {
                KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<byte[], byte[]>(properties);
                consumer.subscribe(Arrays.asList(topic));
                consumers.add(consumer);
            }
        }
    }

    public void close() throws Exception {
        try {
            if (consumers!=null && consumers.size()>0) {
                for (KafkaConsumer<byte[], byte[]> consumer : consumers) {
                    if (consumer!=null) {
                        synchronized (consumer) {
                            if (consumer!=null) {
                                consumer.close();
                                consumer = null;
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            logger.error("Close kafka producer or consumer Error!", e);
        }
        logger.info(" kafka has been shut down!");
    }

    public CopyOnWriteArraySet<KafkaConsumer<byte[], byte[]>> getConsumers() {
        return consumers;
    }
}
