package com.sample.rest.webservices.ledger.util;

import javax.persistence.AttributeConverter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class InstantConverter implements AttributeConverter<Instant, ZonedDateTime> {

    @Override
    public ZonedDateTime convertToDatabaseColumn(Instant instant) {
        if (instant == null) return null;
        return instant.atZone(ZoneId.of("UTC"));
    }

    @Override
    public Instant convertToEntityAttribute(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) return null;
        return zonedDateTime.toInstant();
    }
}
