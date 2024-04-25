package com.example.kafka.chapter2.serializer;

import com.example.kafka.chapter2.Company;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author caijinyang
 * @classname CompanySerializer
 * @description
 * @date 2024/4/25
 **/

public class CompanySerializer implements Serializer<Company> {
    /**
     * Convert {@code data} into a byte array.
     *
     * @param topic topic associated with data
     * @param data  typed data
     * @return serialized bytes
     */
    @Override
    public byte[] serialize(String topic, Company data) {
        return new byte[0];
    }

    /**
     * Configure this class.
     *
     * @param configs configs in key/value pairs
     * @param isKey   whether is for key or value
     */
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Serializer.super.configure(configs, isKey);
    }

    /**
     * Convert {@code data} into a byte array.
     *
     * @param topic   topic associated with data
     * @param headers headers associated with the record
     * @param data    typed data
     * @return serialized bytes
     */
    @Override
    public byte[] serialize(String topic, Headers headers, Company data) {
        if (data == null) {
            return null;
        }

        byte[] name;
        byte[] address;
        if (data.getName() != null) {
            name = data.getName().getBytes(StandardCharsets.UTF_8);
        } else {
            name = new byte[0];
        }

        if (data.getAddress() != null) {
            address = data.getAddress().getBytes(StandardCharsets.UTF_8);
        } else {
            address = new byte[0];
        }

        ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + name.length + address.length);
        buffer.putInt(name.length);
        buffer.put(name);
        buffer.putInt(address.length);
        buffer.put(address);

        return buffer.array();
    }

    /**
     * Close this serializer.
     * <p>
     * This method must be idempotent as it may be called multiple times.
     */
    @Override
    public void close() {
        // Serializer.super.close();
    }
}
