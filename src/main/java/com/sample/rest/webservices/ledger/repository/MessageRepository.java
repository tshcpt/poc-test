package com.sample.rest.webservices.ledger.repository;

import com.sample.rest.webservices.ledger.entity.MessageDetails;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<MessageDetails, Long> {
}
