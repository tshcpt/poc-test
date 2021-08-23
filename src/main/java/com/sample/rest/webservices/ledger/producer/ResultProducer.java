package com.sample.rest.webservices.ledger.producer;

import com.sample.rest.webservices.ledger.consumer.LedgerConsumer;
import com.sample.rest.webservices.ledger.response.ClientNotification;
import org.springframework.messaging.Message;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.logging.Logger;

@Service
public class ResultProducer extends ReactiveKafkaMessageProducer<ClientNotification> {
    final static Logger log = Logger.getLogger(ResultProducer.class.getName());
    @Override
    public Mono<RecordMetadata> publish(final ClientNotification clientNotification){
        log.info("Publish result "+clientNotification.getEvent().getOrigMsgTime() + "Notification Time: "+clientNotification.getEvent().getNotifTime() + " Difference : "+
                Duration.between(clientNotification.getEvent().getNotifTime(), clientNotification.getEvent().getOrigMsgTime()));
        return super.publish(clientNotification);
    }

    @Override
    protected Duration acknowledgementTimeout() {
        return Duration.ofSeconds(125);
    }


}
