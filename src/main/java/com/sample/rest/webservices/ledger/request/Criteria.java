package com.sample.rest.webservices.ledger.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown= true)
public class Criteria {
    public CriteriaData criteriaData;
    public String postingType;
    public String transactionStatus;
    public String txType;
    public String ledgerId;
}

