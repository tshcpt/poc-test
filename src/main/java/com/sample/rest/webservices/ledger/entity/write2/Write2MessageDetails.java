package com.sample.rest.webservices.ledger.entity.write2;

import com.sample.rest.webservices.ledger.util.InstantConverter;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@ToString
@Data
@Entity
@Table(name = "message_details")
public class Write2MessageDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="channel_name")
    private String channelName;

    @Column(name="sent_date_time")
    @Convert(converter = InstantConverter.class)
    private Instant sentDateTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "message_detail_id")
    private List<Write2TransactionDetails> transactionDetails;

}


