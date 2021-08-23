package com.sample.rest.webservices.ledger.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown= true)
public class Event{
    public Instant origMsgTime;
    public Instant notifTime;
    public String id;
    public long creationDateTime;
    public long occurrenceDateTime;
    public String programId;
    public String eventType;
    public String eventState;
    public boolean finalState;
    public DataDetail data;
    public String txType;
    public boolean batchIntegrity;
}