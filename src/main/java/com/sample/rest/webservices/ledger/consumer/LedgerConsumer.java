package com.sample.rest.webservices.ledger.consumer;

import com.sample.rest.webservices.ledger.entity.MessageDetails;
import com.sample.rest.webservices.ledger.entity.TransactionDetails;
import com.sample.rest.webservices.ledger.exception.InvalidDataConsumerException;
import com.sample.rest.webservices.ledger.producer.ResultProducer;
import com.sample.rest.webservices.ledger.request.MessageBody;
import com.sample.rest.webservices.ledger.request.MessageHeader;
import com.sample.rest.webservices.ledger.request.Root;
import com.sample.rest.webservices.ledger.request.TransactionDetail;
import com.sample.rest.webservices.ledger.response.ClientNotification;
import com.sample.rest.webservices.ledger.service.SampleService;
import com.sample.rest.webservices.ledger.util.SerializationUtils;
import lombok.AllArgsConstructor;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class LedgerConsumer<T> implements Function<Flux<Message<byte[]>>, Mono<Void>> {
    final static Logger log = Logger.getLogger(LedgerConsumer.class.getName());
    private final ResultProducer resultProducer;
    private final SampleService sampleService;

    @Override
    public Mono<Void> apply(Flux<Message<byte[]>> messageFlux) {
        log.info("Inside consumer - "+messageFlux);

        return messageFlux.doOnEach(this::acknowledge)
                .map(msg -> new String(msg.getPayload()))
                .doOnNext(jsonString -> log.info("Transaction request received:\n"+jsonString))
                .map(json -> SerializationUtils.deserialize(json.toString(), Root.class ))
                .map(request -> processEvent(request))
                .doOnNext(payload -> sampleService.persistMessage(payload))
                .doOnError(error -> log.info(" Error occurred while persisting request"+ error))
                .onErrorContinue(InvalidDataConsumerException.class, (throwable, o) -> log.info("Exception while consuming message"))
                        .then()
                .retry();

    }


    private MessageDetails processEvent(Root root) {
        MessageHeader header = root.getMessageHeader();
        MessageDetails msgEntity = new MessageDetails();
        msgEntity.setChannelName(header.getChannelName());
        msgEntity.setSentDateTime(convertToTimeZone(header.getSentDateTime().toString()));

        MessageBody messageBody = root.getMessageBody();
        List<TransactionDetails> txEntity = new ArrayList<TransactionDetails>();
        List<TransactionDetail> requestTransactions = messageBody.getTransactionDetail();
        requestTransactions.stream().forEach(o -> {
            TransactionDetails trObject = new TransactionDetails();
            trObject.setMessageDetail(msgEntity);
            trObject.setAmount(new BigDecimal(o.getAmount()));
            trObject.setEodId(o.getEodId());
            trObject.setMopId(o.getMopId());
            trObject.setPoId(o.getPoId());
            trObject.setPeCode(o.getPeCode());
            trObject.setRxAcId(o.getRxAcId());
            trObject.setSenderAcId(o.getSenderAcId());
            trObject.setTxId(o.getTxId());
            trObject.setServiceCode(o.getServiceCode());

            txEntity.add(trObject);
        });
        msgEntity.setTransactionDetails(txEntity);
        return msgEntity;
    }

    private ZonedDateTime convertToTimeZone(String date) {
        System.out.println("date:"+date);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T':HH:mm:ss.SSSz");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        ZonedDateTime dt = null;
        dt = ZonedDateTime.parse(date, formatter);
        return dt;


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
