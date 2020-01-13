package com.royalmail.rest.webservices.barcodevalidator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.royalmail.rest.webservices.barcodevalidator.BarCodeValidatorController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BarcodeValidatorApplicationTests {

	@Autowired
	private BarCodeValidatorController barcodeValidatorController;

	@Test
	public void contextLoads() {

		assertThat(barcodeValidatorController).isNotNull();
	}

	@Test
	public void testWithValidBarcode() {

		assertEquals(true, barcodeValidatorController.barcodeValidator("AA473124829GB"));
		assertEquals(true, barcodeValidatorController.barcodeValidatorWithRequestParam("RM123456785GB"));
	}

	@Test
	public void testWithInvalidServiceIndicator() {

		assertEquals(false, barcodeValidatorController.barcodeValidator("Aa473124829GB"));
		assertEquals(false, barcodeValidatorController.barcodeValidator("zB473124829GB"));
		assertEquals(false, barcodeValidatorController.barcodeValidatorWithRequestParam("C1473124829GB"));
		assertEquals(false, barcodeValidatorController.barcodeValidatorWithRequestParam("22473124829GB"));
	}

	@Test
	public void testWithInvalidBarcodeLength() {

		assertEquals(false, barcodeValidatorController.barcodeValidator("AA73124829GB"));
		assertEquals(false, barcodeValidatorController.barcodeValidator("AA7324829GB"));
		assertEquals(false, barcodeValidatorController.barcodeValidatorWithRequestParam("AA2GB"));
	}

	@Test
	public void testWithInvalidCheckDigit() {

		assertEquals(false, barcodeValidatorController.barcodeValidator("AA473124828GB"));
		assertEquals(false, barcodeValidatorController.barcodeValidatorWithRequestParam("RM123456780GB"));
	}

	@Test
	public void testWithInvalidCountryCode() {

		assertEquals(false, barcodeValidatorController.barcodeValidator("AA473124828Gb"));
		assertEquals(false, barcodeValidatorController.barcodeValidator("AA473124828gB"));
		assertEquals(false, barcodeValidatorController.barcodeValidator("AA473124828BB"));
		assertEquals(false, barcodeValidatorController.barcodeValidatorWithRequestParam("AA473124828GG"));
		assertEquals(false, barcodeValidatorController.barcodeValidatorWithRequestParam("AA47312482812"));
	}

	@Test
	public void testWithEmptyAndNullBarcode() {

		assertEquals(false, barcodeValidatorController.barcodeValidator(""));
		assertEquals(false, barcodeValidatorController.barcodeValidatorWithRequestParam(null));
	}
}
