package com.sample.rest.webservices.ledger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleApplicationTests {

	@Autowired
	private com.sample.rest.webservices.ledger.controller.SampleController SampleController;

	@Test
	public void contextLoads() {

		assertThat(SampleController).isNotNull();
	}

	@Test
	public void testWithValidcode() {

		assertEquals(true, SampleController.Sample("AA473124829GB"));
		//assertEquals(true, SampleController.SampleWithRequestParam("RM123456785GB"));
	}

	@Test
	public void testWithInvalidServiceIndicator() {

		assertEquals(false, SampleController.Sample("Aa473124829GB"));
		assertEquals(false, SampleController.Sample("zB473124829GB"));
		//assertEquals(false, SampleController.SampleWithRequestParam("C1473124829GB"));
		//assertEquals(false, SampleController.SampleWithRequestParam("22473124829GB"));
	}

	@Test
	public void testWithInvalidcodeLength() {

		assertEquals(false, SampleController.Sample("AA73124829GB"));
		assertEquals(false, SampleController.Sample("AA7324829GB"));
		//assertEquals(false, SampleController.SampleWithRequestParam("AA2GB"));
	}

	@Test
	public void testWithInvalidCheckDigit() {

		assertEquals(false, SampleController.Sample("AA473124828GB"));
		//assertEquals(false, SampleController.SampleWithRequestParam("RM123456780GB"));
	}

	@Test
	public void testWithInvalidCountryCode() {

		assertEquals(false, SampleController.Sample("AA473124828Gb"));
		assertEquals(false, SampleController.Sample("AA473124828gB"));
		assertEquals(false, SampleController.Sample("AA473124828BB"));
		//assertEquals(false, SampleController.SampleWithRequestParam("AA473124828GG"));
		//assertEquals(false, SampleController.SampleWithRequestParam("AA47312482812"));
	}

	@Test
	public void testWithEmptyAndNullcode() {

		assertEquals(false, SampleController.Sample(""));
		//assertEquals(false, SampleController.SampleWithRequestParam(null));
	}
}
