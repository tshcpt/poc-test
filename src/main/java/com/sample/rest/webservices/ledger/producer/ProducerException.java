package com.sample.rest.webservices.ledger.producer;

public class ProducerException extends RuntimeException{
    public ProducerException(Throwable throwable){
        super(throwable);
    }
}
