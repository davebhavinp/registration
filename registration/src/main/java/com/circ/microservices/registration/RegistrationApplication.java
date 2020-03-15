package com.circ.microservices.registration;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RegistrationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}
	
	/*
	@Bean
	public Docket registrationApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.circ.microservices.registration")).build();
	}
	*/

}
