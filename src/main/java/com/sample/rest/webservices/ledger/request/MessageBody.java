package com.sample.rest.webservices.ledger.request;

import java.util.List;

public class MessageBody{
    public String payId;
    public String methodOfPay;
    public String transferType;
    public String transactionType;
    public List<TransactionDetail> transactionDetail;
}