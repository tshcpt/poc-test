package com.sample.rest.webservices.ledger.consumer;

import com.sample.rest.webservices.ledger.entity.MessageDetails;
import com.sample.rest.webservices.ledger.entity.TransactionDetails;
import com.sample.rest.webservices.ledger.exception.InvalidDataConsumerException;
import com.sample.rest.webservices.ledger.producer.ResultProducer;
import com.sample.rest.webservices.ledger.request.MessageBody;
import com.sample.rest.webservices.ledger.request.MessageHeader;
import com.sample.rest.webservices.ledger.request.Root;
import com.sample.rest.webservices.ledger.request.TransactionDetail;
import com.sample.rest.webservices.ledger.service.SampleService;
import com.sample.rest.webservices.ledger.util.SerializationUtils;
import lombok.AllArgsConstructor;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class ResultConsumer<T> implements Function<Flux<Message<byte[]>>, Mono<Void>> {
    final static Logger log = Logger.getLogger(ResultConsumer.class.getName());

    @Override
    public Mono<Void> apply(Flux<Message<byte[]>> messageFlux) {
        log.info("Inside Result consumer - "+messageFlux);

        return messageFlux.doOnEach(this::acknowledge)
                .map(msg -> new String(msg.getPayload()))
                .doOnNext(jsonString -> log.info("Transaction request received:\n"+jsonString))
                .doOnError(error -> log.info(" Error occurred while persisting request"+ error))
                .onErrorContinue(InvalidDataConsumerException.class, (throwable, o) -> log.info("Exception while consuming message"))
                .then()
                .retry();

    }

    private void acknowledge(final Signal<Message<byte[]>> signal){
        if(signal.getType() == SignalType.ON_NEXT){
            Message<byte[]> message = signal.get();
            Acknowledgment ack = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        }
    }


    @Override
    public <V> Function<V, Mono<Void>> compose(Function<? super V, ? extends Flux<Message<byte[]>>> before) {
        return Function.super.compose(before);
    }

    @Override
    public <V> Function<Flux<Message<byte[]>>, V> andThen(Function<? super Mono<Void>, ? extends V> after) {
        return Function.super.andThen(after);
    }
}
