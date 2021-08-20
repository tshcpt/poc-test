package com.sample.rest.webservices.ledger.controller;

import com.sample.rest.webservices.ledger.request.AggregateRequest;
import com.sample.rest.webservices.ledger.response.AggregateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SampleController {

	@Autowired
	private com.sample.rest.webservices.ledger.service.SampleService SampleService;

	/**
	 * Service test method
	 * 
	 * @PathVariable String
	 * @return boolean
	 */
	@GetMapping("/validate/{code}")
	public boolean Sample(@PathVariable String code) {

		return SampleService.validatecode(code);

	}

	/**
	 * This method gets the input code from the request parameter & invokes the
	 * service method to validate the code
	 * 
	 * @RequestBody Root
	 * @return ResponseBody
	 */
	@PostMapping("/aggregate")
	public AggregateResponse SampleWithRequestBody(@RequestBody AggregateRequest request){

		return SampleService.getAggregateBalance(request);

	}

}
