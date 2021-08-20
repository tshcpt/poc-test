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
public class TransactionDetail{
    public String rxAcId;
    public String senderAcId;
    public double amount;
    public String serviceCode;
    public String peCode;
    public String txId;
    public String mopId;
    public String eodId;
    public String poId;
}