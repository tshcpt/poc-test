package com.sample.rest.webservices.ledger.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "message_details")
public class MessageDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String channelName;

    private ZonedDateTime sentDateTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "message_detail_id")
    private List<TransactionDetails> transactionDetails;

}


