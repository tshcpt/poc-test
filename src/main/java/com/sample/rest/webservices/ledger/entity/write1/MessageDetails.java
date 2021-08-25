package com.sample.rest.webservices.ledger.entity.write1;

import com.sample.rest.webservices.ledger.util.InstantConverter;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "message_details")
public class MessageDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="channel_name")
    private String channelName;

    @Column(name="sent_date_time")
    @Convert(converter = InstantConverter.class)
    private Instant sentDateTime;

    //private transient Instant insertTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "message_detail_id")
    private List<TransactionDetails> transactionDetails;

}


