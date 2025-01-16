package com.training.reactive_bank_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class ReactiveBankSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveBankSystemApplication.class, args);
	}

}
