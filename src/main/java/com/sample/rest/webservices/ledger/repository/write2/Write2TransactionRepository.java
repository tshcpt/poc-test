package com.sample.rest.webservices.ledger.repository.write2;

import com.sample.rest.webservices.ledger.entity.write2.Write2QueryResult;
import com.sample.rest.webservices.ledger.entity.write2.Write2TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


public interface Write2TransactionRepository extends JpaRepository<Write2TransactionDetails, Long> {
    @Query("select NEW com.sample.rest.webservices.ledger.entity.write2.Write2QueryResult(" +
            "sum(p.amount), " +
            "count(p.txId)) " +
            "from Write2TransactionDetails p " +
            "where (:senderAcId is null or p.senderAcId =:senderAcId) " +
            "and (:serviceCode is null or p.serviceCode =:serviceCode) " +
            "and (:poId is null or p.poId =:poId) " +
            "and (:peCode is null or p.peCode =:peCode) " +
            "and (:eodId is null or p.eodId =:eodId) " +
            "and (:mopId is null or p.mopId =:mopId)")
    Write2QueryResult findByTransferDebitType(String senderAcId, String peCode,
                                              String serviceCode, String poId, String eodId, String mopId);

    @Query("select NEW com.sample.rest.webservices.ledger.entity.write2.Write2QueryResult(" +
            "sum(p.amount), " +
            "count(p.txId)) " +
            "from Write2TransactionDetails p " +
            "where (:rxAcId is null or p.rxAcId =:rxAcId) " +
            "and (:serviceCode is null or p.serviceCode =:serviceCode) " +
            "and (:poId is null or p.poId =:poId) " +
            "and (:peCode is null or p.peCode =:peCode) " +
            "and (:eodId is null or p.eodId =:eodId) " +
            "and (:mopId is null or p.mopId =:mopId)")
    Write2QueryResult findByTransferCreditType(String rxAcId, String peCode,
                                         String serviceCode, String poId, String eodId, String mopId);

}
