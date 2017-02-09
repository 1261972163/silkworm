package com.jengine.store.mq.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * content
 *
 * @author bl07637
 * @date 9/1/2016
 * @since 0.1.0
 */
public class Kafka090ProducerService {
    protected final Log logger = LogFactory.getLog(getClass());

    private Producer<byte[], byte[]> producer;

    public Kafka090ProducerService(Properties properties) throws Exception {
        if (properties==null && properties.get("bootstrap.servers")==null
                && properties.get("key.serializer")==null
                && properties.get("value.serializer")==null) {
            throw new Exception("lack of properties");
        } else {
            producer = new KafkaProducer<byte[], byte[]>(properties);
        }
    }

    public void close() throws Exception {
        try {
            producer.close();
        } catch (Exception e) {
            logger.error("Close kafka producer or consumer Error!", e);
        }
        logger.info(" kafka has been shut down!");
    }

    public void send(ProducerRecord<byte[], byte[]> producerRecord) {
        if (producerRecord!=null) {
            producer.send(producerRecord);
        }
    }

}
