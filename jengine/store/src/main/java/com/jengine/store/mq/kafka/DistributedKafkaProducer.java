package com.jengine.store.mq.kafka;

import com.jengine.serializer.json.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.zookeeper.WatchedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * content
 *
 * @author bl07637
 * @date 11/15/2016
 * @since 0.1.0
 */
public class DistributedKafkaProducer<K, V> {
    private final Log logger = LogFactory.getLog(getClass());

    private Producer<K, V> producer = null;


    private static Properties initProps(String brokerList) {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", brokerList);
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        return properties;
    }

    public void initClient(CuratorWatcher watcher) {
        String brokerList = DynamicBrokerLocator.getBrokerlist(watcher);
        producer = new KafkaProducer<K, V>(initProps(brokerList));
    }

    public void send(String topic, V message) {
        if (null == message) {
            throw new IllegalArgumentException("message can't be null.");
        }
        this.send(topic, null, message);
    }


    public void send(String topic, K key, V message) {
        if (null == message) {
            throw new IllegalArgumentException("message can't be null.");
        }
        ProducerRecord<K, V> producerRecord = null;
        if (null == key) {
            producerRecord = new ProducerRecord(topic, JsonUtil.toByteJson(message));
        } else {
            producerRecord = new ProducerRecord(topic, UUID.randomUUID().toString().getBytes(), JsonUtil.toByteJson(message));
        }
        producer.send(producerRecord);
    }

    public void send(String topic, List messages) {
        this.send(topic, null, messages);
    }


    public void send(String topic, K key, List<V> messages) {
        if (null == messages || messages.size() <= 0) {
            throw new IllegalArgumentException("message can't be empty.");
        }
        List<ProducerRecord<K, V>> producerRecords = new ArrayList<ProducerRecord<K, V>>(messages.size());
        for (V message : messages) {
            this.send(topic, null, messages);
        }
    }
}

class KafkaBrokerWatcher implements CuratorWatcher {


    private DistributedKafkaProducer producer;

    public KafkaBrokerWatcher(DistributedKafkaProducer producer) {
        this.producer = producer;
    }

    @Override
    public void process(WatchedEvent event) throws Exception {
        if (event.getType().toString().equalsIgnoreCase("NodeChildrenChanged")) {
            producer.initClient(this);
        }

    }

}

class DynamicBrokerLocator {

//    private static ZkClient zkCli = new ZkClient(ZK_STR);

    public static String getBrokerlist(CuratorWatcher watcher) {
//        Brokers brokers = new Brokers();
//        List brokerList = new ArrayList();
//        List nodes = zkCli.list(brokerPath(), watcher);
//        if (null != nodes && nodes.size() > 0) {
//            for (String node : nodes) {
//                String nodePath = brokerPath() + "/" + node;
//                String nodeContent = zkCli.read(nodePath, null);
//                System.out.println("node path: " + nodePath + " : " + nodeContent);
//                JSONObject json = JSONObject.fromObject(nodeContent);
//                Broker broker = new Broker(json.getString("host"), json.getInt("port"));
//                System.out.println("broker---->" + broker.toString());
//                brokerList.add(broker);
//            }
//        } else {
//            return DEFAULT_BROKER_LIST;
//        }
//        System.out.println("brokerList: " + JsonUtil.serialize(brokerList));
//        brokers.setBrokers(brokerList);
//        System.out.println("=============broker.list: " + brokers.toString());
//        return brokers.toString();
        return null;
    }

    private static String brokerPath() {
        return "/brokers/ids";
    }
}
