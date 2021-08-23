package com.sample.rest.webservices.ledger.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown= true)
public class Result {
    private String asOf;

    private String ledgerId;
    private String curreny;
    private BigDecimal amount;
    private BigDecimal amountRounded;
    private Long transactionCount;

}
