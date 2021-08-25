package com.sample.rest.webservices.ledger.consumer;

import com.sample.rest.webservices.ledger.entity.write1.MessageDetails;
import com.sample.rest.webservices.ledger.entity.write1.TransactionDetails;
import com.sample.rest.webservices.ledger.entity.write2.Write2MessageDetails;
import com.sample.rest.webservices.ledger.entity.write2.Write2TransactionDetails;
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

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class LedgerConsumer<T> implements Function<Flux<Message<byte[]>>, Mono<Void>> {
    final static Logger log = Logger.getLogger(LedgerConsumer.class.getName());
    private final ResultProducer resultProducer;
    private final SampleService sampleService;

    @Override
    @Transactional
    public Mono<Void> apply(Flux<Message<byte[]>> messageFlux) {
        //log.info("Inside consumer - "+messageFlux.toString());

        return messageFlux.doOnEach(this::acknowledge)
                //.map(msg -> new String(msg.getPayload()))
                //.doOnNext(jsonString -> log.info("Transaction request received:\n"+jsonString))
                .map(msg -> processEvent(SerializationUtils.deserialize(new String(msg.getPayload()), Root.class )))
                //.map(request -> processEvent(request))
                .doOnNext(map -> {
                    Map.Entry<Long, Object> mapSet = map.entrySet().stream().findFirst().get();
                    if(mapSet.getKey() == 1) {
                        sampleService.persistMessage((MessageDetails)mapSet.getValue());
                    } else {
                        sampleService.persistWrite2Message((Write2MessageDetails)mapSet.getValue());
                    }
                })
                .doOnError(error -> log.info(" Error occurred while persisting request"+ error))
                .onErrorContinue(InvalidDataConsumerException.class, (throwable, o) -> log.info("Exception while consuming message"))
                        .then()
                .retry();

    }


    private Map<Long, Object> processEvent(Root root) {
        Map<Long, Object> map = new HashMap<Long, Object>();
        MessageHeader header = root.getMessageHeader();
        MessageBody messageBody = root.getMessageBody();
        List<TransactionDetail> requestTransactions = messageBody.getTransactionDetail();
        Long key = 1L;
        if(header.getPartitionKeyId() != null){
            key = header.getPartitionKeyId() % 2;
        }
        log.info("Key =>>>>> "+key);
        if(key == 1) {
            MessageDetails msgEntity = new MessageDetails();
            msgEntity.setChannelName(header.getChannelName());
            //msgEntity.setSentDateTime(header.getSentDateTime());


            List<TransactionDetails> txEntity = new ArrayList<TransactionDetails>();

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
            map.put(key, msgEntity);
        } else {
            Write2MessageDetails msg2Entity = new Write2MessageDetails();
            msg2Entity.setChannelName(header.getChannelName());
            //msg2Entity.setSentDateTime(header.getSentDateTime());
            List<Write2TransactionDetails> tx2Entity = new ArrayList<Write2TransactionDetails>();

            requestTransactions.stream().forEach(o -> {
                Write2TransactionDetails tr2Object = new Write2TransactionDetails();
                tr2Object.setMessageDetail(msg2Entity);
                tr2Object.setAmount(new BigDecimal(o.getAmount()));
                tr2Object.setEodId(o.getEodId());
                tr2Object.setMopId(o.getMopId());
                tr2Object.setPoId(o.getPoId());
                tr2Object.setPeCode(o.getPeCode());
                tr2Object.setRxAcId(o.getRxAcId());
                tr2Object.setSenderAcId(o.getSenderAcId());
                tr2Object.setTxId(o.getTxId());
                tr2Object.setServiceCode(o.getServiceCode());

                tx2Entity.add(tr2Object);
            });
            msg2Entity.setTransactionDetails(tx2Entity);
            map.put(key, msg2Entity);

        }
        return map;
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
