package com.sample.rest.webservices.ledger.entity.write1;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transaction_details")
public class TransactionDetails implements Serializable {

    private static final long serialVersionUID = 123467322579L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="rx_ac_id")
    private String rxAcId;

    @Column(name="sender_ac_id")
    private String senderAcId;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="service_code")
    private String serviceCode;

    @Column(name="pe_code")
    private String peCode;

    @Column(name="tx_id")
    private String txId;

    @Column(name="mop_id")
    private String mopId;

    @Column(name="eod_id")
    private String eodId;

    @Column(name="po_id")
    private String poId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="message_detail_id")
    private MessageDetails messageDetail;

}
