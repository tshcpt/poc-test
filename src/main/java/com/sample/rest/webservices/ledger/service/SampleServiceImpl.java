package com.sample.rest.webservices.ledger.service;

import com.sample.rest.webservices.ledger.entity.MessageDetails;
import com.sample.rest.webservices.ledger.entity.QueryResult;
import com.sample.rest.webservices.ledger.repository.MessageRepository;
import com.sample.rest.webservices.ledger.repository.TransactionRepository;
import com.sample.rest.webservices.ledger.request.AggregateRequest;
import com.sample.rest.webservices.ledger.request.CriteriaData;
import com.sample.rest.webservices.ledger.response.AggregateResponse;
import com.sample.rest.webservices.ledger.response.ClientNotification;
import com.sample.rest.webservices.ledger.response.Event;
import com.sample.rest.webservices.ledger.response.Result;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Hashtable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * This Service class
 *
 */
@Component
public class SampleServiceImpl implements SampleService {

	//@Autowired
	//private ResultProducer resultProducer;

	@Autowired
	private MessageRepository  messageRepository;

	@Autowired
	private TransactionRepository  transactionRepository;

	public AggregateResponse getAggregateBalance(AggregateRequest request){
		QueryResult resultData = new QueryResult();

		if(Optional.of(request.getCreiteria()).isPresent()) {

			CriteriaData data = request.getCreiteria().getCriteriaData();
			if (CREDIT_TR_TYPE.equalsIgnoreCase(request.getCreiteria().getPostingType())) {
				resultData = transactionRepository.findByTransferCreditType(request.getCreiteria().getLedgerId()
						, data.getPeCode(), data.getServiceCode(), data.getPoId(), data.getEodId(), data.getMopId());
			} else if (DEBIT_TR_TYPE.equalsIgnoreCase(request.getCreiteria().getPostingType())) {
				resultData = transactionRepository.findByTransferDebitType(request.getCreiteria().getLedgerId()
						, data.getPeCode(), data.getServiceCode(), data.getPoId(), data.getEodId(), data.getMopId());
			} else {
				Hashtable<String, QueryResult> balanceByTxType = new Hashtable<String, QueryResult>();
				QueryResult finalResultData = resultData;
				CompletableFuture.completedFuture(transactionRepository.findByTransferCreditType(request.getCreiteria().getLedgerId()
						, data.getPeCode(), data.getServiceCode(), data.getPoId(), data.getEodId(), data.getMopId()))
						.thenApply(result -> balanceByTxType.put(CREDIT_TR_TYPE, result)).thenAcceptBoth(
						CompletableFuture.completedFuture(transactionRepository.findByTransferDebitType(request.getCreiteria().getLedgerId()
								, data.getPeCode(), data.getServiceCode(), data.getPoId(), data.getEodId(), data.getMopId()))
								.thenApply(result -> balanceByTxType.put(DEBIT_TR_TYPE, result)),
						(s1, s2) -> {
							finalResultData.setTransactionCount(s1.getTransactionCount() + s2.getTransactionCount());
							finalResultData.setAmount(s1.getAmount().subtract(s2.getAmount()));
						});
			}
		}

		return constructAggregateResponse(resultData, request.getCreiteria().ledgerId);
	}

	public void persistMessage(MessageDetails message){

		messageRepository.save(message);
		//return resultProducer.publish(prepareResponse(message));
	}

	private ClientNotification prepareResponse(MessageDetails message) {

		if(message.getTransactionDetails() != null){
			Event event = Event.builder().eventType("BATCH").batchIntegrity(true).eventState("COMPLETED").txType("V2V").finalState(true)
					.creationDateTime(System.currentTimeMillis())
					.programId("1002251").occurrenceDateTime(System.currentTimeMillis())
					.id(message.getTransactionDetails().get(0).getTxId()).build();
			ClientNotification.builder().event(event);
		}
		return null;
	}

	private AggregateResponse constructAggregateResponse(QueryResult result, String ledgerAcId ){

		return AggregateResponse.builder().aggregationId(String.valueOf(System.currentTimeMillis())).status(RES_COMPLETED_STATUS)
				.result(Result.builder().amount(result.getAmount() == null ? new BigDecimal(0): result.getAmount()).asOf(new Date().toString())
						.transactionCount(result.getTransactionCount() == null ? 0 : result.getTransactionCount()).curreny(DEFAULT_CUR_CODE).ledgerId(ledgerAcId)
						.amountRounded(result.getAmount() == null ? new BigDecimal(0):
								result.getAmount().setScale(8, BigDecimal.ROUND_HALF_UP)).build()).build();
	}
	/**
	 * It validates the length, service indicator code, checkdigit and the country
	 * code.
	 * 
	 * @param code
	 * @return boolean
	 */
	public boolean validatecode(String code) {

		boolean iscodeValid = false;

		if (code != null && code.length() == codeLENGTH) {

			String checkDigitSource = code.substring(2, 11);
			String checkDigitVal = code.substring(10, 11);

			if (code.matches(codeEX) && checkDigitVal.equals(calculateCheckDigit(checkDigitSource))) {
				iscodeValid = true;
			}
		}
		return iscodeValid;
	}

	/**
	 * This method calculates the checkdigit using the input serial number to verify
	 * its value.
	 * 
	 * @param sourceData
	 * @return String
	 */
	private String calculateCheckDigit(String sourceData) {
		int sum = 0;
		for (int i = 0; i < WEIGHTAGE.length; i++) {
			sum = sum + (sourceData.charAt(i) * WEIGHTAGE[i]);
		}
		int checkDigit = 11 - (sum % 11);

		if (checkDigit == 10)
			return "0";
		else if (checkDigit == 11)
			return "5";
		else
			return Integer.toString(checkDigit);

	}

}
