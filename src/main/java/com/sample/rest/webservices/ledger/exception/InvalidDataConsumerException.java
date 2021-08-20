package com.sample.rest.webservices.ledger.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public class InvalidDataConsumerException extends RuntimeException {
    public InvalidDataConsumerException(final String errorMsg, final JsonProcessingException e) {
        super(errorMsg, e);
    }

    public InvalidDataConsumerException(final String errorMsg, final IOException e) {
        super(errorMsg, e);
    }
}
