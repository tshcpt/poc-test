package com.sample.rest.webservices.ledger;


import com.sample.rest.webservices.ledger.consumer.LedgerConsumer;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.FluxMessageChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

import java.util.logging.Logger;

@Configuration
@ConditionalOnProperty(value = "spring.cloud.stream.kafka.default.producer.recordMetaDataChannel",
havingValue = "pocKafkaProducerResultListener")
public class KafkaProducerResultListener {
    final static Logger log = Logger.getLogger(KafkaProducerResultListener.class.getName());

    @Bean
    MessageChannel pocKafkaProducerResultListener() {
        return new FluxMessageChannel();
    }

    //public void handleAck(@Header(value = ProducerHeaders))

}
