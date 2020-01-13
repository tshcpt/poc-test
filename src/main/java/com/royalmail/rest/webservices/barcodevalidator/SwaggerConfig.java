package com.royalmail.rest.webservices.barcodevalidator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final Contact CONTACT_INFO = new Contact("Vinoth Senthamaraikannan", "", "vinoth54@gmail.com");
	public static final ApiInfo UPDATED_API_INFO = new ApiInfo("BarCodeValidator RESTAPI", "BarCodeValidator REST API",
			"1.0", "urn:tos", CONTACT_INFO, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(UPDATED_API_INFO);
	}

}
