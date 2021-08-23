package com.sample.rest.webservices.ledger.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.sample.rest.webservices.ledger.exception.InvalidDataConsumerException;

import java.io.IOException;

public class SerializationUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());

    public static<R> R deserialize(final String json, final Class<R> clazz){
        R request = null;
        try {
            request = objectMapper.readValue(json, clazz);
        } catch(Exception e) {
            e.printStackTrace();
            //throw new InvalidDataConsumerException("Exception while deserializing ledger request", e);
        /*} catch(IOException ioException) {
            throw new InvalidDataConsumerException("IOException while deserializing ledger request", ioException);
        }*/
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
