package com.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FbBootSampleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FbBootSampleApiApplication.class, args);
	}

}
