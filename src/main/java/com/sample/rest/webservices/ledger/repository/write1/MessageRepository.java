package com.sample.rest.webservices.ledger.repository.write1;

import com.sample.rest.webservices.ledger.entity.write1.MessageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

//@Repository
@Transactional
public interface MessageRepository extends JpaRepository<MessageDetails, Long> {
}
