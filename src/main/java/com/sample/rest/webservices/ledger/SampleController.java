package com.sample.rest.webservices.ledger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

	@Autowired
	private SampleService SampleService;

	/**
	 * This method gets the input barcode from the path variable & invokes the
	 * service method to validate the barcode
	 * 
	 * @PathVariable String
	 * @return boolean
	 */
	@GetMapping("/validate/{barcode}")
	public boolean Sample(@PathVariable String barcode) {

		return SampleService.validateBarcode(barcode);

	}

	/**
	 * This method gets the input barcode from the request parameter & invokes the
	 * service method to validate the barcode
	 * 
	 * @RequestParam String
	 * @return boolean
	 */
	@GetMapping("/validate")
	public boolean SampleWithRequestParam(
			@RequestParam(name = "barcode", required = false, defaultValue = "") String barcode) {

		return SampleService.validateBarcode(barcode);

	}

}
