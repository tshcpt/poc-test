package com.sample.rest.webservices.ledger.producer;

import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.CompletableFuture;

public class FutureProducerAcknowledgment extends CompletableFuture<RecordMetadata>
        implements ProducerAcknowledgment{

    @Override
    public void acknowledge(RecordMetadata metadata) {

        this.complete(metadata);
    }

    @Override
    public void nack(Throwable throwable) {
        this.completeExceptionally(throwable);
    }


}
