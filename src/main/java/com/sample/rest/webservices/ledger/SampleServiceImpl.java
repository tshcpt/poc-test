package com.sample.rest.webservices.ledger;

import org.springframework.stereotype.Component;

/**
 * This Service validates the given barcode to ensure that it adheres to the
 * business requirements. It validates the length, service indicator code,
 * checkdigit and the country code. It also calculates the checkdigit using the
 * input serial number to verify its value.
 *
 */
@Component
public class SampleServiceImpl implements SampleService {

	/**
	 * It validates the length, service indicator code, checkdigit and the country
	 * code.
	 * 
	 * @param barcode
	 * @return boolean
	 */
	public boolean validateBarcode(String barcode) {

		boolean isBarcodeValid = false;

		if (barcode != null && barcode.length() == BARCODELENGTH) {

			String checkDigitSource = barcode.substring(2, 11);
			String checkDigitVal = barcode.substring(10, 11);

			if (barcode.matches(BARCODEEX) && checkDigitVal.equals(calculateCheckDigit(checkDigitSource))) {
				isBarcodeValid = true;
			}
		}
		return isBarcodeValid;
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
