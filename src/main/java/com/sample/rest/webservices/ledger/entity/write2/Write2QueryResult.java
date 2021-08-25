package com.sample.rest.webservices.ledger.entity.write2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Write2QueryResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal amount;
    private Long transactionCount;

}
