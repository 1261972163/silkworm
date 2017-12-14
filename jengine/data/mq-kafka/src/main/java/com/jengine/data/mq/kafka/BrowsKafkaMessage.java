package com.jengine.data.mq.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;


public class BrowsKafkaMessage {

  public static void main(String[] args) {
//   consumer("10.45.4.95:9092","estest4",0,7000l,10);
    consumer("10.45.4.95:9092","yqjlog-test1",0, 100l,10);


    System.out.println("publish finish!");

  }
  
  public static void consumer(String servers, String topic, int partition, long offset, int number){
    Properties props = new Properties();
    props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "brower");
    props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
    props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    
    KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
    TopicPartition tp = new TopicPartition(topic, partition);
    TopicPartition[] tps = new TopicPartition[]{tp};
    consumer.assign(Arrays.asList(tps));
    consumer.seek(tp, offset);
    int cnt=0;
    int quit=0;
    while(cnt<number){
      System.out.println("****");
      ConsumerRecords<String, String> records = consumer.poll(500);
      for(ConsumerRecord<String, String> record: records){
        System.out.println("key = " + record.key());
        System.out.println("value = " + record.value());

        ObjectMapper json = new ObjectMapper();
        try {
          Map<String,String> map = json.readValue(record.value(), new TypeReference<Map<String,String>>() {});
        } catch (IOException e) {
          e.printStackTrace();        }

        cnt++;
        if(cnt>=number){
          break;
        }
      }
      
      quit = records.isEmpty()?++quit:0;
      if(quit>5){
        break;
      }
    }
    
  }


}
