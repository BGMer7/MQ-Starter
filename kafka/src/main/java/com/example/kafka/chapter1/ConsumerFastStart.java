package com.example.kafka.chapter1;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author caijinyang
 * @classname ProducerFastStart
 * @description
 * @date 2024/4/24
 **/

public class ConsumerFastStart {
    public static final String brokerList = "localhost:9092";
    public static final String topic = "topic-demo";
    public static final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("bootstrap.servers", brokerList);
        properties.put("group.id", groupId);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> r : records) {
                System.out.println(r.value());
            }
        }
    }
}