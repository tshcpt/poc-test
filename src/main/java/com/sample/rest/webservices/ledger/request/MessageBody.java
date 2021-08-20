package com.sample.rest.webservices.ledger.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown= true)
public class MessageBody{
    public String payId;
    public String methodOfPay;
    public String transferType;
    public String transactionType;
    public List<TransactionDetail> transactionDetail;
}