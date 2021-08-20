package com.sample.rest.webservices.ledger.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transaction_details")
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rxAcId;
    private String senderAcId;
    private BigDecimal amount;
    private String serviceCode;
    private String peCode;
    private String txId;
    private String mopId;
    private String eodId;
    private String poId;

    @ManyToOne(fetch = FetchType.LAZY)
    private MessageDetails messageDetail;

}
