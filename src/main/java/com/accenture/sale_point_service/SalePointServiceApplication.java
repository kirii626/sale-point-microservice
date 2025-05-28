package com.accenture.sale_point_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class SalePointServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalePointServiceApplication.class, args);
	}

}
