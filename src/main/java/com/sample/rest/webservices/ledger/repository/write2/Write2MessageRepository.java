package com.sample.rest.webservices.ledger.repository.write2;

import com.sample.rest.webservices.ledger.entity.write2.Write2MessageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

//@Repository
@Transactional
public interface Write2MessageRepository extends JpaRepository<Write2MessageDetails, Long> {
}
