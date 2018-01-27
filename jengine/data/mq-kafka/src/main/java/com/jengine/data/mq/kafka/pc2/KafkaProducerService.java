package com.jengine.data.mq.kafka.pc2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * content
 *
 * @author nouuid
 * @date 9/1/2016
 * @since 0.1.0
 */
public class KafkaProducerService {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  private Producer<byte[], byte[]> producer;

  public KafkaProducerService(Properties properties) throws Exception {
    if (properties == null && properties.get("bootstrap.servers") == null
        && properties.get("key.serializer") == null
        && properties.get("value.serializer") == null) {
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
    if (producerRecord != null) {
      producer.send(producerRecord);
    }
  }

}
