package com.example.kafka.chapter2;

import com.example.kafka.constant.ServerConstant;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author caijinyang
 * @classname Producer
 * @description
 * @date 2024/4/25
 **/

public class Producer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getClasses());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getClasses());
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        KafkaProducer<String, Company>  producer = new KafkaProducer<>(properties);
        Company company = Company.builder().name("hiddenkafka").address("China").build();
        ProducerRecord<String, Company> record = new ProducerRecord<>(ServerConstant.TOPIC, company);

        producer.send(record).get();
    }
}
