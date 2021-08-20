package com.sample.rest.webservices.ledger.repository;

import com.sample.rest.webservices.ledger.entity.MessageDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageDetails, Long> {
}
