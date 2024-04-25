package com.example.kafka.chapter2.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author caijinyang
 * @classname DemoPartitioner
 * @description
 * @date 2024/4/25
 **/

public class DemoPartitioner implements Partitioner {
    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Compute the partition for the given record.
     *
     * @param topic      The topic name
     * @param key        The key to partition on (or null if no key)
     * @param keyBytes   The serialized key to partition on( or null if no key)
     * @param value      The value to partition on or null
     * @param valueBytes The serialized value to partition on or null
     * @param cluster    The current cluster metadata
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        if (null == keyBytes) {
            // 将消息轮询地写入所有的partition
            return counter.getAndIncrement() % numPartitions;
        } else
            // murmurhash2哈希
            return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
    }

    /**
     * This is called when partitioner is closed.
     */
    @Override
    public void close() {

    }

    /**
     * Configure this class with the given key-value pairs
     *
     * @param configs
     */
    @Override
    public void configure(Map<String, ?> configs) {

    }

    /**
     * Notifies the partitioner a new batch is about to be created. When using the sticky partitioner,
     * this method can change the chosen sticky partition for the new batch.
     *
     * @param topic         The topic name
     * @param cluster       The current cluster metadata
     * @param prevPartition The partition previously selected for the record that triggered a new batch
     */
    @Override
    public void onNewBatch(String topic, Cluster cluster, int prevPartition) {
        Partitioner.super.onNewBatch(topic, cluster, prevPartition);
    }
}
