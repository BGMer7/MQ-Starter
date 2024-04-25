package com.example.kafka.chapter2.interceptor;

import com.example.kafka.constant.Constant;
import org.apache.kafka.clients.producer.*;

import java.util.Map;

/**
 * @author caijinyang
 * @classname ProducerInterceptor
 * @description
 * @date 2024/4/25
 **/

public class ProducerInterceptorPrefix implements ProducerInterceptor<String, String> {
    private volatile long sendSuccess = 0;
    private volatile long sendFailure = 0;

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        String modifiedValue = "prefix1-" + record.value();
        return new ProducerRecord<>(record.topic(),
                record.partition(), record.timestamp(),
                record.key(), modifiedValue, record.headers());
//        if (record.value().length() < 5) {
//            throw new RuntimeException();
//        }
//        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        // 并不需要严格计数，所以只需要volatile即可
        if (e == null) {
            sendSuccess++;
        } else {
            sendFailure++;
        }
    }

    @Override
    public void close() {
        double successRatio = (double) sendSuccess / (sendFailure + sendSuccess);
        System.out.println("[INFO] 发送成功率=" + String.format("%f", successRatio * 100) + "%");
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}