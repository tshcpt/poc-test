package com.sample.rest.webservices.ledger.repository;

import com.sample.rest.webservices.ledger.entity.MessageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageDetails, Long> {
}
