package com.example.kafka.chapter1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author caijinyang
 * @classname ProducerFastStart
 * @description
 * @date 2024/4/24
 **/

public class ProducerFastStart {
    public static final String BROKER_LIST = "localhost:9092";
    public static final String TOPIC = "topic-demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("bootstrap.servers", BROKER_LIST);

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, "hello, Kafka!");
        try {
            // producer.send(record);
            producer.send(producerRecord).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.close();
    }
}