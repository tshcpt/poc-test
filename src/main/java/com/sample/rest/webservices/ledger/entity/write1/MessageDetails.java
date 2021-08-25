package com.sample.rest.webservices.ledger.entity.write1;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "message_details")
public class MessageDetails implements Serializable {

    private static final long serialVersionUID = 6832006422622219737L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="channel_name")
    private String channelName;

    //private Instant sentDateTime;

    //private transient Instant insertTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "message_detail_id")
    private List<TransactionDetails> transactionDetails;

}


