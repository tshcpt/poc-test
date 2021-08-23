package com.sample.rest.webservices.ledger.producer;

import com.sample.rest.webservices.ledger.response.ClientNotification;
import org.springframework.messaging.Message;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ResultProducer extends ReactiveKafkaMessageProducer<ClientNotification> {
    @Override
    public Mono<RecordMetadata> publish(final ClientNotification clientNotification){
        return super.publish(clientNotification);
    }

    @Override
    protected Duration acknowledgementTimeout() {
        return Duration.ofSeconds(125);
    }


}
