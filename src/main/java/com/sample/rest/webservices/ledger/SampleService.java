package com.sample.rest.webservices.ledger;

public interface SampleService {

	static final int[] WEIGHTAGE = { 8, 6, 4, 2, 3, 5, 9, 7 };

	static final String BARCODEEX = "[A-Z]{2}[0-9]{9}[G][B]";

	static final int BARCODELENGTH = 13;

	public boolean validateBarcode(String barcode);

}
