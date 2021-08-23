package com.sample.rest.webservices.ledger.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.rest.webservices.ledger.exception.InvalidDataConsumerException;

import java.io.IOException;

public class SerializationUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static<R> R deserialize(final String json, final Class<R> clazz){
        R request = null;
        try {
            request = objectMapper.readValue(json, clazz);
        } catch(JsonProcessingException e){
            throw new InvalidDataConsumerException("Exception while deserializing ledger request", e);
        } catch(IOException ioException) {
            throw new InvalidDataConsumerException("IOException while deserializing ledger request", ioException);
        }

        return request;
    }

    public static <R> String serialize(final R object) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch(JsonProcessingException exception){
            exception.printStackTrace();
            throw new RuntimeException("Error while serializing object to String", exception);
        }

        return json;
    }
}
