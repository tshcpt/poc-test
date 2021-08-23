package com.sample.rest.webservices.ledger.producer;

import org.apache.kafka.clients.producer.RecordMetadata;

@FunctionalInterface
public interface ProducerAcknowledgment {
    void acknowledge(final RecordMetadata recordMetaData);

    default void nack(final Throwable throwable) {
        throw new UnsupportedOperationException("Producer ack not support nack ");
    }
}
