package com.sample.rest.webservices.ledger.service;

import com.sample.rest.webservices.ledger.entity.write1.MessageDetails;
import com.sample.rest.webservices.ledger.entity.write2.Write2MessageDetails;
import com.sample.rest.webservices.ledger.request.AggregateRequest;
import com.sample.rest.webservices.ledger.response.AggregateResponse;
import reactor.core.publisher.Mono;

public interface SampleService {

	int[] WEIGHTAGE = { 8, 6, 4, 2, 3, 5, 9, 7 };

	String codeEX = "[A-Z]{2}[0-9]{9}[G][B]";

	int codeLENGTH = 13;

	String CREDIT_TR_TYPE = "CREDIT";

	String DEBIT_TR_TYPE = "DEBIT";

    String RES_COMPLETED_STATUS = "COMPLETED";

    String DEFAULT_CUR_CODE = "AUD";

	boolean validatecode(String code);

	AggregateResponse getAggregateBalance(AggregateRequest aggregateRequest);

	Mono<Void> persistMessage(MessageDetails messageDetails);

	Mono<Void> persistWrite2Message(Write2MessageDetails messageDetails);

}
