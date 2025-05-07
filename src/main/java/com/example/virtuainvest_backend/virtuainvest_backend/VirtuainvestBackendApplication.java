package com.example.virtuainvest_backend.virtuainvest_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class VirtuainvestBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtuainvestBackendApplication.class, args);
	}

}
