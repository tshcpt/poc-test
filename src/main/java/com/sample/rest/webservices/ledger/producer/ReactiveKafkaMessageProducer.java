package com.sample.rest.webservices.ledger.producer;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Supplier;

import static com.sample.rest.webservices.ledger.producer.ProducerHeaders.*;

public abstract class ReactiveKafkaMessageProducer<T> implements Supplier<Flux<Message<T>>> {
    private static final Logger LOG = LoggerFactory.getLogger(ReactiveKafkaMessageProducer.class);
    private static final Duration DEFAULT_ACK_TIMEOUT = Duration.ofSeconds(125);
    private static final String DEFAULT_PRODUCER_NAME = ReactiveKafkaMessageProducer.class.getSimpleName();

    private final EmitterProcessor<Message<T>> emitterProcessor = EmitterProcessor.create(Integer.MAX_VALUE);
    private final FluxSink<Message<T>> fluxSink = emitterProcessor.sink();

    public Mono<RecordMetadata> publish(final T t) {
        return publish(MessageBuilder.withPayload(t).build());

    }

    public Mono<RecordMetadata> publish(Message<T> message) {
        return Mono.defer(() -> Mono.fromFuture(emitMessage(message)))
                .name(producerName())
                .timeout(acknowledgmentTimeout())
                .onErrorMap(ProducerException::new);
    }

    private FutureProducerAcknowledgment emitMessage(Message<T> message) {
        if (message.getHeaders().containsKey(PRODUCER_ACKNOWLEDGMENT)) {
            String ackFoundInHeaderErrorMessage = "Publisher ack found in header. Publisher ack as the valie is stateful";
            throw new IllegalArgumentException(ackFoundInHeaderErrorMessage);
        }

        FutureProducerAcknowledgment kafkaProducerAck = new FutureProducerAcknowledgment();
        message = MessageBuilder.fromMessage(message)
                .setHeader(PRODUCER_ACKNOWLEDGMENT, kafkaProducerAck)
                .build();
        try {
            fluxSink.next(message);
        } catch (Exception e) {
            kafkaProducerAck.nack(e);
        }

        return kafkaProducerAck;
    }

    @Override
    public Flux<Message<T>> get() {
        return emitterProcessor;
    }

    private Duration acknowledgmentTimeout() {
        return DEFAULT_ACK_TIMEOUT;
    }

    protected String producerName() {
        return DEFAULT_PRODUCER_NAME;
    }


    protected abstract Duration acknowledgementTimeout();
}
