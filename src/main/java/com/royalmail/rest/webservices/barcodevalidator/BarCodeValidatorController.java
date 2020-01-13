package com.royalmail.rest.webservices.barcodevalidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarCodeValidatorController {

	@Autowired
	private BarcodeValidatorService barcodeValidatorService;

	/**
	 * This method gets the input barcode from the path variable & invokes the
	 * service method to validate the barcode
	 * 
	 * @PathVariable String
	 * @return boolean
	 */
	@GetMapping("/validate/{barcode}")
	public boolean barcodeValidator(@PathVariable String barcode) {

		return barcodeValidatorService.validateBarcode(barcode);

	}

	/**
	 * This method gets the input barcode from the request parameter & invokes the
	 * service method to validate the barcode
	 * 
	 * @RequestParam String
	 * @return boolean
	 */
	@GetMapping("/validate")
	public boolean barcodeValidatorWithRequestParam(
			@RequestParam(name = "barcode", required = false, defaultValue = "") String barcode) {

		return barcodeValidatorService.validateBarcode(barcode);

	}

}
